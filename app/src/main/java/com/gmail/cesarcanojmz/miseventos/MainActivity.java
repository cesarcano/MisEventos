package com.gmail.cesarcanojmz.miseventos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView listaMisEventos;
    private Spinner spn_TipoEventos;
    private Spinner spn_Dia;
    private TextView aviso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_AgregarEvento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_NvoEvento = new Intent(getBaseContext(), AgregarEvento.class);
                startActivity(i_NvoEvento);
            }
        });

        listaMisEventos = (ListView) findViewById(R.id.lv_listaMisEventos);
        spn_TipoEventos = (Spinner) findViewById(R.id.spn_TipoEventoFiltro);
        spn_Dia = (Spinner) findViewById(R.id.spn_DiaSemana);
        aviso = (TextView) findViewById(R.id.lbl_NoHayEventos);

        loadComponentes();
        loadLista();

    }

    private void loadComponentes() {
        ArrayAdapter<CharSequence> spn_adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.tipo_eventos_filtro,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spn_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spn_TipoEventos.setAdapter(spn_adapter);

        ArrayAdapter<CharSequence> spn_adapter_2 = ArrayAdapter.createFromResource(getBaseContext(), R.array.filtro_dias,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spn_adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spn_Dia.setAdapter(spn_adapter_2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_AgregarEvento:
                Intent i_NvoEvento = new Intent(getBaseContext(), AgregarEvento.class);
                startActivity(i_NvoEvento);
                break;
            case  R.id.action_VerListaEventos:
                Intent i_ListaEventos = new Intent(getBaseContext(), ListaEventos.class);
                startActivity(i_ListaEventos);
                break;
            case R.id.action_Actualizar:
                loadLista();
                break;
            case R.id.action_Salir:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLista() {
        /**
         * CARGANDO DATOS EN LA LISTA
         */

        int typeEvent = spn_TipoEventos.getSelectedItemPosition();
        int whichDay = spn_Dia.getSelectedItemPosition();
        String day = spn_Dia.getSelectedItem().toString();

        AdminSQLite dbHandler;
        dbHandler = new AdminSQLite(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor resultados = dbHandler.getAllMyEvents();
        if (whichDay == 0 && typeEvent == 0) {
            Log.d("todos los eventos", day);
            resultados = dbHandler.getAllMyEvents();
        }
        if (typeEvent > 0 && whichDay == 0 ) {
            Log.d("secific eventos anyday", day);
            resultados = dbHandler.getAllEventsByType(typeEvent);
        }
        if (typeEvent == 0 && whichDay > 0 ) {
            Log.d("any event specific day", day);
            resultados = dbHandler.getAllEventsByADay(whichDay);
        }
        if (typeEvent > 0 && whichDay > 0 ) {
            Log.d("specific day and event", day);
            resultados = dbHandler.getbyEventAndDay(whichDay, typeEvent);
        }

        if(resultados.getCount() > 0 ) {
            aviso.setVisibility(View.GONE);
            listaMisEventos.setVisibility(View.VISIBLE);
        }
        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                resultados,
                new String[] {AdminSQLite.COLUMN_NOMBRE, AdminSQLite.COLUMN_DESCRIPCION},
                new int[] {android.R.id.text1, android.R.id.text2});

        // Bind to our new adapter.
        listaMisEventos.setAdapter(adapter);
    }
}
