package com.thanasis.silagemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Paragogos extends AppCompatActivity {

    public TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paragogos);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


        Button kataxorisi_btn = (Button) findViewById(R.id.kataxorisi_btn);
        kataxorisi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Paragogos.this, InsertParagogos.class);
                startActivity(intent);
            }
        });

        Button enimerosi_btn = (Button) findViewById(R.id.enimerosi_btn);
        enimerosi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Paragogos.this, UpdateParagogos.class);
                startActivity(intent);
            }
        });

        Button diagrafi_btn = (Button) findViewById(R.id.diagrafi_btn);
        diagrafi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdelete = new Intent(Paragogos.this, DeleteParagogos.class);
                startActivity(intentdelete);
            }
        });



        Button provoli_btn = (Button) findViewById(R.id.provoli_btn);
        provoli_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Cursor res = databaseAccess.getData("paragogos");
                if(res.getCount()==0){
                    Toast.makeText(Paragogos.this, "ΚΑΜΙΑ ΕΓΓΡΑΦΗ", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Όνομα: " +res.getString(1)+"\n");
                    buffer.append("Επίθετο: " +res.getString(2)+"\n\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Paragogos.this);
                builder.setCancelable(true);
                builder.setTitle("ΠΑΡΑΓΩΓΟΙ");
                builder.setMessage(buffer.toString());
                builder.show();
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
            Intent intent = new Intent(Paragogos.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(Paragogos.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(Paragogos.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(Paragogos.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}