package com.example.silagemanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.silagemanager.Apotelesmata.Paragogos.ParagogosResultTypeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApotelesmataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApotelesmataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApotelesmataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApotelesmataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApotelesmataFragment newInstance(String param1, String param2) {
        ApotelesmataFragment fragment = new ApotelesmataFragment();
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
    Button paragogos, autokinita, mixani;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view  = inflater.inflate(R.layout.fragment_apotelesmata, container, false);

        paragogos = view.findViewById(R.id.paragogos);
        autokinita = view.findViewById(R.id.autokinita);
        mixani = view.findViewById(R.id.mixani);

        paragogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParagogosResultTypeFragment fragment = new ParagogosResultTypeFragment();
                fm.beginTransaction().replace(R.id.nav_host_fragment_content_menu, fragment).addToBackStack("null").commit();
            }
        });

        return view;
    }
}