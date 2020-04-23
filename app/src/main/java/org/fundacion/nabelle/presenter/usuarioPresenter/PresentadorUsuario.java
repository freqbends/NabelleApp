package org.fundacion.nabelle.presenter.usuarioPresenter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.fundacion.nabelle.interfaces.UsuarioInterface;
import org.fundacion.nabelle.model.Usuario;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PresentadorUsuario  implements UsuarioInterface.Presentador {

    private Context mContext;
    private FirebaseAuth auth;
    private String correo;
    private String mPassword;
    Usuario miUsuario;
    private String foto;

    private StorageReference storageReference;
    private DocumentReference docRef;
    private FirebaseFirestore db;

    private UsuarioInterface.Vista vista;


    public PresentadorUsuario(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void setVista(UsuarioInterface.Vista vista) {
        this.vista = vista;
    }

    @Override
    public void obtenerUsuario(Usuario usuario) {
        db = FirebaseFirestore.getInstance();

       // docRef= db.collection("Usuarios").document(usuario.getCorreo());
        db.collection("Usuarios").document(usuario.getCorreo()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Usuario miUsuario = new Usuario();
                        miUsuario.setFotoUrl(document.getString("Imagen"));
                        miUsuario.setNombre(document.getString("nombre"));
                        miUsuario.setApPaterno(document.getString("apPaterno"));
                        miUsuario.setApMaterno(document.getString("apMaterno"));
                        miUsuario.setCorreo(document.getString("Correo"));
                        miUsuario.setEstadoCivil(document.getString("estadoCivil"));
                        miUsuario.setFechaNacimiento(document.getString("fechaNacimiento"));
                        miUsuario.setCalle(document.getString("calle"));
                        miUsuario.setCodigoPostal(document.getString("codigoPostal"));
                        miUsuario.setSeccionElectoral(document.getString("seccion"));
                        miUsuario.setClaveElector(document.getString("claveElector"));
                        miUsuario.setNumIdentificador(document.getString("numeroElectoral"));
                        miUsuario.setCurpUsuario(document.getString("curp"));
                        miUsuario.setRfcUsuario(document.getString("rfc"));
                        miUsuario.setCelular(document.getString("celular"));
                        miUsuario.setFijo(document.getString("fijo"));
                        vista.mostrarUsuario(miUsuario);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        Map<String, Object> user = new HashMap<>();
        user.put("nombre", usuario.getNombre());
        user.put("apPaterno", usuario.getApPaterno());
        user.put("apMaterno",usuario.getApMaterno());
        user.put("Correo", usuario.getCorreo());
        user.put("estadoCivil", usuario.getEstadoCivil());
        user.put("fechaNacimiento", usuario.getFechaNacimiento());
        user.put("calle",usuario.getCalle());
        user.put("codigoPostal", usuario.getCodigoPostal());
        user.put("seccion",usuario.getSeccionElectoral());
        user.put("claveElector", usuario.getClaveElector());
        user.put("numeroElectoral", usuario.getNumIdentificador());
        user.put("curp", usuario.getCurpUsuario());
        user.put("rfc", usuario.getRfcUsuario());
        user.put("celular", usuario.getCelular());
        user.put("fijo", usuario.getFijo());


        docRef= FirebaseFirestore.getInstance().collection("Usuarios").document(usuario.getCorreo());

        docRef
                .update(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        vista.mostrarMensaje("Se actualizó la información con éxito");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        vista.mostrarMensaje("No se pudo actualizar la información, favor de revisar !");
                    }
                });
    }



    @Override
    public void actualizaImagenUsuario(Uri imageUri, final String idUsuario, String extension) {
        storageReference = FirebaseStorage.getInstance().getReference("Galeria/");
        docRef= FirebaseFirestore.getInstance().collection("Usuarios").document(idUsuario);
        if(imageUri != null){
            final StorageReference referencia = storageReference.child(idUsuario + "_"+System.currentTimeMillis()+ "." + extension);
            Task<Uri> urlTask = referencia.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return referencia.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    vista.mostrarProgreso();
                                                }
                                            },
                                5000);
                        Uri downloadUrl = task.getResult();
                        String miUrl = downloadUrl.toString();
                        Usuario miUsuario = new Usuario();
                        miUsuario.setCorreo(idUsuario);
                        miUsuario.setFotoUrl(miUrl);


                        Map<String, Object> user = new HashMap<>();
                        user.put("Imagen", miUsuario.getFotoUrl());

                        docRef
                                .update(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       vista.mostrarMensaje("Imágen actualizada correctamente");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        vista.mostrarMensaje("No se pudo actualizar la imágen, favor de revisar");
                                    }
                                });




                    } else {
                        vista.mostrarMensaje("No se pudo actualizar la imágen, favor de revisar");
                    }
                }
            });




            referencia.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                vista.mostrarProgreso();
                                            }
                                        },
                            5000);
                    vista.mostrarMensaje("Imágen subida");

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            vista.mostrarMensaje("Hubo un error");
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double  progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            vista.mostrarProgreso();
                        }
                    });

        }else{
            vista.mostrarMensaje("Debe seleccionar un archivo");
        }
    }

}
