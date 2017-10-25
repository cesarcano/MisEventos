package com.gmail.cesarcanojmz.miseventos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class AgregarEvento extends AppCompatActivity {

    final private Calendar currentDate = Calendar.getInstance();
    private DatePicker dateP_diaEvento;
    private TimePicker timeP_horaEvento;
    private EditText txt_nombreEvento;
    private EditText txt_descripcionEvento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);

        // CASTEO
        dateP_diaEvento = (DatePicker) findViewById(R.id.dateP_DiaEvento);
        timeP_horaEvento = (TimePicker) findViewById(R.id.timeP_HoraEvento);
        txt_nombreEvento = (EditText) findViewById(R.id.txt_NombreEvento);
        txt_descripcionEvento = (EditText) findViewById(R.id.txt_DescripcionEvento);

        iniParams();

    }

    private void iniParams() {
        currentDate.add(Calendar.DAY_OF_YEAR, 0);
        dateP_diaEvento.setMinDate(currentDate.getTimeInMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_GuardarEvento:
                Evento evento = new Evento(txt_nombreEvento.getText().toString(),
                        txt_descripcionEvento.getText().toString(),
                        dateP_diaEvento.getDayOfMonth(), dateP_diaEvento.getMonth(),
                        dateP_diaEvento.getYear(), timeP_horaEvento.getHour(), timeP_horaEvento.getMinute());
                dateP_diaEvento.getDayOfMonth();
                break;
            case  R.id.action_Cancelar:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
