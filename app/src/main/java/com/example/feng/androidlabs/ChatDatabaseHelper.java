package com.example.feng.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Chats.db";
    public static final int VERSION_NUM = 4;
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String TABLE_NAME = "CHATS_TABLE";
    public static final String TAG = "ChatDatabaseHelper";

    public ChatDatabaseHelper(Context cxt) {
        super(cxt, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_MESSAGE + " TEXT);");

        Log.i(TAG, "Calling onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete current table
        onCreate(db);
        Log.i(TAG, "Calling onUpdate(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);
    }
}
