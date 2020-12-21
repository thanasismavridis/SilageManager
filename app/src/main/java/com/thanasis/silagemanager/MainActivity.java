package com.thanasis.silagemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button insert_btn = (Button) findViewById(R.id.insert_btn);
        Button view_btn = (Button) findViewById(R.id.view_btn);
        TextView tonoi_txt = (EditText) findViewById(R.id.tonoi_txt);
        TextView eidos_txt = (EditText) findViewById(R.id.eidos_txt);
        TextView ar_kikloforias_txt = (EditText) findViewById(R.id.ar_kikloforias_txt);
        tonoi_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        eidos_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        final String[] id_paragogos = new String[1];
        ar_kikloforias_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        ArrayList<String> listpar = databaseAccess.getsurname("paragogos");
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt, listpar);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String epitheto = parent.getItemAtPosition(position).toString();
                Cursor res = databaseAccess.getDataSurname("paragogos", epitheto);
                while(res.moveToNext()){
                  id_paragogos[0] = res.getString(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, id_paragogos[0], Toast.LENGTH_LONG).show();
                String tonoi = tonoi_txt.getText().toString();
                String eidos = eidos_txt.getText().toString();
                String ar_kikloforias = ar_kikloforias_txt.getText().toString();
                Integer id_paragogosInt = Integer.parseInt(id_paragogos[0]);
                Long tonoiLong = Long.parseLong(tonoi);
                String imerominia = getDateTime();

                if(tonoi.matches("") || eidos.matches("") || ar_kikloforias.matches("")){
                    Toast.makeText(MainActivity.this, "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΠΕΔΙΑ", Toast.LENGTH_LONG).show();
                }else{
                    Boolean checkinsert = databaseAccess.insertzigisma(tonoiLong, eidos, id_paragogosInt, ar_kikloforias, imerominia);
                    if (checkinsert == true) {
                        Toast.makeText(MainActivity.this, "ΚΑΤΑΧΩΡΗΘΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Η ΚΑΤΑΧΩΡΗΣΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
                    }
                }

                tonoi_txt.setText("");
                eidos_txt.setText("");
                ar_kikloforias_txt.setText("");

            }
        });

        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, IstorikoZigismatos.class);
            startActivity(intent);

           }
        });
    }



    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    //-----------------------Menu Call---------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_home){
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(MainActivity.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(MainActivity.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(MainActivity.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}