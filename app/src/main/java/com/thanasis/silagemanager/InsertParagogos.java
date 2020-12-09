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

public class InsertParagogos extends AppCompatActivity {

    TextView namepar_txt, surnamepar_txt;
    Button insert_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_paragogos);

        namepar_txt = (TextView) findViewById(R.id.namepar_txt);
        surnamepar_txt = (TextView) findViewById(R.id.surnamepar_txt);
        namepar_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        surnamepar_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        insert_btn = (Button) findViewById(R.id.insert_btn);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String onoma = namepar_txt.getText().toString();
                String epitheto = surnamepar_txt.getText().toString();

                if(onoma.matches("") || epitheto.matches("")){
                    Toast.makeText(InsertParagogos.this, "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΠΕΔΙΑ", Toast.LENGTH_LONG).show();
                }else {

                    Boolean checkinsert = databaseAccess.insertparagogos(onoma, epitheto);
                    if (checkinsert == true) {
                        Toast.makeText(InsertParagogos.this, "ΚΑΤΑΧΩΡΗΘΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(InsertParagogos.this, "Η ΚΑΤΑΧΩΡΗΣΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(InsertParagogos.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(InsertParagogos.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(InsertParagogos.this, Ktinotrofos.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}