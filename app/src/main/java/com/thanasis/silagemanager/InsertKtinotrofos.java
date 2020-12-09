package com.thanasis.silagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class InsertKtinotrofos extends AppCompatActivity {

    TextView namektin_txt, surnamektin_txt;
    Button insert_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ktinotrofos);

        namektin_txt = (TextView) findViewById(R.id.namemet_txt);
        surnamektin_txt = (TextView) findViewById(R.id.surnamemet_txt);
        namektin_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        surnamektin_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        insert_btn = (Button) findViewById(R.id.insert_btn);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String onoma = namektin_txt.getText().toString();
                String epitheto = surnamektin_txt.getText().toString();
                if(onoma.matches("") || epitheto.matches("")){
                    Toast.makeText(InsertKtinotrofos.this, "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΠΕΔΙΑ", Toast.LENGTH_LONG).show();
                }else {

                    Boolean checkinsert = databaseAccess.insertktinotrofos(onoma, epitheto);
                    if (checkinsert == true) {
                        Toast.makeText(InsertKtinotrofos.this, "ΚΑΤΑΧΩΡΗΘΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InsertKtinotrofos.this, "Η ΚΑΤΑΧΩΡΗΣΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
                    }

                    databaseAccess.close();
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
            Intent intent = new Intent(InsertKtinotrofos.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(InsertKtinotrofos.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(InsertKtinotrofos.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(InsertKtinotrofos.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}