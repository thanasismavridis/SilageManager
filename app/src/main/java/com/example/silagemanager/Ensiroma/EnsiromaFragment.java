package com.example.silagemanager.Ensiroma;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnsiromaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnsiromaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EnsiromaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnsiromaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnsiromaFragment newInstance(String param1, String param2) {
        EnsiromaFragment fragment = new EnsiromaFragment();
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
        view = inflater.inflate(R.layout.fragment_ensiroma, container, false);

        Button kataxorisi_btn = view.findViewById(R.id.kataxorisi_btn);
        kataxorisi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertEnsiromaFragment insertEnsiromaFragment = new InsertEnsiromaFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, insertEnsiromaFragment).commit();
            }
        });

        Button enimerosi_btn = view.findViewById(R.id.enimerosi_btn);
        enimerosi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateEnsiromaFragment updateEnsiromaFragment = new UpdateEnsiromaFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, updateEnsiromaFragment).commit();
            }
        });

        Button diagrafi_btn  = view.findViewById(R.id.diagrafi_btn);
        diagrafi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteEnsiromaFragment deleteEnsiromaFragment = new DeleteEnsiromaFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, deleteEnsiromaFragment).commit();
            }
        });

//        Button provoli_btn = view.findViewById(R.id.provoli_btn);
//        provoli_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EnsiromaDB ensiromaDB = new EnsiromaDB(getContext());
//                ensiromaDB.open();
//                Log.e("TAG", "onClick: " +ensiromaDB.getEnsiromaInfo());
//                ensiromaDB.close();
//            }
//        });
        return view;
    }
}