package com.gmail.cesarcanojmz.miseventos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditarEvento extends AppCompatActivity {

    private DatePicker dateP_diaEvento;
    private TimePicker timeP_horaEvento;
    private EditText txt_nombreEvento;
    private EditText txt_descripcionEvento;
    private Spinner spn_TipoEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_evento);

        // CASTEO
        dateP_diaEvento = (DatePicker) findViewById(R.id.dateP_DiaEvento);
        timeP_horaEvento = (TimePicker) findViewById(R.id.timeP_HoraEvento);
        txt_nombreEvento = (EditText) findViewById(R.id.txt_NombreEvento);
        txt_descripcionEvento = (EditText) findViewById(R.id.txt_DescripcionEvento);

        spn_TipoEvento = (Spinner) findViewById(R.id.spn_TipoEvento);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spn_adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.tipo_eventos_array,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spn_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        spn_TipoEvento.setAdapter(spn_adapter);

        // OBTENIENDO ID
        Bundle extras = getIntent().getExtras();
        int id = (int) extras.getLong("id_Evento");
        loadDatosFromDB(id);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_evento, menu);
        menu.findItem(R.id.action_AddToMyEvents).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_GuardarEvento:
                Evento evento = new Evento(
                        txt_nombreEvento.getText().toString().isEmpty()?"Sin nombre":  txt_nombreEvento.getText().toString(),
                        txt_descripcionEvento.getText().toString().isEmpty()?"Sin descripción":  txt_descripcionEvento.getText().toString(),
                        spn_TipoEvento.getSelectedItemPosition() + 1,
                        dateP_diaEvento.getDayOfMonth(),
                        dateP_diaEvento.getMonth(),
                        dateP_diaEvento.getYear(),
                        timeP_horaEvento.getHour(),
                        timeP_horaEvento.getMinute(),
                        getBaseContext());
                Toast.makeText(getBaseContext(), "Evento creado ", Toast.LENGTH_LONG);
                finish();
                evento.crearEvento();
                break;
            case  R.id.action_Cancelar:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    // RELLENANDO LOS CAMPOS
    public void loadDatosFromDB (int id) {
        String aux = "";
        String args [];
        // SE CONSULTA A LA BD PARA PODER OBTENER LOS DATOS DEL ITEM PREVIAMENTE SELECCIONADO
        AdminSQLite dbHandler;
        dbHandler = new AdminSQLite(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor resultados = dbHandler.getEventById(id);

        txt_nombreEvento.setText(resultados.getString(1));
        txt_descripcionEvento.setText(resultados.getString(2));
        spn_TipoEvento.setSelection(Integer.parseInt(resultados.getString(3)) - 1);
        // OBTENIENDO FECHA
        aux = resultados.getString(4);
        args =  aux.split("-");
        dateP_diaEvento.updateDate(Integer.parseInt(args[0]), Integer.parseInt(args[1]) - 1, Integer.parseInt(args[2]));
        aux = resultados.getString(5);
        args =  aux.split(":");
        timeP_horaEvento.setHour(Integer.parseInt(args[0]));
        timeP_horaEvento.setMinute(Integer.parseInt(args[1]));

    }

    // CARGAR CALENDARIO
    private void loadCalendar() {

    }
}
