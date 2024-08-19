package com.example.silagemanager.Metaforeas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertMetaforeasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertMetaforeasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsertMetaforeasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertMetaforeasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertMetaforeasFragment newInstance(String param1, String param2) {
        InsertMetaforeasFragment fragment = new InsertMetaforeasFragment();
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
    EditText namemet_txt, surnamemet_txt, arkikloforias_txt, anagnoristiko_txt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insert_metaforeas, container, false);

        namemet_txt = view.findViewById(R.id.namemet_txt);
        surnamemet_txt = view.findViewById(R.id.surnamemet_txt);
        arkikloforias_txt = view.findViewById(R.id.ar_kikloforias_txt);
        anagnoristiko_txt = view.findViewById(R.id.anagnoristiko_txt);

        Button insert_btn = view.findViewById(R.id.insert_btn);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MetaforeasDB metaforeasDB = new MetaforeasDB(getContext());
                metaforeasDB.open();

                String onoma = namemet_txt.getText().toString();
                String epitheto = surnamemet_txt.getText().toString();
                String  ar_kikloforias = arkikloforias_txt.getText().toString();
                String anagnoristiko = anagnoristiko_txt.getText().toString();

                if(onoma.matches("") || epitheto.matches("")){
                    Toast.makeText(getContext(), "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΠΕΔΙΑ", Toast.LENGTH_LONG).show();
                }else {

                    metaforeasDB.manageEntry(epitheto, onoma, ar_kikloforias, anagnoristiko);

                    metaforeasDB.close();
                }
                namemet_txt.setText("");
                surnamemet_txt.setText("");
                arkikloforias_txt.setText("");
                anagnoristiko_txt.setText("");
            }
        });

        return view;
    }
}