package org.fundacion.nabelle.model;

import android.app.Application;

import java.util.List;

public class Servicio  extends Application{

    private Long idServicio;
    private String idTipoServicio;
    private String descripcionCorta;
    private String descripcionLarga;
    private String fotoPrincipalServicio;
    private String calleServicio;
    private List<FotosServicio> listaFotosServicio;
    private List<Especificaciones> listaEspecificaciones;
    private String precio;

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getFotoPrincipalServicio() {
        return fotoPrincipalServicio;
    }

    public void setFotoPrincipalServicio(String fotoPrincipalServicio) {
        this.fotoPrincipalServicio = fotoPrincipalServicio;
    }

    public String getCalleServicio() {
        return calleServicio;
    }

    public void setCalleServicio(String calleServicio) {
        this.calleServicio = calleServicio;
    }

    public List<FotosServicio> getListaFotosServicio() {
        return listaFotosServicio;
    }

    public void setListaFotosServicio(List<FotosServicio> listaFotosServicio) {
        this.listaFotosServicio = listaFotosServicio;
    }

    public List<Especificaciones> getListaEspecificaciones() {
        return listaEspecificaciones;
    }

    public void setListaEspecificaciones(List<Especificaciones> listaEspecificaciones) {
        this.listaEspecificaciones = listaEspecificaciones;
    }

    public String getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(String idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
