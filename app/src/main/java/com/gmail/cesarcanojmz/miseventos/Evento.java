package com.gmail.cesarcanojmz.miseventos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by cesar on 24/10/17.
 */

public class Evento {

    private String nombre;
    private String descripcion;
    private int tipo;
    private int numDiaSemana;
    private String hora;
    private String fecha;
    private Context context;

    public Evento(String nombre, String descripcion, int tipo, int dia, int mes, int anio, int hora, int minuto, Context contexto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.hora = setFormatHour(hora, minuto);
        this.fecha = setFormatDate(dia, mes, anio);
        this.context = contexto;
        this.numDiaSemana =  getDayOfWeek(dia, mes, anio);
    }

    private String setFormatDate(int dia, int mes, int anio) {
        String formatfecha;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        date.setDate(dia);
        date.setMonth(mes);
        date.setYear(anio- 1900);
        formatfecha = dateFormat.format(date);
        Log.d("Fecha calendar ", formatfecha);
        return formatfecha;
    }

    private String setFormatHour(int hora, int minuto) {
        String formatHora;
        //Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = Calendar.getInstance().getTime();
        date.setMinutes(minuto);
        date.setHours(hora);
        formatHora = dateFormat.format(date);
        Log.d("Hora calendar ", formatHora);
        return formatHora;
    }

    private int getDayOfWeek(int dia, int mes, int anio) {
        String diaSemana;
        int nDia = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        Date date = Calendar.getInstance().getTime();
        date.setDate(dia);
        date.setMonth(mes);
        date.setYear(anio- 1900);
        diaSemana = dateFormat.format(date);

        switch (diaSemana) {
            case "dom.":
                nDia = 7;
                break;
            case "lun.":
                nDia = 1;
                break;
            case "mar.":
                nDia = 2;
                break;
            case "mié.":
                nDia = 3;
                break;
            case "jue.":
                nDia = 4;
                break;
            case "vie.":
                nDia = 5;
                break;
            case "sáb.":
                nDia = 6;
                break;
        }

        Log.d("Dia de la semana ", diaSemana + " " + Integer.toString(nDia) );
        return nDia;
    }

    /**
     * FUNCIONES PARA CONSULTAR/ACTUALIZAR LA BD
     */

    public void crearEvento() {
        AdminSQLite dbHandler;
        dbHandler= new AdminSQLite(this.context, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        dbHandler.createEvento(this.nombre, this.descripcion, this.tipo, this.fecha, this.hora, this.numDiaSemana);
    }
}
