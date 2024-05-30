package com.example.to_do_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class My_Database extends SQLiteOpenHelper {
    public My_Database(@Nullable Context context) {
        super(context, "TO_DO_Data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table Category(ID integer primary key autoincrement,NAME text)";
        db.execSQL(query);
        Log.d("TAG", "onCreate: Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addData(String addCategory) {
        String query = "insert into Category(NAME) values('" + addCategory + "')";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }

    public Cursor viewData() {
        String query = "select * from Category";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void updateData(Integer id, String newName) {
        String query = "UPDATE Category SET NAME = '" + newName + "' WHERE ID = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    public void deleteData(Integer id) {

        String query = "delete from Category where ID=" + id + "";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
}
