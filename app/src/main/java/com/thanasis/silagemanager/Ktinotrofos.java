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

public class Ktinotrofos extends AppCompatActivity {

    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ktinotrofos);

        Button kataxorisi_btn = (Button) findViewById(R.id.kataxorisi_btn);
        kataxorisi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ktinotrofos.this, InsertKtinotrofos.class);
                startActivity(intent);
            }
        });

        Button enimerosi_btn = (Button) findViewById(R.id.enimerosi_btn);
        enimerosi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ktinotrofos.this, UpdateKtinotrofos.class);
                startActivity(intent);
            }
        });

        Button diagrafi_btn = (Button) findViewById(R.id.diagrafi_btn);
        diagrafi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ktinotrofos.this, DeleteKtinotrofos.class);
                startActivity(intent);
            }
        });

        Button provoli_btn = (Button) findViewById(R.id.provoli_btn);

        provoli_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                Cursor res = databaseAccess.getData("ktinotrofos");
                if(res.getCount()==0){
                    Toast.makeText(Ktinotrofos.this, "ΚΑΜΙΑ ΕΓΓΡΑΦΗ", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Όνομα: " +res.getString(1)+"\n");
                    buffer.append("Επίθετο: " +res.getString(2)+"\n\n\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Ktinotrofos.this);
                builder.setCancelable(true);
                builder.setTitle("ΚΤΗΝΟΤΡΟΦΟΙ");
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
            Intent intent = new Intent(Ktinotrofos.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(Ktinotrofos.this, Paragogos.class);
            startActivity(intent);
        }else
            if(id== R.id.menu_ktinotrofos){
                Intent intent = new Intent(Ktinotrofos.this, Ktinotrofos.class);
                startActivity(intent);
            }

        return super.onOptionsItemSelected(item);
    }
}