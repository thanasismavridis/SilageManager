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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateMetaforeas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_metaforeas);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        TextView namemet_txt = (TextView) findViewById(R.id.namemet_txt);
        TextView surnamemet_txt = (TextView) findViewById(R.id.surnamemet_txt);
        TextView ar_kikloforias_txt = (TextView) findViewById(R.id.ar_kikloforias_txt);
        namemet_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        surnamemet_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ArrayList<String> listpar = databaseAccess.getsurname("metaforeas");
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt, listpar);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String epitheto = parent.getItemAtPosition(position).toString();

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Cursor res = databaseAccess.getDataSurname("metaforeas", epitheto);
                while(res.moveToNext()){
                    namemet_txt.setText(res.getString(0));
                    surnamemet_txt.setText(res.getString(1));
                    ar_kikloforias_txt.setText(res.getString(2));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button update_btn = (Button) findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = namemet_txt.getText().toString();
                String surname = surnamemet_txt.getText().toString();
                Boolean checkinsertdata = databaseAccess.updatemetaforeas(name, surname);
                if(checkinsertdata==true){
                    Toast.makeText(UpdateMetaforeas.this, "ΚΑΤΑΧΩΡΗΘΗΚΕ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UpdateMetaforeas.this, "Η ΚΑΤΑΧΩΡΗΣΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
                }
            }
        });
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
            Intent intent = new Intent(UpdateMetaforeas.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(UpdateMetaforeas.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(UpdateMetaforeas.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(UpdateMetaforeas.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}