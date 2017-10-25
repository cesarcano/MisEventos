package com.gmail.cesarcanojmz.miseventos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView listaMisEventos;

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



        /**
         * CARGANDO DATOS EN LA LISTA
         */
        listaMisEventos = (ListView) findViewById(R.id.lv_listaMisEventos);

        AdminSQLite dbHandler;
        dbHandler = new AdminSQLite(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        Cursor resultados = dbHandler.getAllEvents();
        ListAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                resultados,     // Pass in the cursor to bind to.
                new String[] {AdminSQLite.COLUMN_NOMBRE, AdminSQLite.COLUMN_DESCRIPCION}, // Array of cursor columns to bind to.
                new int[] {android.R.id.text1, android.R.id.text2});  // Parallel array of which template objects to bind to those columns.

        // Bind to our new adapter.
        listaMisEventos.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_AgregarEvento:
                Intent i_NvoEvento = new Intent(getBaseContext(), AgregarEvento.class);
                startActivity(i_NvoEvento);
                break;
            case  R.id.action_VerListaEventos:
                break;
            case R.id.action_MisEventos:
                break;
            case R.id.action_Salir:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
