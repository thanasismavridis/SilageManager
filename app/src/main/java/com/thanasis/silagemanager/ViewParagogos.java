package com.thanasis.silagemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewParagogos extends AppCompatActivity {


    public EditText onomaparagogos_txt;
    public Button search_btn;
    public TextView textView5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paragogos);

        onomaparagogos_txt = (EditText) findViewById(R.id.onomaparagogos_txt);
        search_btn = (Button) findViewById(R.id.search_btn);
        textView5 = (TextView) findViewById(R.id.textView5);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
//                if(databaseAccess!=null){
//                databaseAccess.open();}
//
//                String n= onomaparagogos_txt.getText().toString();
//                String finalepitheto = databaseAccess.getEpitheto(n);
//                textView5.setText(finalepitheto);
//
//                Toast.makeText(ViewParagogos.this, databaseAccess.getEpitheto(n),Toast.LENGTH_LONG).show();
//
//                databaseAccess.close();
            }
        });
    }
}