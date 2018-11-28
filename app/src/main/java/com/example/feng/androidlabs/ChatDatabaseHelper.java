package com.example.feng.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Chats.db";
    public static final int VERSION_NUM = 5;
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String TABLE_NAME = "CHATS_TABLE";
    public static final String TAG = "ChatDatabaseHelper";
    private SQLiteDatabase database;
    public static String[] MESSAGE_FIELDS = new String[]{
            KEY_ID,
            KEY_MESSAGE,
    };


    public ChatDatabaseHelper(Context cxt) {
        super(cxt, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE + " TEXT);");

        Log.i(TAG, "Calling onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete current table
        onCreate(db);
        Log.i(TAG, "Calling onUpdate(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);
    }

    public void insertEntry(String content) {
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, content);
        database.insert(TABLE_NAME, null, values);
    }

    public void deleteItem(String id) {
        this.getWritableDatabase().execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id);
    }

    public Cursor getRecords() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i("Database ", "onOpen was called");
    }

    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
