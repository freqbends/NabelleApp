package org.fundacion.nabelle.ui.direccion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.conexionBD.ConexionSQLiteHelper;
import org.fundacion.nabelle.conexionBD.DataBaseAccess;
import org.fundacion.nabelle.model.Estados;
import org.fundacion.nabelle.utils.UtilidadesDB;
import org.fundacion.nabelle.utils.UtilidadesTablasDB;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DireccionActivity extends AppCompatActivity {

    ListView listaViewEstados;
    List<String> listaInformacion;
    List<Estados> lista;
    ConexionSQLiteHelper conn;
    UtilidadesTablasDB utils = new UtilidadesTablasDB();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);
        listaViewEstados = (ListView)findViewById(R.id.listaEstados);
        conn = new ConexionSQLiteHelper(this,"bd_nabelle",null,2);
        lista = utils.consultarLista(getApplicationContext());
        obtenerLista();
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listaViewEstados.setAdapter(adaptador);
    }



    void obtenerLista(){
        listaInformacion = new ArrayList<String>();
        for(int i = 0; i<lista.size(); i++){
            listaInformacion.add(lista.get(i).getIdEstado() + " - " + lista.get(i).getNombreEstado());
        }

    }
}
