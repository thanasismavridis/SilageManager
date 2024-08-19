package com.example.silagemanager.Metaforeas;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateMetaforeasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateMetaforeasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateMetaforeasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateMetaforeasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateMetaforeasFragment newInstance(String param1, String param2) {
        UpdateMetaforeasFragment fragment = new UpdateMetaforeasFragment();
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
    Spinner sp;
    ArrayAdapter<String> adapter;
    ArrayList<String> listmet;
    String id_metaforeas = "";
    MetaforeasDB metaforeasDB;
    int spinner_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_metaforeas, container, false);

        EditText namemet_txt = view.findViewById(R.id.namemet_txt);
        EditText surnamemet_txt = view.findViewById(R.id.surnamemet_txt);
        EditText ar_kikloforias_txt = view.findViewById(R.id.ar_kikloforias_txt);
        EditText anagnoristiko_txt = view.findViewById(R.id.anagnoristiko_txt);

        sp = view.findViewById(R.id.spinner);
        listmet = new ArrayList();
        metaforeasDB = new MetaforeasDB(getContext());
        metaforeasDB.open();
        for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {

            listmet.add("("+metaforeasDB.getMetaforeasInfo().get(i).get("id") +") "+ metaforeasDB.getMetaforeasInfo().get(i).get("epitheto") + " "
                    + metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
        }

        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listmet);
        sp.setAdapter(adapter);
        metaforeasDB.close();

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_par = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                id_metaforeas = id_par;
                metaforeasDB.open();
                Log.e("TAG", "onItemSelected: " + id_par );
                for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {
                    if(metaforeasDB.getMetaforeasInfo().get(i).get("id").equals(id_par)){
                        Log.e("TAG", "onItemSelected: " + metaforeasDB.getMetaforeasInfo().get(i).get("id") );
                        namemet_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("onoma"));
                        surnamemet_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("epitheto"));
                        ar_kikloforias_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
                        anagnoristiko_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("anagnoristiko"));
                    }
                }
                metaforeasDB.close();
                spinner_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button update_btn = view.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = namemet_txt.getText().toString();
                String surname = surnamemet_txt.getText().toString();
                String ar_kikloforias = ar_kikloforias_txt.getText().toString();
                String anagnoristiko = anagnoristiko_txt.getText().toString();

                metaforeasDB.open();
                for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {
                    if(metaforeasDB.getMetaforeasInfo().get(i).get("id").equals(id_metaforeas)){
                        metaforeasDB.updateEntry(id_metaforeas, surname, name, ar_kikloforias, anagnoristiko);
                    }
                }


                listmet.clear();
                for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {

                    listmet.add("("+metaforeasDB.getMetaforeasInfo().get(i).get("id") +") "+ metaforeasDB.getMetaforeasInfo().get(i).get("epitheto") + " "
                            + metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
                }
                metaforeasDB.close();

                adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listmet);
                sp.setAdapter(adapter);
                sp.setSelection(spinner_position);
            }
        });



        return view;
    }
}