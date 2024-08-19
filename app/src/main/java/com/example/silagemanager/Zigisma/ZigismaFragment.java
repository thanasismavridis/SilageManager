package com.example.silagemanager.Zigisma;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.silagemanager.AppService;
import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.Database.ZigismataDB;
import com.example.silagemanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZigismaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZigismaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ZigismaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZigismaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZigismaFragment newInstance(String param1, String param2) {
        ZigismaFragment fragment = new ZigismaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    ArrayAdapter<String> adapter;
    ArrayList<String> listmet;
    Spinner sp;
    ParagogosDB paragogosDB;
    EnsiromaDB ensiromaDB;
    EditText ofelimo, mikto, apovaro;
    EditText paragogos_of_day;
    String oxima;
    String id_par = "";
    String id_met = "";
    String id_ens = "";
    String posotita_ofelimou = "";
    String posotita_miktou = "";
    String posotita_apovarou = "";
    ImageView calculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_zigisma, container, false);


        TextView year = view.findViewById(R.id.year);
        year.setText(AppService.year);

        paragogos_of_day = view.findViewById(R.id.paragogos_of_day);

        paragogosDB = new ParagogosDB(getContext());
        paragogosDB.open();
        for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {
            if(paragogosDB.getParagogosInfo().get(i).get("id").equals(AppService.paragogos_of_day)) {
                id_par = paragogosDB.getParagogosInfo().get(i).get("id");
                paragogos_of_day.setText(paragogosDB.getParagogosInfo().get(i).get("epitheto") + " "
                        + paragogosDB.getParagogosInfo().get(i).get("onoma"));
            }
        }
        paragogosDB.close();
        disableEditText(paragogos_of_day);

        EditText eidos = view.findViewById(R.id.eidos);
        eidos.setText(AppService.ensiroma_of_day);
        ensiromaDB = new EnsiromaDB(getContext());
        ensiromaDB.open();
        for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {
            if(ensiromaDB.getEnsiromaInfo().get(i).get("id").equals(AppService.ensiroma_of_day))
                id_ens = ensiromaDB.getEnsiromaInfo().get(i).get("id");
           // listmet.add("("+ensiromaDB.getEnsiromaInfo().get(i).get("id") +") "+ ensiromaDB.getEnsiromaInfo().get(i).get("eidos"));
            eidos.setText(ensiromaDB.getEnsiromaInfo().get(i).get("eidos"));
        }
        ensiromaDB.close();
        disableEditText(eidos);

        listmet = AppService.cars_of_day;

        sp = view.findViewById(R.id.spinner_oxima);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = sp.getItemAtPosition(i).toString();
                id_met = str.substring(str.indexOf("(")+1, str.indexOf(")"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listmet);
        sp.setAdapter(adapter);

        ofelimo = view.findViewById(R.id.ofelimo);
        mikto = view.findViewById(R.id.mikto);
        apovaro = view.findViewById(R.id.apovaro);
        calculate = view.findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ofelimo_ = Float.parseFloat(ofelimo.getText().toString());
                float apovaro_ = Float.parseFloat(apovaro.getText().toString());

                float sum = ofelimo_ + apovaro_;
                mikto.setText(String.valueOf(sum));
            }
        });

        Button insert_btn = view.findViewById(R.id.insert_btn);
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posotita_ofelimou = ofelimo.getText().toString();
                posotita_miktou = mikto.getText().toString();
                posotita_apovarou = apovaro.getText().toString();
                Log.e("TAG", "onClick: " + id_par + " " + id_met + " " + id_ens + " " + posotita_apovarou );

                ZigismataDB zigismataDB = new ZigismataDB(getContext());
                zigismataDB.open();
                zigismataDB.manageEntry(posotita_miktou, id_ens, id_par, id_met, getDateTime(), posotita_ofelimou, posotita_apovarou);
                zigismataDB.close();
                ofelimo.setText("");
                mikto.setText("");
                apovaro.setText("");
            }
        });

//        Button view_btn = view.findViewById(R.id.view_btn);
//        view_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ZigismataDB zigismataDB = new ZigismataDB(getContext());
//                zigismataDB.open();
//                Log.e("TAG", "onClick: " + zigismataDB.getZigismaInfo());
//                zigismataDB.getZigismaInfo();
//                zigismataDB.close();
//            }
//        });

        return view;
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(true);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        //editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private String getDateTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}