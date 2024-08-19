package com.example.silagemanager.Ensiroma;

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

import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.Database.ZigismataDB;
import com.example.silagemanager.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteEnsiromaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteEnsiromaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeleteEnsiromaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeleteEnsiromaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeleteEnsiromaFragment newInstance(String param1, String param2) {
        DeleteEnsiromaFragment fragment = new DeleteEnsiromaFragment();
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
    Activity activity;
    ArrayList<String> listEnsir;
    EnsiromaDB ensiromaDB;
    int spinner_position;
    String id_ensiroma = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_delete_ensiroma, container, false);

        EditText nameesnir_txt = view.findViewById(R.id.nameesnir_txt);
        Button delete_btn = view.findViewById(R.id.delete_btn);
        disableEditText(nameesnir_txt);

        activity = getActivity();
        listEnsir = new ArrayList();

        ensiromaDB = new EnsiromaDB(getContext());
        ensiromaDB.open();
        for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {

            listEnsir.add("("+ensiromaDB.getEnsiromaInfo().get(i).get("id") +") "+ ensiromaDB.getEnsiromaInfo().get(i).get("eidos"));
        }
        ensiromaDB.close();

        sp = view.findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listEnsir);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_ensir = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                id_ensiroma = id_ensir;
                ensiromaDB.open();
                //Log.e("TAG", "onItemSelected: " + id_ensir );
                for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {
                    if(ensiromaDB.getEnsiromaInfo().get(i).get("id").equals(id_ensir)){
                        Log.e("TAG", "onItemSelected: " + ensiromaDB.getEnsiromaInfo().get(i).get("id") );
                        nameesnir_txt.setText(ensiromaDB.getEnsiromaInfo().get(i).get("eidos"));
                    }
                }
                ensiromaDB.close();

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
                    if(zigismataDB.getZigismaInfo().get(i).get("eidos").equals(id_ensiroma)){
                        canDelete = false;
                    }else{
                        canDelete = true;
                    }
                }
                zigismataDB.close();


                if (canDelete){
                    ensiromaDB.open();
                    for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {
                        if(ensiromaDB.getEnsiromaInfo().get(i).get("id").equals(id_ensiroma)){
                            ensiromaDB.deleteEntry(id_ensiroma);
                        }
                    }

                    ensiromaDB.open();
                    listEnsir.clear();
                    for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {

                        listEnsir.add("("+ensiromaDB.getEnsiromaInfo().get(i).get("id") +") "+ ensiromaDB.getEnsiromaInfo().get(i).get("eidos"));
                    }
                    ensiromaDB.close();

                    adapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listEnsir);
                    sp.setAdapter(adapter);
                    sp.setSelection(spinner_position);
                }else{
                    // Initialize alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    // set title
                    builder.setTitle("Προσοχή!");

                    builder.setMessage("Η διαγραφή δεν μπορεί να πραγματοποιηθεί καθώς το ενσίρωμα εμπεριέχεται σε ζύγισμα");

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