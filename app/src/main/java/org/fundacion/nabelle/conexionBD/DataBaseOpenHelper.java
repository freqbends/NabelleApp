package org.fundacion.nabelle.conexionBD;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseOpenHelper extends SQLiteAssetHelper {
    private static final String DB_DIRECCION = "db_direccion.db";
    private static final int DB_VERSION = 1;

    public DataBaseOpenHelper(Context context){
        super(context, DB_DIRECCION,null,DB_VERSION);
    }
}
