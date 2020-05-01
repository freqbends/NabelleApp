package org.fundacion.nabelle.conexionBD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.fundacion.nabelle.model.Estados;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DataBaseAccess instance;
    Cursor c = null;

    private DataBaseAccess(Context context){
        this.openHelper = new DataBaseOpenHelper(context);

    }

    public static DataBaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    public void open(){
        try{
            this.db = this.openHelper.getWritableDatabase();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("error-----" + ex.getMessage());
        }
    }

    public void close(){
        if(db!= null){
            this.db.close();
        }
    }

    public List<Estados> getEstados(){
        List<Estados> listEstados = new ArrayList<>();
        Estados estado = null;
        c = db.rawQuery("select * from estados",null);
        while(c.moveToNext()){
            estado = new Estados();
            estado.setIdEstado(c.getInt(0));
            estado.setNombreEstado(c.getString(1));
            listEstados.add(estado);
        }
        return listEstados;
    }
}
