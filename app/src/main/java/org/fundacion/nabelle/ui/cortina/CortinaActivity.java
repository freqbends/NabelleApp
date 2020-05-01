package org.fundacion.nabelle.ui.cortina;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import org.fundacion.nabelle.MainActivity;
import org.fundacion.nabelle.R;
import org.fundacion.nabelle.conexionBD.ConexionSQLiteHelper;
import org.fundacion.nabelle.ui.login.LoginActivity;
import org.fundacion.nabelle.utils.UtilidadesDB;
import org.fundacion.nabelle.utils.UtilidadesTablasDB;


import androidx.appcompat.app.AppCompatActivity;

public class CortinaActivity extends AppCompatActivity {

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cortina);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UtilidadesTablasDB utils = new UtilidadesTablasDB();
                conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_nabelle",null,2);
                utils.registrarEstados(getApplicationContext());
                String usuario = utils.consultarUsuario(conn);
                if(usuario.equals("")){
                    Intent intent=new Intent(CortinaActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("correo",usuario);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }



}
