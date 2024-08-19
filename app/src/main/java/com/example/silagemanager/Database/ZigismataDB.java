package com.example.silagemanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class ZigismataDB {

    public static final String ID = "id";
    public static final String MIKTO = "mikto";
    public static final String EIDOS = "eidos";
    public static final String ID_PARAGOGOS = "id_paragogos";
    public static final String AR_KIKLOFORIAS = "ar_kikloforias";
    public static final String DATE = "date";
    public static final String OFELIMO = "ofelimo";
    public static final String APOVARO = "apovaro";

    public static final String DATABASE_TABLE = DBAdapter.TABLE_NAME_ZIGISMATA;

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public ZigismataDB(Context mCtx) {
        this.mCtx = mCtx;
    }

    public ZigismataDB open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    public void manageEntry(String mikto, String eidos, String id_paragogos,
                            String ar_kikloforias, String date, String ofelimo, String apovaro){
        ContentValues contentValues = new ContentValues();

        contentValues.put(MIKTO, mikto);
        contentValues.put(EIDOS, eidos);
        contentValues.put(ID_PARAGOGOS, id_paragogos);
        contentValues.put(AR_KIKLOFORIAS, ar_kikloforias);
        contentValues.put(DATE, date);
        contentValues.put(OFELIMO, ofelimo);
        contentValues.put(APOVARO, apovaro);

        this.mDb.insert(DATABASE_TABLE, null, contentValues);
    }

    public ArrayList<HashMap<String, String>> getZigismaInfo()
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = mDb.query(true, DATABASE_TABLE, new String[]{ID, MIKTO, EIDOS,
                        ID_PARAGOGOS, AR_KIKLOFORIAS, DATE, OFELIMO, APOVARO},
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> tmpMap = new HashMap<>();

                tmpMap.put("id", cursor.getString(0));
                tmpMap.put("mikto", cursor.getString(1));
                tmpMap.put("eidos", cursor.getString(2));
                tmpMap.put("id_paragogos", cursor.getString(3));
                tmpMap.put("ar_kikloforias", cursor.getString(4));
                tmpMap.put("date", cursor.getString(5));
                tmpMap.put("ofelimo", cursor.getString(6));
                tmpMap.put("apovaro", cursor.getString(7));

                list.add(tmpMap);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public ArrayList<HashMap<String, String>> getZigismataByPar(){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = mDb.rawQuery("SELECT " + DATE + ", " + MIKTO+ ", " +ID_PARAGOGOS + " ," + OFELIMO + ", " + APOVARO + ", "
                + "zigismata.id as id, metaforeas.ar_kikloforias as ar_kikloforias, ensiromata.eidos as eidos, " +
                "ensiromata.id as eidos_id, metaforeas.id as autokinito_id " +
                "FROM zigismata INNER JOIN metaforeas ON metaforeas.id = zigismata.ar_kikloforias " +
                "INNER JOIN ensiromata ON ensiromata.id = zigismata.eidos", null );

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> tmpMap = new HashMap<>();


                tmpMap.put("date", cursor.getString(0));
                tmpMap.put("mikto", cursor.getString(1));
                tmpMap.put("id_paragogos", cursor.getString(2));
                tmpMap.put("ofelimo", cursor.getString(3));
                tmpMap.put("apovaro", cursor.getString(4));
                tmpMap.put("id", cursor.getString(5));
                tmpMap.put("autokinito", cursor.getString(6));
                tmpMap.put("ensiroma", cursor.getString(7));
                tmpMap.put("eidos_id", cursor.getString(8));
                tmpMap.put("autokinito_id", cursor.getString(9));

                //Log.e("TAG", "getZigismataByPar: " +  cursor.getString(4));

                list.add(tmpMap);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public ArrayList<HashMap<String, String>> getZigismataParByDate(String start_day, String end_day){
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = mDb.rawQuery("SELECT " + DATE + ", " + MIKTO+ ", " +ID_PARAGOGOS + " ,"
                + OFELIMO + ", " + APOVARO + ", " + "zigismata.id as id, metaforeas.ar_kikloforias as ar_kikloforias, " +
                "ensiromata.eidos as eidos, ensiromata.id as eidos_id, metaforeas.id as autokinito_id " +
                "FROM zigismata INNER JOIN metaforeas ON metaforeas.id = zigismata.ar_kikloforias " +
                "INNER JOIN ensiromata ON ensiromata.id = zigismata.eidos WHERE DATE BETWEEN ? AND ?",
                new String[]{start_day, end_day});

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> tmpMap = new HashMap<>();


                tmpMap.put("date", cursor.getString(0));
                tmpMap.put("mikto", cursor.getString(1));
                tmpMap.put("id_paragogos", cursor.getString(2));
                tmpMap.put("ofelimo", cursor.getString(3));
                tmpMap.put("apovaro", cursor.getString(4));
                tmpMap.put("id", cursor.getString(5));
                tmpMap.put("autokinito", cursor.getString(6));
                tmpMap.put("ensiroma", cursor.getString(7));
                tmpMap.put("eidos_id", cursor.getString(8));
                tmpMap.put("autokinito_id", cursor.getString(9));

                //Log.e("TAG", "getZigismataByPar: " +  cursor.getString(4));

                list.add(tmpMap);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

}
