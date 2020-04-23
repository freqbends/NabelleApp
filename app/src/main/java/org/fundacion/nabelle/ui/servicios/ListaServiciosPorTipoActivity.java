package org.fundacion.nabelle.ui.servicios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.interfaces.ServicioInterface;
import org.fundacion.nabelle.model.Servicio;
import org.fundacion.nabelle.presenter.serviciosPresenter.PresentadorServicios;
import org.fundacion.nabelle.utils.AdapterImagenesServicios;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ListaServiciosPorTipoActivity extends AppCompatActivity implements ServicioInterface.Vista {

    String tipoServicio = "";
    public PresentadorServicios presentadorServicios;
    RecyclerView rListaTipoServicio;
    AdapterImagenesServicios serviciosAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_servicios_por_tipo);
        Bundle bundle=getIntent().getExtras();
        tipoServicio = bundle.getString("tipoServicio");

        presentadorServicios = new PresentadorServicios(this);
        presentadorServicios.setVista(this);
        presentadorServicios.obtenerListaServicios(tipoServicio);

        rListaTipoServicio = findViewById(R.id.listaTipoServicio);
        rListaTipoServicio.setHasFixedSize(true);
        rListaTipoServicio.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    public void mostrarListaServicios(List<Servicio> listaTipoServicio) {
        serviciosAdapter = new AdapterImagenesServicios(ListaServiciosPorTipoActivity.this, listaTipoServicio);
        rListaTipoServicio.setAdapter(serviciosAdapter);


    }

    @Override
    public void mostrarMensaje(String mensaje) {

        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void mostrarProgreso() {

    }
}
