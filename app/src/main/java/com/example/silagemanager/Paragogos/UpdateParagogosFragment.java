package com.example.silagemanager.Paragogos;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateParagogosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateParagogosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateParagogosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateParagogosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateParagogosFragment newInstance(String param1, String param2) {
        UpdateParagogosFragment fragment = new UpdateParagogosFragment();
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
    String id_paragogos = "";
    Spinner sp;
    ArrayAdapter<String> adapter;
    Activity activity;
    ParagogosDB paragogosDB;
    ArrayList<String> listpar;
    int spinner_postition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_paragogos, container, false);

        activity = getActivity();
        listpar = new ArrayList();
        paragogosDB = new ParagogosDB(getContext());
        paragogosDB.open();
        for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {

            listpar.add("("+paragogosDB.getParagogosInfo().get(i).get("id") +") "+ paragogosDB.getParagogosInfo().get(i).get("epitheto") + " "
                    + paragogosDB.getParagogosInfo().get(i).get("onoma"));
        }
        paragogosDB.close();

        sp = view.findViewById(R.id.spinner);
        TextView namepar_txt = view.findViewById(R.id.namepar_txt);
        TextView surnamepar_txt = view.findViewById(R.id.surnamepar_txt);

        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpar);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_par = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                id_paragogos = id_par;
                paragogosDB.open();
                Log.e("TAG", "onItemSelected: " + id_par);
                for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {
                    if(paragogosDB.getParagogosInfo().get(i).get("id").equals(id_par)){
                        Log.e("TAG", "onItemSelected: " + paragogosDB.getParagogosInfo().get(i).get("id") );
                        namepar_txt.setText(paragogosDB.getParagogosInfo().get(i).get("onoma"));
                        surnamepar_txt.setText(paragogosDB.getParagogosInfo().get(i).get("epitheto"));
                    }
                }
                paragogosDB.close();

                //save position to set it again later
                spinner_postition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button update_btn = view.findViewById(R.id.update_btn);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = namepar_txt.getText().toString();
                String surname = surnamepar_txt.getText().toString();
                ParagogosDB paragogosDB = new ParagogosDB(getContext());
                paragogosDB.open();
                for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {
                    if(paragogosDB.getParagogosInfo().get(i).get("id").equals(id_paragogos)){
                        paragogosDB.updateEntry(id_paragogos, surname, name);
                    }
                }

                listpar.clear();
                for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {

                    listpar.add("("+paragogosDB.getParagogosInfo().get(i).get("id") +") "+ paragogosDB.getParagogosInfo().get(i).get("epitheto") + " "
                            + paragogosDB.getParagogosInfo().get(i).get("onoma"));
                }
                paragogosDB.close();

                adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpar);
                sp.setAdapter(adapter);
                sp.setSelection(spinner_postition);

            }
        });

        return view;
    }
}