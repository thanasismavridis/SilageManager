package com.thanasis.silagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }else
        if(id == R.id.menu_paragogos) {
            Intent intent = new Intent(MainActivity.this, Paragogos.class);
            startActivity(intent);
        }else
        if(id== R.id.menu_ktinotrofos){
            Intent intent = new Intent(MainActivity.this, Ktinotrofos.class);
            startActivity(intent);
        }else
        if(id==R.id.menu_metaforeas){
            Intent intent = new Intent(MainActivity.this, Metaforeas.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}