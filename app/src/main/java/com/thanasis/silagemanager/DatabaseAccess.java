package com.thanasis.silagemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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



    //-----------------------------VIEW FUNCTIONS------------------------------------------------------

    public Cursor getData(String table){
        Cursor cursor = db.rawQuery("SELECT * FROM '"+table+"'", null);
        return cursor;
    }

    public Cursor getDataSurname(String table, String surname){
        Cursor cursor = db.rawQuery("SELECT * FROM '"+table+"' WHERE epitheto = '"+surname+"'" , null);
        return cursor;
    }

    public Cursor getZigismaData(){
        Cursor cursor = db.rawQuery("SELECT\n" +
                "\t\timerominia,\n" +
                "\t\teidos,\n" +
                "\t\ttonoi,\n" +
                "\t\tparagogos.epitheto as paragogos_epitheto,\n" +
                "\t\tparagogos.onoma as paragogos_onoma,\n" +
                "\t\tmetaforeas.epitheto as metaforeas_epitheto,\n" +
                "\t\tmetaforeas.onoma as metaforeas_onoma,\n" +
                "\t\tmetaforeas.ar_kikloforias as ar_kikloforias\n" +
                "FROM\n" +
                "\tzigisma\n" +
                "INNER JOIN paragogos ON paragogos.id_paragogos = zigisma.id_paragogos\n" +
                "INNER JOIN metaforeas ON metaforeas.ar_kikloforias = zigisma.ar_kikloforias",null);
        return cursor;
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

    public Boolean insertzigisma(Long tonoi, String eidos,  int id_paragogos, String ar_kikloforias, String imerominia){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tonoi", tonoi);
        contentValues.put("eidos", eidos);
        contentValues.put("id_paragogos", id_paragogos);
        contentValues.put("ar_kikloforias", ar_kikloforias);
        contentValues.put("imerominia", imerominia);

        long result = db.insert("zigisma", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    //-----------------------------UPDATE FUNCTIONS----------------------------------------------------

    public Boolean updateparagogos(String onoma, String epitheto){
        ContentValues contentValues = new ContentValues();
        contentValues.put("onoma", onoma);
        //contentValues.put("epitheto", epitheto);
        Cursor cursor = db.rawQuery("SELECT * FROM paragogos WHERE epitheto = ?", new String[]{epitheto});
        if(cursor.getCount() > 0){
            long result = db.update("paragogos", contentValues, "epitheto=?", new String[] {epitheto});
            if(result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updatektinotrofos(String onoma, String epitheto){
        ContentValues contentValues = new ContentValues();
        contentValues.put("onoma", onoma);
        //contentValues.put("epitheto", epitheto);
        Cursor cursor = db.rawQuery("SELECT * FROM ktinotrofos WHERE epitheto = ?", new String[]{epitheto});
        if(cursor.getCount() > 0){
            long result = db.update("ktinotrofos", contentValues, "epitheto=?", new String[] {epitheto});
            if(result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updatemetaforeas(String onoma, String epitheto){
        ContentValues contentValues = new ContentValues();
        contentValues.put("onoma", onoma);
        //contentValues.put("epitheto", epitheto);
        Cursor cursor = db.rawQuery("SELECT * FROM metaforeas WHERE epitheto = ?", new String[]{epitheto});
        if(cursor.getCount() > 0){
            long result = db.update("metaforeas", contentValues, "epitheto=?", new String[] {epitheto});
            if(result==-1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }







    //-----------------------------DELETE FUNCTIONS----------------------------------------------------
    public Boolean deleteparagogos (String epitheto){
        Cursor cursor = db.rawQuery("SELECT * FROM paragogos WHERE epitheto = ?", new String[]{epitheto});
        if(cursor.getCount()>0){
            long result = db.delete("paragogos", "epitheto=?", new String[]{epitheto});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deletektinotrofos (String epitheto){
        Cursor cursor = db.rawQuery("SELECT * FROM ktinotrofos WHERE epitheto = ?", new String[]{epitheto});
        if(cursor.getCount()>0){
            long result = db.delete("ktinotrofos", "epitheto=?", new String[]{epitheto});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deletemetaforeas (String epitheto){
        Cursor cursor = db.rawQuery("SELECT * FROM metaforeas WHERE epitheto = ?", new String[]{epitheto});
        if(cursor.getCount()>0){
            long result = db.delete("metaforeas", "epitheto=?", new String[]{epitheto});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    //-----------------------------SPINNER QUERY----------------------------------------------------

    public ArrayList<String> getsurname(String table){
        ArrayList <String> list = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM '"+table+"'", null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String parepitheto = cursor.getString(cursor.getColumnIndex("epitheto"));
                list.add(parepitheto);
            }
        }
        return list;
    }


}
