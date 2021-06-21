package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectDB {

    private static final String KEY_ID = "_id";
    private static final String KEY_FIRST_NAME = "First_Name";
    private static final String KEY_SURE_NAME = "Sure_Name";

    private static final String DATABASE_NAME = "Person_DB";
    private static final String DATABASE_TABLE_NAME = "Person_Table";
    private static final int    VERSION = 1;


    private DBHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ConnectDB (Context context){
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper (Context context){
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_FIRST_NAME + " TEXT NOT NULL, " +
                    KEY_SURE_NAME + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_NAME);
            onCreate(db);
        }
    }

    public ConnectDB open()  throws SQLException{
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long createEntry (String fName, String sName){
        ContentValues cv = new ContentValues();
        cv.put(KEY_FIRST_NAME, fName);
        cv.put(KEY_SURE_NAME, sName);

        return ourDatabase.insert(DATABASE_TABLE_NAME, null, cv);
    }

    public String getData(){

        String[] col = new String[] {KEY_ID, KEY_FIRST_NAME, KEY_SURE_NAME};
        Cursor cursor = ourDatabase.query(DATABASE_TABLE_NAME, col, null, null, null, null, null);
        String result = "";

        int iRowId = cursor.getColumnIndex(KEY_ID);
        int iRowFirstName = cursor.getColumnIndex(KEY_FIRST_NAME);
        int iROwSureName = cursor.getColumnIndex(KEY_SURE_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            result += cursor.getString(iRowId) + " : " + cursor.getString(iRowFirstName) + " " + cursor.getString(iROwSureName);
        }

        cursor.close();

        return result;
    }

}
