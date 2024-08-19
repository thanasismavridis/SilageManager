package com.example.silagemanager.Paragogos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.silagemanager.Database.ParagogosDB;
import com.example.silagemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ParagogosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParagogosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ParagogosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParagogosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ParagogosFragment newInstance(String param1, String param2) {
        ParagogosFragment fragment = new ParagogosFragment();
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
        view = inflater.inflate(R.layout.fragment_paragogos, container, false);

        Button kataxorisi_btn = view.findViewById(R.id.kataxorisi_btn);

        kataxorisi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertParagogosFragment fragment = new InsertParagogosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, fragment).commit();
            }
        });

        Button enimerosi_btn = view.findViewById(R.id.enimerosi_btn);
        enimerosi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateParagogosFragment updateParagogosFragment =  new UpdateParagogosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, updateParagogosFragment).commit();
            }
        });



        Button diagrafi_btn = view.findViewById(R.id.diagrafi_btn);
        diagrafi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteParagogosFragment deleteParagogosFragment = new DeleteParagogosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, deleteParagogosFragment).commit();
            }
        });

        //        Button provoli_btn = view.findViewById(R.id.provoli_btn);
//        provoli_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ParagogosDB paragogosDB =  new ParagogosDB(getContext());
//                paragogosDB.open();
//                Log.e("TAG", "onClick: " + paragogosDB.getParagogosInfo());
//                paragogosDB.close();
//            }
//        });
        return view;
    }
}