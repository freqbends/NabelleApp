package org.fundacion.nabelle.presenter.loginPresenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.fundacion.nabelle.MainActivity;
import org.fundacion.nabelle.interfaces.LoginInterface;
import org.fundacion.nabelle.model.Usuario;

import androidx.annotation.NonNull;

public class PresentadorLogin implements LoginInterface {

    private Context mContext;
    private FirebaseAuth auth;
    private String correo;
    private String mPassword;

    public PresentadorLogin(Context mContext, FirebaseAuth auth) {
        this.mContext = mContext;
        this.auth = auth;
    }


    @Override
    public void autenticaUsuario(String email, String password) {
        correo = email;
        mPassword = password;
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext,"Fallo en inicio de sesión, revisa tu correo o contraseña, o regístrate",Toast.LENGTH_SHORT).show();
                        } else {

                            Usuario usuario = new Usuario();
                            usuario.setCorreo(correo);
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("correo",usuario.getCorreo());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                            mContext.startActivity(intent);
                        }
                    }
                });
    }


    @Override
    public void registroUsuario(String email, String password) {

    }
}
