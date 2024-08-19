package com.example.silagemanager.Apotelesmata.Paragogos;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.nio.file.Paths.get;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.Database.ZigismataDB;
import com.example.silagemanager.R;


import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApotelesmataParagogouFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApotelesmataParagogouFragment extends Fragment {


    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "id";

    // TODO: Rename and change types of parameters
    private String mType;
    private String mId;


    public ApotelesmataParagogouFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ApotelesmataParagogouFragment newInstance(String type, String id) {
        ApotelesmataParagogouFragment fragment = new ApotelesmataParagogouFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        args.putString(ARG_PARAM2, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_PARAM1);
            mId = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    RecyclerView recyclerView;
    ApotelesmataParagogouAdapter adapter;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<HashMap<String, String>> listPar;
    ArrayList<HashMap<String, String>> listEns;
    ArrayList<HashMap<String, String>> listAut;
    ArrayList<HashMap<String, String>> listZigismata;
    ArrayList<String> list;
    String id_par = "1";
    String id_ens = "1";
    String id_aut = "1";
    TextView start_txt, end_txt, start_editBox, end_editBox, par_name, sinolo_kilon;
    ImageView download_btn;

    float sinolika_kila = 0;

    final Calendar myCalendar= Calendar.getInstance();
    final Calendar myCalendar1= Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_apotelesmata_paragogou, container, false);

        spinner = view.findViewById(R.id.spinner);
        recyclerView = view.findViewById(R.id.recyclerView);

        start_editBox = view.findViewById(R.id.start_editBox);
        end_editBox = view.findViewById(R.id.end_editBox);
        start_txt = view.findViewById(R.id.start_txt);
        end_txt = view.findViewById(R.id.end_txt);

        par_name = view.findViewById(R.id.par_name);

        sinolo_kilon = view.findViewById(R.id.sinolo_kilon);

        download_btn = view.findViewById(R.id.download_btn);

        list = new ArrayList();
        listZigismata = new ArrayList();

        ParagogosDB paragogosDB = new ParagogosDB(getContext());
        paragogosDB.open();
        listPar = paragogosDB.getParagogosInfo();
        paragogosDB.close();
        String namePar = "";
        for(int i=0; i<listPar.size(); i++){
            //Log.e("TAG", "Parragogos id: --> " + listPar.get(i).get("id") + "mID: -->" + mId );
            if (listPar.get(i).get("id").equals(mId)) {
                namePar = listPar.get(i).get("epitheto") + " "
                        + listPar.get(i).get("onoma");
            }
        }

        par_name.setText(namePar);

        if(mType.equals("general")){

            start_txt.setVisibility(View.INVISIBLE);
            start_editBox.setVisibility(View.INVISIBLE);
            end_editBox.setVisibility(View.INVISIBLE);
            end_txt.setVisibility(View.INVISIBLE);

            spinner.setVisibility(View.GONE);
            setGeneralData(mId);

        }else if(mType.equals("ensiroma")){

            start_txt.setVisibility(View.INVISIBLE);
            start_editBox.setVisibility(View.INVISIBLE);
            end_editBox.setVisibility(View.INVISIBLE);
            end_txt.setVisibility(View.INVISIBLE);

            spinner.setVisibility(View.VISIBLE);

            EnsiromaDB ensiromaDB = new EnsiromaDB(getContext());
            ensiromaDB.open();
            listEns = ensiromaDB.getEnsiromaInfo();
            ensiromaDB.close();

            for(int i=0; i<listEns.size(); i++){
                list.add(listEns.get(i).get("eidos"));
            }

            spinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, list);

            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(listEns.size() == list.size()){
                        id_ens = listEns.get(i).get("id");
                        Log.e("TAG", "onItemSelected: " + id_ens );
                    }
                    setEnsiromaData(id_ens);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else if(mType.equals("autokinito")){

            start_txt.setVisibility(View.INVISIBLE);
            start_editBox.setVisibility(View.INVISIBLE);
            end_editBox.setVisibility(View.INVISIBLE);
            end_txt.setVisibility(View.INVISIBLE);

            spinner.setVisibility(View.VISIBLE);

            MetaforeasDB metaforeasDB = new MetaforeasDB(getContext());
            metaforeasDB.open();
            listAut = metaforeasDB.getMetaforeasInfo();
            metaforeasDB.close();

            for(int i=0; i<listAut.size(); i++){
                list.add(listAut.get(i).get("ar_kikloforias"));
            }

            spinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, list);

            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(listAut.size() == list.size()){
                        id_aut = listAut.get(i).get("id");
                        Log.e("TAG", "onItemSelected: " + id_aut );
                    }
                    setAutokinitaData(id_aut);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }else if (mType.equals("date")){

            start_txt.setVisibility(View.VISIBLE);
            start_editBox.setVisibility(View.VISIBLE);
            end_editBox.setVisibility(View.VISIBLE);
            end_txt.setVisibility(View.VISIBLE);

            DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH,month);
                    myCalendar.set(Calendar.DAY_OF_MONTH,day);

                    String myFormat="yyyy-MM-dd";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
                    start_editBox.setText(dateFormat.format(myCalendar.getTime()));

                }
            };

            DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {

                    myCalendar1.set(Calendar.YEAR, year);
                    myCalendar1.set(Calendar.MONTH,month);
                    myCalendar1.set(Calendar.DAY_OF_MONTH,day);

                    String myFormat="yyyy-MM-dd";
                    SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
                    end_editBox.setText(dateFormat.format(myCalendar1.getTime()));

                    setDateData(String.valueOf(start_editBox.getText()), String.valueOf(end_editBox.getText()));
                }
            };

            start_editBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            end_editBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(getContext(),date1,myCalendar1.get(Calendar.YEAR),myCalendar1.get(Calendar.MONTH),myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        }

        download_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });

        return view;
    }

    private void setGeneralData(String id){

       sinolika_kila = 0;
       listZigismata.clear();
        ZigismataDB zigismataDB = new ZigismataDB(getContext());
        zigismataDB.open();

        for(int i=0; i<zigismataDB.getZigismataByPar().size(); i++) {
            if(zigismataDB.getZigismataByPar().get(i).get("id_paragogos").equals(id)){
                HashMap<String, String> tmpMap = new HashMap<>();

                String id_zigismatos = zigismataDB.getZigismataByPar().get(i).get("id");
                String date = zigismataDB.getZigismataByPar().get(i).get("date");
                String mikto = zigismataDB.getZigismataByPar().get(i).get("mikto");
                String id_paragogos = zigismataDB.getZigismataByPar().get(i).get("id_paragogos");
                String autokinito = zigismataDB.getZigismataByPar().get(i).get("autokinito");
                String ensiroma = zigismataDB.getZigismataByPar().get(i).get("ensiroma");
                String ofelimo = zigismataDB.getZigismataByPar().get(i).get("ofelimo");
                String apovaro = zigismataDB.getZigismataByPar().get(i).get("apovaro");

                Log.e("TAG", "onBindViewHolder: Results " + "Autokinhto: " + autokinito );

                sinolika_kila = sinolika_kila + Float.valueOf(ofelimo);

                tmpMap.put("id", id_zigismatos);
                tmpMap.put("date", date);
                tmpMap.put("mikto", mikto);
                tmpMap.put("id_paragogos", id_paragogos);
                tmpMap.put("autokinito", autokinito);
                tmpMap.put("ensiroma", ensiroma);
                tmpMap.put("ofelimo", ofelimo);
                tmpMap.put("apovaro", apovaro);

                listZigismata.add(tmpMap);
            }
        }
//        Log.e("TAG", "setData: " + zigismataDB.getZigismataByPar(id_par));
        zigismataDB.close();

        if(listZigismata.size() > 0) {
            adapter = new ApotelesmataParagogouAdapter(getContext(), listZigismata);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getContext(), "Δεν υπάρχουν διαθέσιμες πληροφορίες.", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }

        sinolo_kilon.setText(sinolika_kila + " Kg");

    }

    private void setEnsiromaData(String id){

        sinolika_kila = 0;
        listZigismata.clear();
        ZigismataDB zigismataDB = new ZigismataDB(getContext());
        zigismataDB.open();

        for(int i=0; i<zigismataDB.getZigismataByPar().size(); i++) {
            Log.e("TAG", "setEnsiromaData: " + zigismataDB.getZigismataByPar().get(i).get("eidos_id") + "Paragogos: " + zigismataDB.getZigismataByPar().get(i).get("id_paragogos"));
            if(zigismataDB.getZigismataByPar().get(i).get("id_paragogos").equals(mId) && zigismataDB.getZigismataByPar().get(i).get("eidos_id").equals(id)){
                HashMap<String, String> tmpMap = new HashMap<>();

                //Log.e("TAG", "setEnsiromaData: --->" );

                String id_zigismatos = zigismataDB.getZigismataByPar().get(i).get("id");
                String date = zigismataDB.getZigismataByPar().get(i).get("date");
                String mikto = zigismataDB.getZigismataByPar().get(i).get("mikto");
                String id_paragogos = zigismataDB.getZigismataByPar().get(i).get("id_paragogos");
                String autokinito = zigismataDB.getZigismataByPar().get(i).get("autokinito");
                String ensiroma = zigismataDB.getZigismataByPar().get(i).get("ensiroma");
                String ofelimo = zigismataDB.getZigismataByPar().get(i).get("ofelimo");
                String apovaro = zigismataDB.getZigismataByPar().get(i).get("apovaro");

                sinolika_kila = sinolika_kila + Float.valueOf(ofelimo);

                tmpMap.put("id", id_zigismatos);
                tmpMap.put("date", date);
                tmpMap.put("mikto", mikto);
                tmpMap.put("id_paragogos", id_paragogos);
                tmpMap.put("autokinito", autokinito);
                tmpMap.put("ensiroma", ensiroma);
                tmpMap.put("ofelimo", ofelimo);
                tmpMap.put("apovaro", apovaro);

                listZigismata.add(tmpMap);
            }
        }
//        Log.e("TAG", "setData: " + zigismataDB.getZigismataByPar(id_par));
        zigismataDB.close();

        if(listZigismata.size() > 0) {
            adapter = new ApotelesmataParagogouAdapter(getContext(), listZigismata);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getContext(), "Δεν υπάρχουν διαθέσιμες πληροφορίες.", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }

        sinolo_kilon.setText(sinolika_kila + " Kg");
    }

    private void setAutokinitaData(String id){

        sinolika_kila = 0;
        listZigismata.clear();
        ZigismataDB zigismataDB = new ZigismataDB(getContext());
        zigismataDB.open();

        for(int i=0; i<zigismataDB.getZigismataByPar().size(); i++) {
            //Log.e("TAG", "setEnsiromaData: " + zigismataDB.getZigismataByPar().get(i).get("eidos_id") + "Paragogos: " + zigismataDB.getZigismataByPar().get(i).get("id_paragogos"));
            if(zigismataDB.getZigismataByPar().get(i).get("id_paragogos").equals(mId) && zigismataDB.getZigismataByPar().get(i).get("autokinito_id").equals(id)){
                HashMap<String, String> tmpMap = new HashMap<>();

                Log.e("TAG", "setEnsiromaData: --->" );

                String id_zigismatos = zigismataDB.getZigismataByPar().get(i).get("id");
                String date = zigismataDB.getZigismataByPar().get(i).get("date");
                String mikto = zigismataDB.getZigismataByPar().get(i).get("mikto");
                String id_paragogos = zigismataDB.getZigismataByPar().get(i).get("id_paragogos");
                String autokinito = zigismataDB.getZigismataByPar().get(i).get("autokinito");
                String ensiroma = zigismataDB.getZigismataByPar().get(i).get("ensiroma");
                String ofelimo = zigismataDB.getZigismataByPar().get(i).get("ofelimo");
                String apovaro = zigismataDB.getZigismataByPar().get(i).get("apovaro");

                sinolika_kila = sinolika_kila + Float.valueOf(ofelimo);

                tmpMap.put("id", id_zigismatos);
                tmpMap.put("date", date);
                tmpMap.put("mikto", mikto);
                tmpMap.put("id_paragogos", id_paragogos);
                tmpMap.put("autokinito", autokinito);
                tmpMap.put("ensiroma", ensiroma);
                tmpMap.put("ofelimo", ofelimo);
                tmpMap.put("apovaro", apovaro);

                listZigismata.add(tmpMap);
            }
        }
//        Log.e("TAG", "setData: " + zigismataDB.getZigismataByPar(id_par));
        zigismataDB.close();

        if(listZigismata.size() > 0) {
            adapter = new ApotelesmataParagogouAdapter(getContext(), listZigismata);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getContext(), "Δεν υπάρχουν διαθέσιμες πληροφορίες.", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }
        sinolo_kilon.setText(sinolika_kila + " Kg");
    }

    private void setDateData(String start_date, String end_date){

        sinolika_kila = 0;
        listZigismata.clear();
        ZigismataDB zigismataDB = new ZigismataDB(getContext());
        zigismataDB.open();

        for(int i=0; i<zigismataDB.getZigismataParByDate(start_date, end_date).size(); i++) {
            if(zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("id_paragogos").equals(mId)){
                HashMap<String, String> tmpMap = new HashMap<>();

                String id_zigismatos = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("id");
                String date = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("date");
                String mikto = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("mikto");
                String id_paragogos = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("id_paragogos");
                String autokinito = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("autokinito");
                String ensiroma = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("ensiroma");
                String ofelimo = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("ofelimo");
                String apovaro = zigismataDB.getZigismataParByDate(start_date, end_date).get(i).get("apovaro");

                //Log.e("TAG", "onBindViewHolder: Results " + "Ensiroma: " + ensiroma + "Arithoms: " + apovaro);

                sinolika_kila = sinolika_kila + Float.valueOf(ofelimo);

                tmpMap.put("id", id_zigismatos);
                tmpMap.put("date", date);
                tmpMap.put("mikto", mikto);
                tmpMap.put("id_paragogos", id_paragogos);
                tmpMap.put("autokinito", autokinito);
                tmpMap.put("ensiroma", ensiroma);
                tmpMap.put("ofelimo", ofelimo);
                tmpMap.put("apovaro", apovaro);

                listZigismata.add(tmpMap);
            }
        }
//        Log.e("TAG", "setData: " + zigismataDB.getZigismataByPar(id_par));
        zigismataDB.close();

        if(listZigismata.size() > 0) {
            adapter = new ApotelesmataParagogouAdapter(getContext(), listZigismata);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getContext(), "Δεν υπάρχουν διαθέσιμες πληροφορίες.", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }
        sinolo_kilon.setText(sinolika_kila + " Kg");
    }

    PdfDocument myPdfDocument;
    PdfDocument.PageInfo myPageInfo;
    PdfDocument.Page myPage;
    Canvas canvas;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createPDF()  {

        myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();

        //create the PDF document
        String fileName = "exp" + getDateTime();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                + "/Διαχείριση ενσιρώματος" , fileName + ".pdf");

        int recordPrinted = 0;
        int pageCreated = 0;
        int printCount = 0;
        float kila = 0;
        boolean createPage = true;
        String namePar = "";
        for(int i=0; i<listPar.size(); i++){
            if (listPar.get(i).get("id").equals(mId)) {
                namePar = listPar.get(i).get("epitheto") + " "
                        + listPar.get(i).get("onoma");
            }
        }

        for(int i = 0; i < listZigismata.size(); i++) {
            if(createPage){
                myPageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageCreated + 1).create();
                myPage = myPdfDocument.startPage(myPageInfo);
                canvas = myPage.getCanvas();
                myPaint.setTextSize(25);
                canvas.drawText("Παραγωγός: " + namePar, 20, 50, myPaint);
//                myPaint.setTextSize(18);
//                canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 120 + (700), myPaint );
            }
            myPaint.setTextSize(25);
            canvas.drawText("*** ΖΥΓΙΣΜΑ " + listZigismata.get(i).get("id") + " ***" , 20, 90 + (250*printCount), myPaint);
            myPaint.setTextSize(18);
            canvas.drawText("Ημερομηνία: " + listZigismata.get(i).get("date"), 20, 120 + (250*printCount), myPaint);
            canvas.drawText("Ενσίρωμα: " + listZigismata.get(i).get("ensiroma"), 20, 150 + (250*printCount), myPaint);
            canvas.drawText("Αυτοκίνητο: " + listZigismata.get(i).get("autokinito"), 20, 180 + (250*printCount), myPaint);
            canvas.drawText("Καθαρό: " + listZigismata.get(i).get("ofelimo") + " Kg", 20, 210 + (250*printCount), myPaint);
            canvas.drawText("Απόβαρο: " + listZigismata.get(i).get("apovaro") + " Kg", 20, 240 + (250*printCount), myPaint);
            canvas.drawText("Μικτό: " + listZigismata.get(i).get("mikto") + " Kg", 20, 270 + (250*printCount), myPaint);
            recordPrinted++;
            printCount ++;
            kila = kila + Float.valueOf(listZigismata.get(i).get("ofelimo"));
            if (recordPrinted % 3 == 0){
                myPaint.setTextSize(18);
                canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 120 + (700), myPaint );
            }
            if(recordPrinted != 0 && recordPrinted % 3 == 0 ){
                pageCreated++;
                myPdfDocument.finishPage(myPage);
                createPage = true;
                printCount = 0;
            }else{
                createPage = false;
            }
        }


        //canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 120 + (250*printCount), myPaint );
