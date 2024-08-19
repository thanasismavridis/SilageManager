package com.example.silagemanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class KtinotrofosDB {

    public static final String EPITHETO = "epitheto";
    public static final String ONOMA = "onoma";

    public static final String DATABASE_TABLE = DBAdapter.TABLE_NAME_KTINOTROFOS;

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

    public KtinotrofosDB(Context mCtx) {
        this.mCtx = mCtx;
    }

    public KtinotrofosDB open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    public void manageEntry(String epitheto, String onoma){
        ContentValues contentValues = new ContentValues();

        contentValues.put(EPITHETO, epitheto);
        contentValues.put(ONOMA, onoma);

        this.mDb.insert(DATABASE_TABLE, null, contentValues);
//        if(task == 0){
//            this.mDb.insert(DATABASE_TABLE, null, contentValues);
//        }else if(task == 1){
//            this.mDb.update(DATABASE_TABLE, contentValues, PAGE_ID + "=" + page_id, null);
//        }
    }

    public ArrayList<HashMap<String, String>> getKtinotrofosInfo()
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = mDb.query(true, DATABASE_TABLE, new String[]{EPITHETO, ONOMA},
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> tmpMap = new HashMap<>();

                tmpMap.put("epitheto", cursor.getString(0));
                tmpMap.put("onoma", cursor.getString(1));


                list.add(tmpMap);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
}
