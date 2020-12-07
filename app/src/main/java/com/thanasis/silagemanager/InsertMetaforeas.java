package com.thanasis.silagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class InsertMetaforeas extends AppCompatActivity {

    TextView namemet_txt, surnamemet_txt, arkikloforias_txt;
    Button insert_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_metaforeas);

        namemet_txt = (TextView) findViewById(R.id.namemet_txt);
        surnamemet_txt = (TextView) findViewById(R.id.surnamemet_txt);
        arkikloforias_txt = (TextView) findViewById(R.id.arkikloforias_txt);
        insert_btn = (Button) findViewById(R.id.insert_btn);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String onoma = namemet_txt.getText().toString();
                String epitheto = surnamemet_txt.getText().toString();
                String ar_kikloforias = arkikloforias_txt.getText().toString();

                Boolean checkinsert = databaseAccess.insertmetaforeas(onoma, epitheto, ar_kikloforias);
                if(checkinsert==true){
                    Toast.makeText(InsertMetaforeas.this, "ΚΑΤΑΧΩΡΗΘΗΚΕ ΜΕ ΕΠΙΤΥΧΙΑ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(InsertMetaforeas.this, "Η ΚΑΤΑΧΩΡΗΣΗ ΑΠΕΤΥΧΕ", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(InsertMetaforeas.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(InsertMetaforeas.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(InsertMetaforeas.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(InsertMetaforeas.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}