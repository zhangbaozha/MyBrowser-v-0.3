package com.zhw.mybrowser03.SQLutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_RECORD = "Create table IF NOT EXISTS Record (" +
            "id integer primary key autoincrement," +
            "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "url text )";
    public static final String CREATE_STAR_CATEGORY = "Create table IF NOT EXISTS StarCategory (" +
            "id integer primary key autoincrement," +
            "name text )";
    public static final String CREATE_STAR_ITEM = "Create table IF NOT EXISTS StarCategoryItem (" +
            "id integer primary key autoincrement," +
            "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
            "category text ," +
            "url text )";

    private Context mContext;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD);
        db.execSQL(CREATE_STAR_CATEGORY);
        db.execSQL(CREATE_STAR_ITEM);
        Toast.makeText(mContext,"Create Successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Record");
        onCreate(db);
    }
}
