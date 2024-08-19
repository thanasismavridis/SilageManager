package com.example.silagemanager.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.List;

public class DBAdapter {

    public static final String DATABASE_NAME = "appdb";
    public static int DATABASE_VERSION = 71;

    static final String TABLE_NAME_PARAGOGOS = "Paragogos";
    static final String TABLE_NAME_METAFOREAS = "Metaforeas";
    static final String TABLE_NAME_KTINOTROFOS = "Ktinotrofos";
    static final String TABLE_NAME_ENSIROMATA = "Ensiromata";
    static final String TABLE_NAME_ZIGISMATA = "Zigismata";

    //create tables
    private static final String CREATE_TABLE_PARAGOGOS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PARAGOGOS + " (id INTEGER PRIMARY KEY autoincrement, epitheto TEXT, onoma TEXT)";

    private static final String CREATE_TABLE_METAFOREAS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_METAFOREAS + " (id INTEGER PRIMARY KEY autoincrement, epitheto TEXT, onoma TEXT, ar_kikloforias TEXT, anagnoristiko TEXT)";

    private static final String CREATE_TABLE_KTINOTROFOS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_KTINOTROFOS + " (id INTEGER PRIMARY KEY autoincrement, epitheto TEXT, onoma TEXT)";

    private static final String CREATE_TABLE_ZIGISMTA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ZIGISMATA + " (id INTEGER PRIMARY KEY autoincrement, mikto TEXT, eidos TEXT, id_paragogos INTEGER, ar_kikloforias TEXT, date TEXT, ofelimo TEXT, apovaro TEXT)";

    private static final String CREATE_TABLE_ENSIROMATA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ENSIROMATA + " (id INTEGER PRIMARY KEY autoincrement, eidos TEXT)";

    private final Context context;
    private DatabaseHelper DBHelper;
    private static SQLiteDatabase db;

    public DBAdapter(Context ctx) {

        this.context = ctx;
        this.DBHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.onCreate(getWritableDatabase());
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            List<String> tablesArr =
                    Arrays.asList(CREATE_TABLE_PARAGOGOS, CREATE_TABLE_METAFOREAS, CREATE_TABLE_KTINOTROFOS, CREATE_TABLE_ZIGISMTA, CREATE_TABLE_ENSIROMATA);

            for(int i=0; i<tablesArr.size(); i++){
                db.execSQL(tablesArr.get(i));
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion > oldVersion){
                dropCreateTables(db);
            }
        }



        public static void dropCreateTables(SQLiteDatabase db){

            //Drop Tables
            List<String> dropTablesArr =
                    Arrays.asList(CREATE_TABLE_PARAGOGOS, CREATE_TABLE_METAFOREAS, CREATE_TABLE_KTINOTROFOS, CREATE_TABLE_ZIGISMTA, CREATE_TABLE_ENSIROMATA);

            for(int i=0;i<dropTablesArr.size();i++) {
                final String drop_table = "DROP TABLE IF EXISTS "+dropTablesArr.get(i);
                db.execSQL(drop_table);
            }

            //Create Tables
            List<String> createTablesArr =
                    Arrays.asList(CREATE_TABLE_PARAGOGOS, CREATE_TABLE_METAFOREAS, CREATE_TABLE_KTINOTROFOS, CREATE_TABLE_ZIGISMTA, CREATE_TABLE_ENSIROMATA);
            for(int i=0;i<createTablesArr.size();i++) {
                db.execSQL(createTablesArr.get(i));
            }
        }
    }

    //open database

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public static void dropTables() throws SQLException {
        DatabaseHelper.dropCreateTables(db);
    }

    //close database
    public void close()
    {
        this.DBHelper.close();
    }
}
