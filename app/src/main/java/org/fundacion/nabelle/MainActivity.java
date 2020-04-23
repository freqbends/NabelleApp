package org.fundacion.nabelle;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.fundacion.nabelle.interfaces.UsuarioInterface;
import org.fundacion.nabelle.model.Usuario;
import org.fundacion.nabelle.presenter.usuarioPresenter.PresentadorUsuario;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UsuarioInterface.Vista{

    private AppBarConfiguration mAppBarConfiguration;
    private PresentadorUsuario presentadorUsuario;
    ImageView imagen;
    TextView correo;
    String correoUsuario;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView =(NavigationView) findViewById(R.id.nav_view);
        //con esto generamos el usuario en el header del menu-------------------------------
        View hView = navigationView.getHeaderView(0);
        correo = (TextView) hView.findViewById(R.id.correoUsuarioPrincipal);
        imagen = (ImageView) hView.findViewById(R.id.imagenUsuarioPrincipal);
        imagen.setOnClickListener(this);



        Bundle bundle=getIntent().getExtras();
        correoUsuario = bundle.getString("correo");
        Usuario miUsuario = new Usuario();
        miUsuario.setCorreo(correoUsuario);
        presentadorUsuario = new PresentadorUsuario(this);
        presentadorUsuario.setVista(this);
        presentadorUsuario.obtenerUsuario(miUsuario);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_ajustes, R.id.nav_galeria, R.id.nav_promociones, R.id.nav_servicios)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imagenUsuarioPrincipal:
                dialogoImagen().show();
                break;
        }

    }

    @Override
    public void mostrarUsuario(Usuario usuario) {
        correo.setText(correoUsuario);
        if(usuario.getFotoUrl() != null){
            Glide.with(this)
                    .load(usuario.getFotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imagen);
        }

    }

    @Override
    public void mostrarMensaje(String mensaje) {

    }

    @Override
    public void mostrarProgreso() {

    }

    public AlertDialog dialogoImagen() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setTitle("Elige")
                .setMessage(Html.fromHtml("<font color='#540e1f'>Elige para cambiar tu foto</font>"))
                .setNegativeButton(Html.fromHtml("<font color='#540e1f'>CAMARA</font>"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
                                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                                        // Should we show an explanation?
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                                            // Show an expanation to the user *asynchronously* -- don't block
                                            // this thread waiting for the user's response! After the user
                                            // sees the explanation, try again to request the permission.
                                        } else {
                                            // No se necesita dar una explicación al usuario, sólo pedimos el permiso.
                                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA}, 1 );
                                            // MY_PERMISSIONS_REQUEST_CAMARA es una constante definida en la app. El método callback obtiene el resultado de la petición.
                                        }
                                    }else{ //have permissions
                                        dispatchTakePictureIntent();
                                    }
                                }else{ // Pre-Marshmallow
                                    dispatchTakePictureIntent();
                                }

                            }
                        })
                .setPositiveButton(Html.fromHtml("<font color='#540e1f'>GALERIA</font>"),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setType("image/");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent,PICK_IMAGE_REQUEST);
                            }
                        });

        return builder.create();
    }

    private void dispatchTakePictureIntent() {
        Log.d(TAG, "Llegamos al intent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(MainActivity.this.getApplicationContext().getPackageManager()) != null) {
            Log.d(TAG, "Iniciamos");
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }else{
            Log.d(TAG, "No llegamos");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            Glide.with(MainActivity.this)
                    .load(imageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imagen);

            if(imageUri == null){
                mostrarMensaje("Debe seleccionar una imágen");
            }else{
                //progreso.setVisibility(View.VISIBLE);
                presentadorUsuario.actualizaImagenUsuario(imageUri,correoUsuario,getFileExtension(imageUri));
            }
        } else {
            Log.d(TAG, "No se pudo");
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = MainActivity.this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}
