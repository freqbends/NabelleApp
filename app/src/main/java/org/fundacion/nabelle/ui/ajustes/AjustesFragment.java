package org.fundacion.nabelle.ui.ajustes;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.fundacion.nabelle.R;
import org.fundacion.nabelle.conexionBD.ConexionSQLiteHelper;
import org.fundacion.nabelle.interfaces.UsuarioInterface;
import org.fundacion.nabelle.model.Usuario;
import org.fundacion.nabelle.presenter.usuarioPresenter.PresentadorUsuario;
import org.fundacion.nabelle.ui.login.LoginActivity;
import org.fundacion.nabelle.utils.UtilidadesDB;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AjustesFragment extends Fragment implements View.OnClickListener, UsuarioInterface.Vista {

    private GalleryViewModel galleryViewModel;
    View vista;
    Usuario usuario;
    private TextView correo;
    private Button botonGuardar,botonActualizar;
    private EditText nombre,apPaterno,apMaterno,fechaNacimiento,calle,codigoPostal,seccionElectoral,clave,numeroElectoral,curp,rfc,celular,fijo;
    ImageView cerrarSesion;
    private ProgressBar progreso;
    private Spinner estadoCivil,estado,municipio,colonia,genero;
    private PresentadorUsuario presentadorUsuario;
    ArrayAdapter<CharSequence> adaptadorEC;
    private FirebaseAuth auth;
    ConexionSQLiteHelper conn;

    private void listaMunicipios(){
        ArrayAdapter<CharSequence> adaptadorMunicipios = ArrayAdapter.createFromResource(getContext(),R.array.municipios,android.R.layout.simple_spinner_item);
        municipio.setAdapter(adaptadorMunicipios);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        vista =  inflater.inflate(R.layout.fragment_ajustes, container, false);
        usuario = (Usuario)getActivity().getApplicationContext();
        presentadorUsuario = new PresentadorUsuario(getContext());
        correo = (TextView)vista.findViewById(R.id.mailUsuario);
        botonGuardar = vista.findViewById(R.id.btnGuardar);
        botonGuardar.setOnClickListener(this);
        progreso = vista.findViewById(R.id.progressBar);
        estadoCivil = (Spinner)vista.findViewById(R.id.estadoCivil);
        nombre = (EditText)vista.findViewById(R.id.nombres) ;
        apPaterno = (EditText)vista.findViewById(R.id.apPaterno);
        apMaterno = (EditText)vista.findViewById(R.id.apMaterno);
        fechaNacimiento = (EditText)vista.findViewById(R.id.fechaNacimiento);
        calle = (EditText)vista.findViewById(R.id.calle);
        codigoPostal = (EditText)vista.findViewById(R.id.codigoPostal);
        seccionElectoral = (EditText)vista.findViewById(R.id.seccionElectoral);
        clave = (EditText)vista.findViewById(R.id.clave);
        numeroElectoral = (EditText)vista.findViewById(R.id.numeroElectoral);
        curp = (EditText)vista.findViewById(R.id.curp);
        rfc = (EditText)vista.findViewById(R.id.rfc);
        celular = (EditText)vista.findViewById(R.id.celular);
        fijo = (EditText)vista.findViewById(R.id.fijo);
        cerrarSesion = (ImageView)vista.findViewById(R.id.img_sesion);
        cerrarSesion.setOnClickListener(this);


        estado = vista.findViewById(R.id.estado);
        municipio = vista.findViewById(R.id.municipio);
        colonia = vista.findViewById(R.id.colonia);
        genero = vista.findViewById(R.id.genero);

        presentadorUsuario.setVista(this);
        presentadorUsuario.obtenerUsuario(usuario);
        adaptadorEC = ArrayAdapter.createFromResource(getContext(),R.array.estadoCivil,android.R.layout.simple_spinner_item);
        progreso.setVisibility(View.INVISIBLE);
        estadoCivil.setAdapter(adaptadorEC);
        ArrayAdapter<CharSequence> adaptadorEstado = ArrayAdapter.createFromResource(getContext(),R.array.estado,android.R.layout.simple_spinner_item);
        estado.setAdapter(adaptadorEstado);
        auth = FirebaseAuth.getInstance();
        conn = new ConexionSQLiteHelper(getContext(),"bd_nabelle",null,1);



        estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    listaMunicipios();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    return vista;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnGuardar:
                progreso.setVisibility(View.VISIBLE);

                usuario.setNombre(nombre.getText().toString().trim());
                usuario.setApPaterno(apPaterno.getText().toString().trim());
                usuario.setApMaterno(apMaterno.getText().toString().trim());
                usuario.setCorreo(usuario.getCorreo());
                estadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        if ((pos!=0) && (id!=0)) {
                            Object item = parent.getItemAtPosition(pos);
                            String idSeleccionado = item.toString();
                            usuario.setEstadoCivil(idSeleccionado);
                            Toast.makeText(getContext(), idSeleccionado, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                usuario.setFechaNacimiento(fechaNacimiento.getText().toString());
                usuario.setCalle(calle.getText().toString());
                usuario.setCodigoPostal(codigoPostal.getText().toString());
                usuario.setSeccionElectoral(seccionElectoral.getText().toString());
                usuario.setClaveElector(clave.getText().toString());
                usuario.setNumIdentificador(numeroElectoral.getText().toString());
                usuario.setCurpUsuario(curp.getText().toString());
                usuario.setRfcUsuario(rfc.getText().toString());
                usuario.setCelular(celular.getText().toString());
                usuario.setFijo(fijo.getText().toString());

                presentadorUsuario.actualizarUsuario(usuario);
                break;

            case R.id.img_sesion:

                dialogoImagen(view).show();
                break;

        }

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void mostrarUsuario(Usuario usuario) {

        correo.setText(usuario.getCorreo());
        nombre.setText(usuario.getNombre());
        apPaterno.setText(usuario.getApPaterno());
        apMaterno.setText(usuario.getApMaterno());
        fechaNacimiento.setText(usuario.getFechaNacimiento());
        if(usuario.getEstado() == null){
            estadoCivil.setAdapter(adaptadorEC);
        }

        calle.setText(usuario.getCalle());
        codigoPostal.setText(usuario.getCodigoPostal());
        seccionElectoral.setText(usuario.getSeccionElectoral());
        clave.setText(usuario.getClaveElector());
        numeroElectoral.setText(usuario.getNumIdentificador());
        curp.setText(usuario.getCurpUsuario());
        rfc.setText(usuario.getRfcUsuario());
        celular.setText(usuario.getCelular());
        fijo.setText(usuario.getFijo());

    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
        progreso.setVisibility(View.INVISIBLE);

    }

    @Override
    public void mostrarProgreso() {
        progreso.setProgress(20);
    }

    public AlertDialog dialogoImagen(View view){


        AlertDialog.Builder builder =new AlertDialog.Builder(view.getContext());
        builder.setTitle("Cerrar Sesión").setMessage(Html.fromHtml("<font color='#540e1f'>¿Desea cerrar la sesión?</font>"))
                .setNegativeButton(Html.fromHtml("<font color='#540e1f'>NO</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton(Html.fromHtml("<font color='#540e1f'>SI</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (usuario != null) {
                    auth.signOut();
                    Toast.makeText(getContext(), "¡Esta cerrando su sesión!", Toast.LENGTH_SHORT).show();
                    eliminarUsuarioSQLite(usuario);


                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getFragmentManager().beginTransaction().remove(AjustesFragment.this).commit();
                }
                else{
                    Toast.makeText(getContext(), "Ya no estan en la sesión!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getFragmentManager().beginTransaction().remove(AjustesFragment.this).commit();
                }
            }
        });

        return builder.create();

    }

    private void eliminarUsuarioSQLite(Usuario usuario){
        SQLiteDatabase db = conn.getWritableDatabase();
        String[]parametro = {usuario.getCorreo()};
        db.delete(UtilidadesDB.TABLA_USUARIO,UtilidadesDB.CAMPO_ID+"=?",parametro);
        db.close();
    }

}