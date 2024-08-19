package com.example.silagemanager.Ensiroma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertEnsiromaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertEnsiromaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsertEnsiromaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertEnsiromaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertEnsiromaFragment newInstance(String param1, String param2) {
        InsertEnsiromaFragment fragment = new InsertEnsiromaFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insert_ensiroma, container, false);

        EditText eidos_txt = view.findViewById(R.id.eidos_txt);

        Button insert_btn = (Button) view.findViewById(R.id.insert_btn);
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EnsiromaDB ensiromaDB = new EnsiromaDB(getContext());
                ensiromaDB.open();

                String eidos = eidos_txt.getText().toString();


                if(eidos.matches("")){
                    Toast.makeText(getContext(), "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΠΕΔΙΑ", Toast.LENGTH_LONG).show();
                }else {

                    ensiromaDB.manageEntry(eidos);

                    ensiromaDB.close();
                }
                eidos_txt.setText("");
            }
        });

        return view;
    }
}