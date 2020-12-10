package com.thanasis.silagemanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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

import java.util.ArrayList;

public class DeleteKtinotrofos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_ktinotrofos);
        TextView namektin_txt = (TextView) findViewById(R.id.namektin_txt);
        TextView surnamektin_txt = (TextView) findViewById(R.id.surnamektin_txt);
        disableEditText((EditText) surnamektin_txt);
        disableEditText((EditText) namektin_txt);
        surnamektin_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Button delete_btn = (Button) findViewById(R.id.delete_btn);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        ArrayList<String> listpar = databaseAccess.getsurname("ktinotrofos");
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt, listpar);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String epitheto = parent.getItemAtPosition(position).toString();

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Cursor res = databaseAccess.getDataSurname("ktinotrofos", epitheto);
                while(res.moveToNext()){
                    namektin_txt.setText(res.getString(1));
                    surnamektin_txt.setText(res.getString(2));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                String epithetoktin = surnamektin_txt.getText().toString();
                Boolean checkdeletedata = databaseAccess.deletektinotrofos(epithetoktin);
                if(checkdeletedata==true){
                    Toast.makeText(DeleteKtinotrofos.this, "ΔΙΑΓΡΑΦΗΚΕ ΕΠΙΤΥΧΩΣ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DeleteKtinotrofos.this, "Η ΔΙΑΓΡΑΦΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
                }
                databaseAccess.close();
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
            Intent intent = new Intent(DeleteKtinotrofos.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(DeleteKtinotrofos.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(DeleteKtinotrofos.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(DeleteKtinotrofos.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
}