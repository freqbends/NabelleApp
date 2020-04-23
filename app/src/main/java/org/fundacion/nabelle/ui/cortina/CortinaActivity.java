package org.fundacion.nabelle.ui.cortina;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import org.fundacion.nabelle.MainActivity;
import org.fundacion.nabelle.R;
import org.fundacion.nabelle.conexionBD.ConexionSQLiteHelper;
import org.fundacion.nabelle.ui.login.LoginActivity;
import org.fundacion.nabelle.utils.UtilidadesDB;

import androidx.appcompat.app.AppCompatActivity;

public class CortinaActivity extends AppCompatActivity {

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cortina);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_nabelle",null,1);
                String usuario = consultarUsuario();
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

    private String consultarUsuario(){
        String usuario = "";
        SQLiteDatabase db = conn.getReadableDatabase();
        String[]campos = {UtilidadesDB.CAMPO_ID};
        try{
            Cursor cursor = db.query(UtilidadesDB.TABLA_USUARIO,campos,null,null,null,null,null);
            cursor.moveToFirst();
            usuario = cursor.getString(0);
        }catch (Exception ex){

        }

        return usuario;
    }
}
