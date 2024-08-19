package com.example.silagemanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class AppService extends Service {

    public static String paragogos_of_day = "";
    public static ArrayList<String> cars_of_day = new ArrayList<String>();
    public static String ensiroma_of_day = "";

    public static String year = "";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Ανάκτηση δεδομένων από το SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        paragogos_of_day = sharedPreferences.getString("paragogos_of_day", "");
        ensiroma_of_day = sharedPreferences.getString("ensiroma_of_day", "");
        year = sharedPreferences.getString("year", "");

        // Ανάκτηση λίστας αυτοκινήτων από String Set
        Set<String> carSet = sharedPreferences.getStringSet("cars_of_day", null);
        if (carSet != null) {
            cars_of_day = new ArrayList<>(carSet);
        } else {
            cars_of_day = new ArrayList<>();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Αποθήκευση δεδομένων στο SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("paragogos_of_day", paragogos_of_day);
        editor.putString("ensiroma_of_day", ensiroma_of_day);
        editor.putString("year", year);

        // Αποθήκευση λίστας αυτοκινήτων σε String Set
        Set<String> carSet = new HashSet<>(cars_of_day);
        editor.putStringSet("cars_of_day", carSet);

        editor.apply();
    }
}
