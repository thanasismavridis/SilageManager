package com.example.silagemanager.Paragogos;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertParagogosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertParagogosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsertParagogosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertParagogosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertParagogosFragment newInstance(String param1, String param2) {
        InsertParagogosFragment fragment = new InsertParagogosFragment();
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

        fm = getActivity().getSupportFragmentManager();
    }

    View view;
    FragmentManager fm;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_insert_paragogos, container, false);

        EditText namepar_txt = view.findViewById(R.id.namepar_txt);
        EditText surnamepar_txt = view.findViewById(R.id.surnamepar_txt);
        Button insert_btn =  view.findViewById(R.id.insert_btn);

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParagogosDB paragogosDB = new ParagogosDB(getContext());
                paragogosDB.open();

                String onoma = namepar_txt.getText().toString();
                String epitheto = surnamepar_txt.getText().toString();

                if(onoma.matches("") || epitheto.matches("")){
                    Toast.makeText(getContext(), "ΣΥΜΠΛΗΡΩΣΤΕ ΟΛΑ ΤΑ ΠΕΔΙΑ", Toast.LENGTH_LONG).show();
                }else {

                    paragogosDB.manageEntry(epitheto, onoma);

                    paragogosDB.close();
                }
                namepar_txt.setText("");
                surnamepar_txt.setText("");
            }
        });

        return view;
    }
}