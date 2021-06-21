package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDB extends SQLiteOpenHelper {

    public UserDB(@Nullable Context context) {
        super(context, "UserDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE UserTable (name TEXT PRIMARY KEY, sureName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS UserTable");

    }

    public Boolean insertData (String name, String sureName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("sureName", sureName);

        long result = db.insert("UserTable", null, cv);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM UserTable", null);

        return cursor;
    }

    public Boolean deleteData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM UserTable WHERE name =?", new String[]{name});

        if (cursor.getCount() != 0){
            long res = db.delete("UserTable", "name =?", new String[]{name});

            if (res == -1){
                return false;
            } else {
                return true;
            }
        }else return false;
    }

    public Boolean updateData(String name, String sureName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("sureName", sureName);

        Cursor cursor = db.rawQuery("SELECT * FROM UserTable WHERE name = ?", new String[]{name});

        if (cursor.getCount() > 0){
            long res = db.update("UserTable", cv,"name=?", new String[]{name});

            if (res == - 1){
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
