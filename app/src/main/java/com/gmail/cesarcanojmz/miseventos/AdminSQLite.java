package com.gmail.cesarcanojmz.miseventos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cesar on 24/10/17.
 */

public class AdminSQLite extends SQLiteOpenHelper { // CAMPOS DE CLASE

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventos.db";
    private static final String TABLA = "Eventos";
    private static final String COLUMN_ID = "_id";
    protected static final String COLUMN_NOMBRE = "nombre";
    protected static final String COLUMN_DESCRIPCION = "descripcion";
    protected static final String COLUMN_TIPO = "tipo";
    protected static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_HORA = "hora";
    private static final String COLUMN_DIA = "dia_semana";
    private static final String COLUMN_MYACT = "mi_actividad";


    public AdminSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREA LA TABLA DE BD CON UN QUERY
        String query = "CREATE TABLE " + TABLA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT , " +
                COLUMN_DESCRIPCION + " TEXT , " +
                COLUMN_TIPO + " TEXT , " +
                COLUMN_FECHA + " DATE, " +
                COLUMN_HORA + " TIME, " +
                COLUMN_DIA + " INTEGER, " +
                COLUMN_MYACT + " BOOLEAN DEFAULT 0" +
                ");";
        db.execSQL(query); // EJECUTA EN QUERY CUANDO SE INICIALIZA LA BD
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ESTE METODO SE EJECUTA CADA VEZ QUE LA TABLA ES EJECUTADA O CREADA
        db.execSQL("DROP TABLE IF EXISTS " + TABLA);
        onCreate(db);
    }

    //Añade un nuevo  evento a la Base de Datos
    public void createEvento (String nombre, String descripcion, String tipo,  String fecha, String hora, int dia) {
        // metodo propio; inserta valores a la tabla
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_TIPO, tipo);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_HORA, hora);
        values.put(COLUMN_DIA, dia);


        SQLiteDatabase db = getWritableDatabase(); // grabar en la tabla de la base de datos
        db.insert(TABLA, null, values); // se inserta en la tabla indicada
        db.close(); // cerrar la conexion
    }


    // Borrar un evento por su nombre de la Base de Datos

    public void deleteEvento(String nombre){
        // los datos vienen de tres tablas por tanto puede duplicarse id de evento ais que diferenciamos por el tipo tambien
        String deleteEvento = "DELETE FROM " + TABLA
                + " WHERE " + COLUMN_NOMBRE + " = '" + nombre + "';";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(deleteEvento);
        db.close();
    }

    public Cursor updateEvento(String nombreOld, String nombre, String decripcion, String fecha, String hora, String dia) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE " + TABLA + " SET " +
                COLUMN_NOMBRE + " = '" + nombre +"'," +
                COLUMN_DESCRIPCION + " = '" + decripcion +"'," +
                COLUMN_FECHA + " = '" + fecha +"'," +
                COLUMN_HORA + " = '" + hora +"'," +
                COLUMN_DIA + " = '" + dia +"'," +
                "' WHERE " + COLUMN_NOMBRE + " = '" + nombreOld + "';";

        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    /**
     * ORDENAR NOMBRES POR DIAS DE LA SEMANA  ASC
     */

    //ordenar todos los eventos de Lun a Dom Hora asc
    public Cursor getAllEvents(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " ORDER BY "+ COLUMN_FECHA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getByAscDay(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " ORDER BY "+ COLUMN_DIA +", "+
                COLUMN_FECHA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getByADay(int day){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " WHERE " + COLUMN_DIA +" = '" + day +"' ORDER BY "+ COLUMN_FECHA+", "+
                COLUMN_HORA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}
