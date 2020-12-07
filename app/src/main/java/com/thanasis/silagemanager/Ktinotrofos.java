package com.thanasis.silagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        textView2 = (TextView) findViewById(R.id.textView2);


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