package com.thanasis.silagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Metaforeas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metaforeas);

        Button kataxorisi_btn = (Button) findViewById(R.id.kataxorisi_btn);
        kataxorisi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Metaforeas.this, InsertMetaforeas.class);
                startActivity(intent);
            }
        });

        //textView2 = (TextView) findViewById(R.id.textView2);
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
            Intent intent = new Intent(Metaforeas.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(Metaforeas.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(Metaforeas.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(Metaforeas.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}