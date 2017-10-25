package com.gmail.cesarcanojmz.miseventos;

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
    private String diaSemana;
    private String hora;
    private String fecha;

    public Evento(String nombre, String descripcion, int dia, int mes, int anio, int hora, int minuto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = setFormatHour(hora, minuto);
        this.fecha = setFormatDate(dia, mes, anio);
        getDayOfWeek(dia, mes, anio);
    }

    private String setFormatDate(int dia, int mes, int anio) {
        String formatfecha;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

    private void getDayOfWeek(int dia, int mes, int anio) {
        String diaSemana;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE");
        Date date = Calendar.getInstance().getTime();
        date.setDate(dia);
        date.setMonth(mes);
        date.setYear(anio- 1900);
        diaSemana = dateFormat.format(date);
        switch (diaSemana) {
            case "dom.":
                diaSemana = "domingo";
                break;
            case "lun.":
                diaSemana = "lunes";
                break;
            case "mar.":
                diaSemana = "martes";
                break;
            case "mie.":
                diaSemana = "miercoles";
                break;
            case "jue.":
                diaSemana = "jueves";
                break;
            case "vie.":
                diaSemana = "sabado";
                break;
            case "sab.":
                diaSemana = "domingo";
                break;
        }
        Log.d("Dia de la semana ", diaSemana);
        //return diaSemana;
    }
}
