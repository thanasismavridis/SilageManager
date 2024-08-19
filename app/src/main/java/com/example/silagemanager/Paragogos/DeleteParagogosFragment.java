package com.example.silagemanager.Paragogos;

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

import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.Database.ZigismataDB;
import com.example.silagemanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteParagogosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteParagogosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteParagogosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteParagogosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteParagogosFragment newInstance(String param1, String param2) {
        DeleteParagogosFragment fragment = new DeleteParagogosFragment();
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
    int spinner_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delete_paragogos, container, false);

        TextView surnamepar_txt = view.findViewById(R.id.surnamepar_txt);
        TextView namepar_txt = view.findViewById(R.id.namepar_txt);
        disableEditText((EditText) surnamepar_txt);
        disableEditText((EditText) namepar_txt);
        Button delete_btn = view.findViewById(R.id.delete_btn);

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

        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpar);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_par = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                id_paragogos = id_par;
                paragogosDB.open();
                Log.e("TAG", "onItemSelected: " + id_par );
                for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {
                    if(paragogosDB.getParagogosInfo().get(i).get("id").equals(id_par)){
                        Log.e("TAG", "onItemSelected: " + paragogosDB.getParagogosInfo().get(i).get("id") );
                        namepar_txt.setText(paragogosDB.getParagogosInfo().get(i).get("onoma"));
                        surnamepar_txt.setText(paragogosDB.getParagogosInfo().get(i).get("epitheto"));
                    }
                }
                paragogosDB.close();

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

                Boolean canDelete = false;

                ZigismataDB zigismataDB = new ZigismataDB(getContext());
                zigismataDB.open();
                for (int i=0; i<zigismataDB.getZigismaInfo().size(); i++) {
                    if(zigismataDB.getZigismaInfo().get(i).get("id_paragogos").equals(id_paragogos)){
                        canDelete = false;
                    }else{
                        canDelete = true;
                    }
                }
                zigismataDB.close();

                if(canDelete){
                    String name = namepar_txt.getText().toString();
                    String surname = surnamepar_txt.getText().toString();
                    ParagogosDB paragogosDB = new ParagogosDB(getContext());
                    paragogosDB.open();
                    for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {
                        if(paragogosDB.getParagogosInfo().get(i).get("id").equals(id_paragogos)){
                            paragogosDB.deleteEntry(id_paragogos);
                        }
                    }

                    paragogosDB.open();
                    listpar.clear();
                    for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {

                        listpar.add("("+paragogosDB.getParagogosInfo().get(i).get("id") +") "+ paragogosDB.getParagogosInfo().get(i).get("epitheto") + " "
                                + paragogosDB.getParagogosInfo().get(i).get("onoma"));
                    }
                    paragogosDB.close();

                    adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpar);
                    sp.setAdapter(adapter);
                    sp.setSelection(spinner_position);
                }else{
                    // Initialize alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    // set title
                    builder.setTitle("Προσοχή!");

                    builder.setMessage("Η διαγραφή δεν μπορεί να πραγματοποιηθεί καθώς ο παραγωγός εμπεριέχεται σε ζύγισμα");

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