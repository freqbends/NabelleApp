package org.fundacion.nabelle.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.fundacion.nabelle.conexionBD.ConexionSQLiteHelper;
import org.fundacion.nabelle.model.Estados;
import org.fundacion.nabelle.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UtilidadesTablasDB {
    ConexionSQLiteHelper conn;

    public Long registrarUsuario(Context context,String email, String password){
        conn = new ConexionSQLiteHelper(context,"bd_nabelle",null,2);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UtilidadesDB.CAMPO_ID,email);
        values.put(UtilidadesDB.CAMPO_CORREO,email);
        values.put(UtilidadesDB.CAMPO_PWD,password);
        return db.insert(UtilidadesDB.TABLA_USUARIO,UtilidadesDB.CAMPO_ID,values);
    }

    public List<Estados> consultarLista(Context context){
        conn = new ConexionSQLiteHelper(context,"bd_nabelle",null,2);
        SQLiteDatabase db = conn.getWritableDatabase();
        List<Estados> listaEstados = new ArrayList<Estados>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ UtilidadesDB.TABLA_ESTADOS,null);
        while(cursor.moveToNext()){
            Estados estado = new Estados();
            estado.setIdEstado(cursor.getInt(0));
            estado.setNombreEstado(cursor.getString(1));
            listaEstados.add(estado);
        }

        return listaEstados;
    }

    public String consultarUsuario(ConexionSQLiteHelper conn){
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

    public void eliminarUsuarioSQLite(Context context,Usuario usuario){
        conn = new ConexionSQLiteHelper(context,"bd_nabelle",null,2);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[]parametro = {usuario.getCorreo()};
        db.delete(UtilidadesDB.TABLA_USUARIO,UtilidadesDB.CAMPO_ID+"=?",parametro);
        db.close();
    }

    public void registrarEstados(Context context){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context,"bd_nabelle",null,2);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.delete(UtilidadesDB.TABLA_ESTADOS,null,null);
        String insert = "INSERT INTO "+UtilidadesDB.TABLA_ESTADOS+ " ("+UtilidadesDB.CAMPO_ID_EDO  +" , " + UtilidadesDB.CAMPO_NOMBRE_EDO+") VALUES (1,'Aguascalientes'),\n" +
                " (2,'Baja California'),\n" +
                " (3,'Baja California Sur'),\n" +
                " (4,'Campeche'),\n" +
                " (5,'Coahuila'),\n" +
                " (6,'Colima'),\n" +
                " (7,'Chiapas'),\n" +
                " (8,'Chihuahua'),\n" +
                " (9,'Ciudad de México'),\n" +
                " (10,'Durango'),\n" +
                " (11,'Guanajuato'),\n" +
                " (12,'Guerrero'),\n" +
                " (13,'Hidalgo'),\n" +
                " (14,'Jalisco'),\n" +
                " (15,'México'),\n" +
                " (16,'Michoacán'),\n" +
                " (17,'Morelos'),\n" +
                " (18,'Nayarit'),\n" +
                " (19,'Nuevo León'),\n" +
                " (20,'Oaxaca'),\n" +
                " (21,'Puebla'),\n" +
                " (22,'Querétaro'),\n" +
                " (23,'Quintana Roo'),\n" +
                " (24,'San Luis Potosí'),\n" +
                " (25,'Sinaloa'),\n" +
                " (26,'Sonora'),\n" +
                " (27,'Tabasco'),\n" +
                " (28,'Tamaulipas'),\n" +
                " (29,'Tlaxcala'),\n" +
                " (30,'Veracruz'),\n" +
                " (31,'Yucatán'),\n" +
                " (32,'Zacatecas')";

        db.execSQL(insert);
        db.close();

    }
}
