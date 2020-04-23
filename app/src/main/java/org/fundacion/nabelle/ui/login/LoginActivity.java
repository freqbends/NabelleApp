package org.fundacion.nabelle.ui.login;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.conexionBD.ConexionSQLiteHelper;
import org.fundacion.nabelle.ui.cuenta.ResetPasswordActivity;
import org.fundacion.nabelle.ui.cuenta.SignupActivity;
import org.fundacion.nabelle.presenter.loginPresenter.PresentadorLogin;
import org.fundacion.nabelle.ui.galeria.Galeria;
import org.fundacion.nabelle.utils.UtilidadesDB;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private String email,password;
    private PresentadorLogin presentadorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presentadorLogin = new PresentadorLogin(this,auth);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        Button btnGaleria = (Button) findViewById(R.id.btn_galeria);
        btnGaleria.setOnClickListener(this);
        Button btnSignup = (Button) findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(this);
        Button btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                if(email.equals("")||email.equals(null)||password.equals("")||password.equals(null)){
                    Toast.makeText(this,"Debe ingresar Correo y Contraseña",Toast.LENGTH_LONG).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(20);

                    Long idResultante = registrarUsuario();
                    if(idResultante != 0 && idResultante != null){
                        presentadorLogin.autenticaUsuario(email,password);
                        finish();
                    }else{
                        Toast.makeText(this,"Hubo un error, favor de intentar más tarde",Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);

                }
                break;

            case R.id.btn_galeria:
                startActivity(new Intent(LoginActivity.this, Galeria.class));
                break;

            case R.id.btn_signup:
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                break;

            case R.id.btn_reset_password:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
        }

    }

    public Long registrarUsuario(){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"bd_nabelle",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilidadesDB.CAMPO_ID,email);
        values.put(UtilidadesDB.CAMPO_CORREO,email);
        values.put(UtilidadesDB.CAMPO_PWD,password);
        return db.insert(UtilidadesDB.TABLA_USUARIO,UtilidadesDB.CAMPO_ID,values);
    }
}
