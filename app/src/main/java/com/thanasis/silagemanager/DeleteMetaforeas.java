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

public class DeleteMetaforeas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_metaforeas);
        TextView namemet_txt = (TextView) findViewById(R.id.namemet_txt);
        TextView surnamemet_txt = (TextView) findViewById(R.id.surnamemet_txt);
        TextView ar_kikloforias_txt = (TextView) findViewById(R.id.ar_kikloforias_txt);
        disableEditText((EditText) surnamemet_txt);
        disableEditText((EditText) namemet_txt);
        disableEditText((EditText) ar_kikloforias_txt);
        surnamemet_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        Button delete_btn = (Button) findViewById(R.id.delete_btn);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
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
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                String epithetomet = surnamemet_txt.getText().toString();
                Boolean checkdeletedata = databaseAccess.deletemetaforeas(epithetomet);
                if(checkdeletedata==true){
                    Toast.makeText(DeleteMetaforeas.this, "ΔΙΑΓΡΑΦΗΚΕ ΕΠΙΤΥΧΩΣ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DeleteMetaforeas.this, "Η ΔΙΑΓΡΑΦΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(DeleteMetaforeas.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(DeleteMetaforeas.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(DeleteMetaforeas.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(DeleteMetaforeas.this, Metaforeas.class);
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