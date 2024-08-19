package com.example.silagemanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class EnsiromaDB {

    public static final String ID = "id";
    public static final String EIDOS = "eidos";


    public static final String DATABASE_TABLE = DBAdapter.TABLE_NAME_ENSIROMATA;

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

    public EnsiromaDB(Context mCtx) {
        this.mCtx = mCtx;
    }

    public EnsiromaDB open() throws SQLException {
        this.mDbHelper = new DatabaseHelper(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHelper.close();
    }

    public void manageEntry(String eidos){
        ContentValues contentValues = new ContentValues();

        contentValues.put(EIDOS, eidos);

        this.mDb.insert(DATABASE_TABLE, null, contentValues);
    }

    public void updateEntry(String id, String eidos){
        ContentValues contentValues = new ContentValues();

        contentValues.put(EIDOS, eidos);

        this.mDb.update(DATABASE_TABLE, contentValues, ID + "=" + id, null);
    }

    public Boolean deleteEntry(String id){
        Cursor cursor = mDb.rawQuery("SELECT * FROM ensiromata WHERE id = ?", new String[]{id});
        if(cursor.getCount()>0){
            long result = mDb.delete("ensiromata", "id=?", new String[]{id});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public ArrayList<HashMap<String, String>> getEnsiromaInfo()
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = mDb.query(true, DATABASE_TABLE, new String[]{ID, EIDOS},
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> tmpMap = new HashMap<>();

                tmpMap.put("id", cursor.getString(0));
                tmpMap.put("eidos", cursor.getString(1));


                list.add(tmpMap);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
}
