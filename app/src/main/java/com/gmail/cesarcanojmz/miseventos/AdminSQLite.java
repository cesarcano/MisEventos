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
                COLUMN_TIPO + " INTEGER , " +
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
    public void createEvento (String nombre, String descripcion, int tipo,  String fecha, String hora, int dia) {
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


    public Cursor updateEvento(int id, String nombre, String decripcion, int tipo, String fecha, String hora, int dia) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE " + TABLA + " SET " +
                COLUMN_NOMBRE + " = '" + nombre +"'," +
                COLUMN_DESCRIPCION + " = '" + decripcion +"'," +
                COLUMN_FECHA + " = '" + fecha +"'," +
                COLUMN_TIPO + " = '" + tipo +"'," +
                COLUMN_HORA + " = '" + hora +"'," +
                COLUMN_DIA + " = '" + dia +"'" +
                " WHERE " + COLUMN_ID + " = '" + id + "';";

        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    /**
     * ORDENAR NOMBRES POR DIAS DE LA SEMANA  ASC
     */

    /**
     * REGRESA TODA LA LISTA DE EVENTOS
     * @param
     * @return String[]
     */
    public Cursor getAllEvents(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " ORDER BY "+ COLUMN_FECHA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * REGRESA TODA LA LISTA DE EVENTOS
     * @param
     * @return String[]
     */
    public Cursor getAllMyEvents(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " WHERE " + COLUMN_MYACT +" = " + 1 +
        " ORDER BY "+ COLUMN_FECHA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * OBTENER UN TIPO DE EVENTO SIN IMPORTAR LA FECHA
     * @param  tipo
     * @return String[]
     */

    public Cursor getAllEventsByType(int tipo){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA +
                " WHERE "+ COLUMN_TIPO + " = " + tipo +
                " ORDER BY "+  COLUMN_FECHA + ", " + COLUMN_HORA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * ARROJA TODOS LOS EVENTOS EN X DIA DE LA SEMANA (EJ. LOS EVENTOS QUE HABRÁ LOS DOMINGOS SIN IMPORTAR FECHA)
     * @param
     * @return String[]
     */

    public Cursor getAllEventsByADay(int day){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " WHERE " + COLUMN_DIA +" = '" + day +"' ORDER BY "+ COLUMN_FECHA+", "+
                COLUMN_HORA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * ARROJA TODOS LOS UN TIPO DE EVENTO EN X DIA DE LA SEMANA (EJ. LOS EVENTOS DE WEB EN LOS DOMINGOS SIN IMPORTAR FECHA)
     * @return String[]
     */

    public Cursor getbyEventAndDay(int day, int evento){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " WHERE " + COLUMN_DIA +" = '" + day +"' " +
                "AND " + COLUMN_TIPO + "='" + evento + "' ORDER BY "+ COLUMN_FECHA+", "+
                COLUMN_HORA + " ASC;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    /**
     * ARROJA LOS DATOS DE UN EVENTO SEGÚN SU ID
     * @return String[]
     */
    public Cursor getEventById(int id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLA + " WHERE " + COLUMN_ID +" = " + id +" ;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * ELIMINA UN EVENTO SEGÚN SU ID
     */

    public Cursor deleteEventById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM " + TABLA + " WHERE " + COLUMN_ID +" = " + id + " ;";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * AGREGAR A MIS EVENTOS
     */

    public Cursor addToMyEvents(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE " + TABLA + " SET " + COLUMN_MYACT +" = '" + 1 +
                "' WHERE "+ COLUMN_ID +" = " + id +";";
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


}
