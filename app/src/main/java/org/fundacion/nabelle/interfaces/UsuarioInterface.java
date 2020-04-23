package org.fundacion.nabelle.interfaces;

import android.net.Uri;


import org.fundacion.nabelle.model.Usuario;

public interface UsuarioInterface {

    interface Vista{
        public void mostrarUsuario(Usuario usuario);
        public void mostrarMensaje(String mensaje);
        public void mostrarProgreso();
    }

    interface Presentador{
        public void setVista(UsuarioInterface.Vista vista);
        public void obtenerUsuario(Usuario usuario);
        public void actualizarUsuario(Usuario usuario);
        public void actualizaImagenUsuario(Uri imageUri, String idUsuario, String extension);
    }

}
