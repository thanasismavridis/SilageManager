package com.example.silagemanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class ParagogosDB {

    public static final String EPITHETO = "epitheto";
    public static final String ONOMA = "onoma";
    public static final String ID = "id";

    public static final String DATABASE_TABLE = DBAdapter.TABLE_NAME_PARAGOGOS;

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

    public ParagogosDB(Context mCtx) {
        this.mCtx = mCtx;
    }

    public ParagogosDB open() throws SQLException {
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

    }

    public void updateEntry(String id, String epitheto, String onoma){
        ContentValues contentValues = new ContentValues();

        contentValues.put(EPITHETO, epitheto);
        contentValues.put(ONOMA, onoma);
        this.mDb.update(DATABASE_TABLE, contentValues, ID + "=" + id, null);

    }

    public Boolean deleteEntry(String id){
        Cursor cursor = mDb.rawQuery("SELECT * FROM paragogos WHERE id = ?", new String[]{id});
        if(cursor.getCount()>0){
            long result = mDb.delete("paragogos", "id=?", new String[]{id});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public ArrayList<HashMap<String, String>> getParagogosInfo()
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        Cursor cursor = mDb.query(true, DATABASE_TABLE, new String[]{ID, EPITHETO, ONOMA},
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> tmpMap = new HashMap<>();

                tmpMap.put("id", cursor.getString(0));
                tmpMap.put("epitheto", cursor.getString(1));
                tmpMap.put("onoma", cursor.getString(2));


                list.add(tmpMap);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
}
