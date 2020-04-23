package org.fundacion.nabelle.interfaces;


import org.fundacion.nabelle.model.Servicio;

import java.util.List;

public interface ServicioInterface {
    interface Vista{
        void mostrarListaServicios(List<Servicio> listaTipoServicios);
        void mostrarMensaje(String mensaje);
        void mostrarProgreso();
    }

    interface Presentador{
        void setVista(ServicioInterface.Vista vista);
        void obtenerListaServicios(String idTipoServicio);
        void detalleServicio(String idServicio);
    }
}
