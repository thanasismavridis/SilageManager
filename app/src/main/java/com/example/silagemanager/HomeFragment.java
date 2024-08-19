package com.example.silagemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.silagemanager.Apotelesmata.Paragogos.ParagogosResultTypeFragment;
import com.example.silagemanager.Zigisma.ZigismaFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button rithimiseis_imeras = view.findViewById(R.id.rithimiseis_imeras);
        rithimiseis_imeras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment settingsFragment = new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, settingsFragment).commit();
            }
        });

        Button kataxorisi_fortion = view.findViewById(R.id.kataxorisi_fortion);
        kataxorisi_fortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZigismaFragment zigismaFragment = new ZigismaFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, zigismaFragment).commit();
            }
        });

        Button apotelesmata = view.findViewById(R.id.apotelesmata);
        apotelesmata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    ApotelesmataFragment fragment = new ApotelesmataFragment();
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, fragment).addToBackStack("null").commit();
                ParagogosResultTypeFragment fragment = new ParagogosResultTypeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, fragment).addToBackStack("null").commit();
            }
        });


        return view;
    }
}