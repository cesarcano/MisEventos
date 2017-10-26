package com.gmail.cesarcanojmz.miseventos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ListaEventos extends AppCompatActivity {

    private ListView listaEventos;
    private TextView aviso;
    private Spinner spinnerTipo;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);
        listaEventos = (ListView) findViewById(R.id.lv_TodosLosEventos);
        aviso = (TextView) findViewById(R.id.lbl_Aviso);
        spinnerTipo = (Spinner) findViewById(R.id.spn_TipoEvento);

    }

    private void loadLista() {
        /**
         * CARGANDO DATOS EN LA LISTA
         */
        int typeEvent = spinnerTipo.getSelectedItemPosition();
        AdminSQLite dbHandler;
        dbHandler = new AdminSQLite(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor resultados = dbHandler.getAllEvents();
        Log.d("Item Selected", Integer.toString(typeEvent));
        if (typeEvent > 0) {
            resultados = dbHandler.getAllEventsByType(typeEvent);
        }
        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                resultados,
                new String[] {AdminSQLite.COLUMN_NOMBRE, AdminSQLite.COLUMN_DESCRIPCION},
                new int[] {android.R.id.text1, android.R.id.text2});

        if(resultados.getCount() > 0 ) {
            aviso.setVisibility(View.GONE);
            listaEventos.setVisibility(View.VISIBLE);
        }

        // Bind to our new adapter.
        listaEventos.setAdapter(adapter);
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_eventos, menu);
        // CREANDO SPINNER DE MENU
        item = menu.findItem(R.id.action_Spinner);
        spinnerTipo = (Spinner) MenuItemCompat.getActionView(item);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spn_adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.tipo_eventos_filtro,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spn_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTipo.setAdapter(spn_adapter);

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item", Integer.toString(position) );
                loadLista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                
            }
        });

        loadLista();
        return true;
    }

}
