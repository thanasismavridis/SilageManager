package com.thanasis.silagemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    public  void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public String getEpitheto(String name){
        c = db.rawQuery("SELECT * FROM paragogos WHERE onoma = '"+name+"'", new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext()){
            String epitheto1 = c.getString(2);
            String id = c.getString(0);
            buffer.append(""+epitheto1+""+ id);

        }
        return buffer.toString();
    }

    //-----------------------------INSERT FUNCTIONS----------------------------------------------------

    public Boolean insertparagogos(String onoma, String epitheto){
        ContentValues contentValues = new ContentValues();
        contentValues.put("onoma", onoma);
        contentValues.put("epitheto", epitheto);
        long result = db.insert("paragogos", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean insertmetaforeas(String onoma, String epitheto, String ar_kikloforias){
        ContentValues contentValues = new ContentValues();
        contentValues.put("onoma", onoma);
        contentValues.put("epitheto", epitheto);
        contentValues.put("ar_kikloforias", ar_kikloforias);
        long result = db.insert("metaforeas", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean insertktinotrofos(String onoma, String epitheto){
        ContentValues contentValues = new ContentValues();
        contentValues.put("onoma", onoma);
        contentValues.put("epitheto", epitheto);
        long result = db.insert("ktinotrofos", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
}
