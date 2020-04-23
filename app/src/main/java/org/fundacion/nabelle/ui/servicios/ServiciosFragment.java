package org.fundacion.nabelle.ui.servicios;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.fundacion.nabelle.MainActivity;
import org.fundacion.nabelle.R;
import org.fundacion.nabelle.model.Servicio;
import org.fundacion.nabelle.model.TipoServicios;
import org.fundacion.nabelle.model.Upload;
import org.fundacion.nabelle.ui.galeria.Galeria;
import org.fundacion.nabelle.utils.AdapterServiPromo;
import org.fundacion.nabelle.utils.ImagesAdapter;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ServiciosFragment extends Fragment {

    RecyclerView listaServicios;
    private AdapterServiPromo mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<TipoServicios> lista;
    private StorageReference storageRef;
    private ProgressBar progressBar;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_servicios, container, false);
        listaServicios = root.findViewById(R.id.listaServicios);
        listaServicios.setHasFixedSize(true);
        listaServicios.setLayoutManager(new GridLayoutManager(getContext(),2));
        progressBar = root.findViewById(R.id.progressBarServicios);
        progressBar.setProgress(20);

        storageRef = FirebaseStorage.getInstance().getReference();

        lista = new ArrayList<>();

        final CollectionReference coleccion = FirebaseFirestore.getInstance().collection("TipoServicios");

        coleccion.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);
                    for (DocumentSnapshot document : task.getResult()) {
                        Upload upload =  new Upload();
                        TipoServicios servicio = new TipoServicios();
                        servicio.setIdTipoServicio(document.getId());
                        servicio.setIcono(document.getString("icono"));
                        servicio.setNombre(document.getString("nombre"));

                        lista.add(servicio);

                    }
                    mAdapter = new AdapterServiPromo(getContext(), lista);


                    mAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent servicios = new Intent(getContext(), ListaServiciosPorTipoActivity.class);
                            servicios.putExtra("tipoServicio", lista.get(listaServicios.getChildAdapterPosition(v)).getIdTipoServicio());
                            startActivity(servicios);
                        }
                    });


                    listaServicios.setAdapter(mAdapter);





                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });




        return root;
    }
}