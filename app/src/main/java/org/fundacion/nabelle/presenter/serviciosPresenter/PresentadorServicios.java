package org.fundacion.nabelle.presenter.serviciosPresenter;

import android.content.Context;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import org.fundacion.nabelle.interfaces.ServicioInterface;
import org.fundacion.nabelle.model.Servicio;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class PresentadorServicios implements ServicioInterface.Presentador {

    private Context mContext;
    private ServicioInterface.Vista vista;

    private StorageReference storageReference;
    private DocumentReference docRef;
    private FirebaseFirestore db;
    private List<Servicio> listaServicio;


    public PresentadorServicios(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void setVista(ServicioInterface.Vista vista) {
        this.vista = vista;

    }

    @Override
    public void obtenerListaServicios(String idTipoServicio) {
        listaServicio = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        db.collection("Servicios").whereEqualTo("idTipoServicio",idTipoServicio).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Servicio servicio = new Servicio();
                        servicio.setIdServicio(document.getLong("idServicioTipo"));
                        servicio.setDescripcionCorta(document.getString("descCorta"));
                        servicio.setFotoPrincipalServicio(document.getString("fotoPrincipal"));
                        servicio.setDescripcionLarga(document.getString("descLarga"));
                        servicio.setPrecio(document.getString("precio"));
                        listaServicio.add(servicio);
                        vista.mostrarListaServicios(listaServicio);
                    }
                }else{
                    vista.mostrarMensaje("No existen datos para su búsqueda");
                }

            }
        });


    }

    @Override
    public void detalleServicio(String idServicio) {

    }
}
