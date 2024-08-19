package com.example.silagemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.silagemanager.Database.EnsiromaDB;
import com.example.silagemanager.Database.MetaforeasDB;
import com.example.silagemanager.Database.ParagogosDB;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
    ParagogosDB paragogosDB;
    MetaforeasDB metaforeasDB;
    EnsiromaDB ensiromaDB;
    ArrayList<String> listpar;
    ArrayList<String> listEnsir;
    String[] listMet;
    ArrayAdapter<String> adapter_par;
    ArrayAdapter<String> adapter_ensir;
    Spinner sp_par;
    Spinner sp_ensir;

    EditText year;
    String id_paragogos = "";
    String id_ensiroma = "";
    boolean[] selectedCar;
    ArrayList<Integer> carList = new ArrayList<>();

    String tempParOfDay = "";
    ArrayList<String> tempCarsofDay = new ArrayList<>();
    String tempEnsirOfDay = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);


        // paragogos of the day
        listpar = new ArrayList();
        paragogosDB = new ParagogosDB(getContext());
        paragogosDB.open();
        for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {

            listpar.add("("+paragogosDB.getParagogosInfo().get(i).get("id") +") "+ paragogosDB.getParagogosInfo().get(i).get("epitheto") + " "
                    + paragogosDB.getParagogosInfo().get(i).get("onoma"));
        }
        paragogosDB.close();

        sp_par = view.findViewById(R.id.spinner);

        adapter_par = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listpar);
        sp_par.setAdapter(adapter_par);

        sp_par.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_par = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                id_paragogos = id_par;
                paragogosDB.open();
                Log.e("TAG", "onItemSelected: " + id_par);
                for (int i=0; i<paragogosDB.getParagogosInfo().size(); i++) {
                    if(paragogosDB.getParagogosInfo().get(i).get("id").equals(id_par)){
                        Log.e("TAG", "onItemSelected: " + paragogosDB.getParagogosInfo().get(i).get("id") );
                        tempParOfDay = paragogosDB.getParagogosInfo().get(i).get("id");
                    }
                }
                paragogosDB.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // car of the day
        metaforeasDB = new MetaforeasDB(getContext());
        metaforeasDB.open();
        listMet = new String[metaforeasDB.getMetaforeasInfo().size()];
        for (int i=0; i<metaforeasDB.getMetaforeasInfo().size(); i++) {
            //listMet.add("("+metaforeasDB.getMetaforeasInfo().get(i).get("id") +") "+ metaforeasDB.getMetaforeasInfo().get(i).get("ar_kikloforias"));
            listMet[i] = "("+metaforeasDB.getMetaforeasInfo().get(i).get("id") +") "+ metaforeasDB.getMetaforeasInfo().get(i).get("anagnoristiko");
        }

        TextView car_of_day_selected = view.findViewById(R.id.car_of_day_selected);
        car_of_day_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Επιλέξτε οχήματα");

                // set dialog non cancelable
                builder.setCancelable(false);

                // initialize selected language array
                selectedCar = new boolean[listMet.length];

                builder.setMultiChoiceItems(listMet, selectedCar, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            carList.add(i);
                            // Sort array list
                            Collections.sort(carList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            carList.remove(Integer.valueOf(i));
                        }
                    }
                });

                ArrayList<String> tempCarList = new ArrayList<String>();
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < carList.size(); j++) {
                            // concat array value
                            stringBuilder.append(listMet[carList.get(j)]);
                            tempCarList.add(listMet[carList.get(j)]);
                            // check condition
                            if (j != carList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        car_of_day_selected.setText(stringBuilder.toString());
                        //Log.e("TAG", "onClick: " + tempCarList.get(0));

                        if(tempCarList.size()>0){
                            tempCarsofDay.addAll(tempCarList);
                        }else{
                            Toast.makeText(getContext(), "Παρακαλώ επιλέξτε οχήματα", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedCar.length; j++) {
                            // remove all selection
                            selectedCar[j] = false;
                            // clear language list
                            carList.clear();
                            // clear text view value
                            car_of_day_selected.setText("");
                            tempCarList.clear();
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });


        // ensiroma of the day

        listEnsir = new ArrayList();
        ensiromaDB = new EnsiromaDB(getContext());
        ensiromaDB.open();
        for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {

            listEnsir.add("("+ensiromaDB.getEnsiromaInfo().get(i).get("id") +") "+ ensiromaDB.getEnsiromaInfo().get(i).get("eidos"));
        }
        ensiromaDB.close();

        sp_ensir = view.findViewById(R.id.spinner_ensiroma);

        adapter_ensir = new ArrayAdapter<String>(getContext(),R.layout.spinner_layout, R.id.txt, listEnsir);
        sp_ensir.setAdapter(adapter_ensir);

        sp_ensir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                String id_par = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                ensiromaDB.open();
                //Log.e("TAG", "onItemSelected: " + id_par);
                for (int i=0; i<ensiromaDB.getEnsiromaInfo().size(); i++) {
                    if(ensiromaDB.getEnsiromaInfo().get(i).get("id").equals(id_par)){
                        Log.e("TAG", "onItemSelected: " + ensiromaDB.getEnsiromaInfo().get(i).get("id") );
                        tempEnsirOfDay = ensiromaDB.getEnsiromaInfo().get(i).get("id");
                    }
                }
                ensiromaDB.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //year
        year = view.findViewById(R.id.year);



        Button save_btn = view.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppService.paragogos_of_day = tempParOfDay;
                AppService.cars_of_day = new ArrayList<>();
                AppService.cars_of_day.addAll(tempCarsofDay);
                AppService.ensiroma_of_day = tempEnsirOfDay;
                AppService.year = year.getText().toString();

                HomeFragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_menu, homeFragment).commit();
                //Log.e("TAG", "onClick: " + AppService.paragogos_of_day + "\n" + AppService.cars_of_day + "\n" + AppService.ensiroma_of_day);
            }
        });

        return view;
    }
}