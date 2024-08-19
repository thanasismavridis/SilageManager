package com.example.silagemanager.Metaforeas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.Database.ZigismataDB;
import com.example.silagemanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteMetaforeasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteMetaforeasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteMetaforeasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteMetaforeasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteMetaforeasFragment newInstance(String param1, String param2) {
        DeleteMetaforeasFragment fragment = new DeleteMetaforeasFragment();
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
    String id_metaforeas = "";
    Spinner sp;
    ArrayAdapter<String> adapter;
    Activity activity;
    MetaforeasDB metaforeasDB;
    ArrayList<String> listpmet;
    int spinner_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delete_metaforeas, container, false);

        EditText namemet_txt = view.findViewById(R.id.namemet_txt);
        EditText surnamemet_txt = view.findViewById(R.id.surnamemet_txt);
        EditText ar_kikloforias_txt = view.findViewById(R.id.ar_kikloforias_txt);
        disableEditText(surnamemet_txt);
        disableEditText(namemet_txt);
        disableEditText(ar_kikloforias_txt);
        Button delete_btn = view.findViewById(R.id.delete_btn);

        activity = getActivity();
        listpmet = new ArrayList();
        metaforeasDB = new MetaforeasDB(getContext());
        metaforeasDB.open();
        for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {

            listpmet.add("("+metaforeasDB.getMetaforeasInfo().get(i).get("id") +") "+ metaforeasDB.getMetaforeasInfo().get(i).get("epitheto") + " "
                    + metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
        }
        metaforeasDB.close();

        sp = view.findViewById(R.id.spinner);

        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpmet);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_met = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                id_metaforeas = id_met;
                metaforeasDB.open();
                Log.e("TAG", "onItemSelected: " + id_met );
                for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {
                    if(metaforeasDB.getMetaforeasInfo().get(i).get("id").equals(id_met)){
                        Log.e("TAG", "onItemSelected: " + metaforeasDB.getMetaforeasInfo().get(i).get("id") );
                        namemet_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("onoma"));
                        surnamemet_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("epitheto"));
                        ar_kikloforias_txt.setText(metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
                    }
                }
                metaforeasDB.close();

                //save spinner position to set it again later
                spinner_position = position - 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = namemet_txt.getText().toString();
                String surname = surnamemet_txt.getText().toString();
                String ar_kikloforias = ar_kikloforias_txt.getText().toString();

                Boolean canDelete = false;

                ZigismataDB zigismataDB = new ZigismataDB(getContext());
                zigismataDB.open();
                for (int i=0; i<zigismataDB.getZigismaInfo().size(); i++) {
                    if(zigismataDB.getZigismaInfo().get(i).get("ar_kikloforias").equals(id_metaforeas)){
                        canDelete = false;
                    }else{
                        canDelete = true;
                    }
                }
                zigismataDB.close();

                if(canDelete){
                    metaforeasDB.open();
                    for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {
                        if(metaforeasDB.getMetaforeasInfo().get(i).get("id").equals(id_metaforeas)){
                            metaforeasDB.deleteEntry(id_metaforeas);
                        }
                    }

                    metaforeasDB.open();
                    listpmet.clear();
                    for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {

                        listpmet.add("("+metaforeasDB.getMetaforeasInfo().get(i).get("id") +") "+ metaforeasDB.getMetaforeasInfo().get(i).get("epitheto") + " "
                                + metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
                    }
                    metaforeasDB.close();

                    adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpmet);
                    sp.setAdapter(adapter);
                    sp.setSelection(spinner_position);
                }else{
                    // Initialize alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    // set title
                    builder.setTitle("Προσοχή!");

                    builder.setMessage("Η διαγραφή δεν μπορεί να πραγματοποιηθεί καθώς ο μεταφορέας εμπεριέχεται σε ζύγισμα");

                    // set dialog non cancelable
                    builder.setCancelable(false);

                    builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // dismiss dialog
                            dialogInterface.dismiss();
                        }
                    });

                    // show dialog
                    builder.show();
                }

            }
        });

        return view;
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }
}