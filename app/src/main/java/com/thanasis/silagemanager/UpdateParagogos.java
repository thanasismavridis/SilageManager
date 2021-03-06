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

public class UpdateParagogos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_paragogos);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        TextView namepar_txt = (TextView) findViewById(R.id.namepar_txt);
        TextView surnamepar_txt = (TextView) findViewById(R.id.surnamepar_txt);
        namepar_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        surnamepar_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        ArrayList<String> listpar = databaseAccess.getsurname("paragogos");
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt, listpar);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String epitheto = parent.getItemAtPosition(position).toString();

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Cursor res = databaseAccess.getDataSurname("paragogos", epitheto);
                while(res.moveToNext()){
                    namepar_txt.setText(res.getString(1));
                    surnamepar_txt.setText(res.getString(2));
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
                String name = namepar_txt.getText().toString();
                String surname = surnamepar_txt.getText().toString();
                Boolean checkinsertdata = databaseAccess.updateparagogos(name, surname);
                if(checkinsertdata==true){
                    Toast.makeText(UpdateParagogos.this, "ΚΑΤΑΧΩΡΗΘΗΚΕ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UpdateParagogos.this, "Η ΚΑΤΑΧΩΡΗΣΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(UpdateParagogos.this, name + surname, Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(UpdateParagogos.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(UpdateParagogos.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(UpdateParagogos.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(UpdateParagogos.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}