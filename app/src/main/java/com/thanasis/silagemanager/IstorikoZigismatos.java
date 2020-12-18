package com.thanasis.silagemanager;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IstorikoZigismatos extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> imerominia, eidos, tonoi, paragogos_epitheto, paragogos_onoma, metaforeas_epitheto, metaforeas_onoma, ar_kikloforias;
    IstorikoZigismatosAdapter istorikoZigismatosAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoriko_zigismatos);
        recyclerView = findViewById(R.id.recyclerView);
        imerominia = new ArrayList<>();
        eidos = new ArrayList<>();
        tonoi = new ArrayList<>();
        paragogos_epitheto = new ArrayList<>();
        paragogos_onoma = new ArrayList<>();
        metaforeas_epitheto = new ArrayList<>();
        metaforeas_onoma = new ArrayList<>();
        ar_kikloforias = new ArrayList<>();

        displayData();
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        istorikoZigismatosAdapter = new IstorikoZigismatosAdapter(IstorikoZigismatos.this, imerominia, eidos, tonoi, paragogos_epitheto, paragogos_onoma, metaforeas_epitheto,
                                                                    metaforeas_onoma, ar_kikloforias);
        recyclerView.setAdapter(istorikoZigismatosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(IstorikoZigismatos.this));
    }

    void displayData(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        Cursor cursor = databaseAccess.getZigismaData();
        if(cursor.getCount() == 0){
            Toast.makeText(IstorikoZigismatos.this, "ΔΕΝ ΥΠΑΡΧΟΥΝ ΔΕΔΟΜΕΝΑ", Toast.LENGTH_LONG).show();
        }else{
            while(cursor.moveToNext()){
                imerominia.add(cursor.getString(0));
                eidos.add(cursor.getString(1));
                tonoi.add(cursor.getString(2));
                paragogos_epitheto.add(cursor.getString(3));
                paragogos_onoma.add(cursor.getString(4));
                metaforeas_epitheto.add(cursor.getString(5));
                metaforeas_onoma.add(cursor.getString(6));
                ar_kikloforias.add(cursor.getString(7));
            }
        }
    }

}