package com.example.silagemanager.Apotelesmata.Paragogos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.Database.ZigismataDB;
import com.example.silagemanager.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParagogosResultTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParagogosResultTypeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ParagogosResultTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParagogosResultTypeFragment newInstance(String param1, String param2) {
        ParagogosResultTypeFragment fragment = new ParagogosResultTypeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentManager fm;

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
    Button general_btn, ensiroma_btn, fortio_btn, autokinito_btn, date_btn;
    Spinner spinner;
    ArrayList<String> list;
    ArrayList<HashMap<String, String>> listPar;
    ArrayAdapter<String> spinnerAdapter;
    String id_par = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_paragogos_result_type, container, false);

        general_btn = view.findViewById(R.id.general_btn);
        ensiroma_btn = view.findViewById(R.id.ensiroma_btn);
        //fortio_btn = view.findViewById(R.id.fortio_btn);
        autokinito_btn = view.findViewById(R.id.autokinito_btn);
        date_btn = view.findViewById(R.id.date_btn);

        spinner = view.findViewById(R.id.spinner);

        list = new ArrayList();

        ParagogosDB paragogosDB = new ParagogosDB(getContext());
        paragogosDB.open();
        listPar = paragogosDB.getParagogosInfo();
        paragogosDB.close();

        for(int i=0; i<listPar.size(); i++){
            list.add(listPar.get(i).get("epitheto") + " "
                    + listPar.get(i).get("onoma"));
        }

        spinnerAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, list);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(listPar.size() == list.size()){
                    id_par = listPar.get(i).get("id");
                    //Log.e("TAG", "onItemSelected: " + id_par );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        general_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!id_par.equals("")) {
                    ApotelesmataParagogouFragment apotelesmataParagogouFragment =
                            ApotelesmataParagogouFragment.newInstance("general", id_par);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment_content_menu, apotelesmataParagogouFragment)
                            .addToBackStack("null").commit();
                }
            }
        });

        ensiroma_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!id_par.equals("")) {
                    ApotelesmataParagogouFragment apotelesmataParagogouFragment = ApotelesmataParagogouFragment.newInstance("ensiroma", id_par);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, apotelesmataParagogouFragment).addToBackStack("null").commit();
                }
            }
        });

        autokinito_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!id_par.equals("")) {
                    ApotelesmataParagogouFragment apotelesmataParagogouFragment = ApotelesmataParagogouFragment.newInstance("autokinito", id_par);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, apotelesmataParagogouFragment).addToBackStack("null").commit();
                }
            }
        });

        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ZigismataDB zigismataDB = new ZigismataDB(getContext());
//                zigismataDB.open();
//                Log.e("TAG", "onClick: " + zigismataDB.getZigismataParByDate("2023-04-04", "2023-04-04"));
//                //zigismataDB.getZigismataParByDate("2023/04/04", "2023/04/04");
//                zigismataDB.close();
                if(!id_par.equals("")) {
                    ApotelesmataParagogouFragment apotelesmataParagogouFragment = ApotelesmataParagogouFragment.newInstance("date", id_par);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, apotelesmataParagogouFragment).addToBackStack("null").commit();
                }
            }
        });

        return view;
    }
}