package org.fundacion.nabelle.utils;

public class UtilidadesDB {

    //Constantes tabla de Usuario
    public final static String TABLA_USUARIO = "usuario";
    public final static String CAMPO_ID = "idUsuario";
    public final static String CAMPO_CORREO = "correo";
    public final static String CAMPO_PWD = "password";
    public final static String CREAR_TABLA_USUARIO = "CREATE TABLE "+TABLA_USUARIO+ "("+CAMPO_ID+" TEXT, "+CAMPO_CORREO+" TEXT, "+CAMPO_PWD+" TEXT)";

}