//        if (printCount == 0){
//
//        }else if (printCount == 1){
//            canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 120 + (250*printCount), myPaint );
//        }else if (printCount == 2){
//            canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 120 + (250*printCount), myPaint );
//        }else if (printCount == 3){
//            //canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 120 + (100*printCount), myPaint );
//        }
//        myPaint.setTextSize(25);
//        canvas.drawText("Παραγωγός: " + namePar, 20, 50, myPaint);
//        canvas.drawText("*** ΖΥΓΙΣΜΑ " + listZigismata.get(0).get("id") + " ***" , 20, 90, myPaint);
//        myPaint.setTextSize(18);
//        canvas.drawText("Ηεμερομηνία: " + listZigismata.get(0).get("date"), 20, 120, myPaint);
//        canvas.drawText("Ενσύρωμα: " + listZigismata.get(0).get("ensiroma"), 20, 150, myPaint);
//        canvas.drawText("Αυτοκίνητο: " + listZigismata.get(0).get("autokinito"), 20, 180, myPaint);
//        canvas.drawText("Καθαρό: " + listZigismata.get(0).get("ofelimo") + " Kg", 20, 210, myPaint);
//        canvas.drawText("Απόβαρο: " + listZigismata.get(0).get("apovaro") + " Kg", 20, 240, myPaint);
//        canvas.drawText("Μικτό: " + listZigismata.get(0).get("mikto") + " Kg", 20, 270, myPaint);
//
//        myPaint.setTextSize(25);
//        canvas.drawText("*** ΖΥΓΙΣΜΑ " + listZigismata.get(1).get("id") + " ***" , 20, 90 + 250, myPaint);
//        myPaint.setTextSize(18);
//        canvas.drawText("Ηεμερομηνία: " + listZigismata.get(1).get("date"), 20, 120 + 250, myPaint);
//        canvas.drawText("Ενσύρωμα: " + listZigismata.get(1).get("ensiroma"), 20, 150 + 250, myPaint);
//        canvas.drawText("Αυτοκίνητο: " + listZigismata.get(1).get("autokinito"), 20, 180 + 250, myPaint);
//        canvas.drawText("Καθαρό: " + listZigismata.get(1).get("ofelimo") + " Kg", 20, 210 + 250, myPaint);
//        canvas.drawText("Απόβαρο: " + listZigismata.get(1).get("apovaro") + " Kg", 20, 240 + 250, myPaint);
//        canvas.drawText("Μικτό: " + listZigismata.get(1).get("mikto") + " Kg", 20, 270 + 250, myPaint);
//
//        myPaint.setTextSize(25);
//        canvas.drawText("*** ΖΥΓΙΣΜΑ " + listZigismata.get(1).get("id") + " ***" , 20, 90 + (250*2), myPaint);
//        myPaint.setTextSize(18);
//        canvas.drawText("Ηεμερομηνία: " + listZigismata.get(1).get("date"), 20, 120 + (250*2), myPaint);
//        canvas.drawText("Ενσύρωμα: " + listZigismata.get(1).get("ensiroma"), 20, 150 + (250*2), myPaint);
//        canvas.drawText("Αυτοκίνητο: " + listZigismata.get(1).get("autokinito"), 20, 180 + (250*2), myPaint);
//        canvas.drawText("Καθαρό: " + listZigismata.get(1).get("ofelimo") + " Kg", 20, 210 + (250*2), myPaint);
//        canvas.drawText("Απόβαρο: " + listZigismata.get(1).get("apovaro") + " Kg", 20, 240 + (250*2), myPaint);
//        canvas.drawText("Μικτό: " + listZigismata.get(1).get("mikto") + " Kg", 20, 270 + (250*2), myPaint);
        //canvas.drawText("Συνολικά κιλά: " + kila + " Κg",20, 300 + (250*printCount), myPaint );


        //save the PDF document
        Boolean success = false;
        try {
            if (checkPermission()) {

                if(!createPage){
                    myPdfDocument.finishPage(myPage);
                }
                //
                myPdfDocument.writeTo(Files.newOutputStream(file.toPath()));
                myPdfDocument.close();
                success = true;
            } else {
                requestPermission();
            }

        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        }finally {
            if(success) {
                Toast.makeText(getContext(), "Η εξαγωγή ολοκληρώθηκε.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(), "Η εξαγωγή απέτυχε. Προσπαθήστε ξανά.", Toast.LENGTH_LONG).show();
            }
        }

    }

    private Bitmap getBitmapFromView(View view) {

        //define bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        //bind canvas to it
        Canvas canvas = new Canvas(returnedBitmap);

        //get the view's background
        Drawable bgDrawable = view.getBackground();
        if(bgDrawable != null){
            bgDrawable.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }

        //draw the view on the canvas
        view.draw(canvas);

        return returnedBitmap;
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        requestPermissionLauncher.launch(WRITE_EXTERNAL_STORAGE);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        // PERMISSION GRANTED
                        try {
                            File ext = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                            File newDir = new File(ext + "/" + "Διαχείριση ενσιρώματος");
                            if (!newDir.exists()) {
                                newDir.mkdir();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Log.e("TAG", "onActivityResult: " + ex );
                        }
                        Toast.makeText(getContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                    } else {
                        // PERMISSION NOT GRANTED
                    }
                }
            }
    );

    private String getDateTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}