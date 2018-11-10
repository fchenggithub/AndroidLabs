package com.example.feng.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ChatAdapter messageAdapter;
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private Context ctx = null;
    private static SQLiteDatabase chatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = findViewById(R.id.chatView);

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
        editText = findViewById(R.id.editChat);
        button = findViewById(R.id.send_Chat);

        ctx = this;
        ChatDatabaseHelper dbOpener = new ChatDatabaseHelper(this);
        chatDB = dbOpener.getWritableDatabase();
        ContentValues newRow = new ContentValues();

        Cursor cursor = chatDB.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                arrayList.add(message);
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatString = editText.getText().toString();
                arrayList.add(chatString);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");
                newRow.put("message", chatString);
                chatDB.insert(ChatDatabaseHelper.TABLE_NAME, null, newRow);
            }
        });
        Log.i(ACTIVITY_NAME, "Cursors column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++){
            Log.i("Cursor column name", cursor.getColumnName(i));
        }

        Log.i(ACTIVITY_NAME, "In onCreate()");
        //onClick();
        // messageAdapter = new ChatAdapter( this );
        //listView.setAdapter (messageAdapter);


    }
//    public void onClick(){
//        button.setOnClickListener(e->{
//            arrayList.add(editText.getText().toString());
//            messageAdapter.notifyDataSetChanged();
//            editText.setText("");
//
//        });


    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
        chatDB.close();
    }


    class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public String getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.messageText);
            message.setText(getItem(position)); // get the string at position
            return result;

        }
//        public long getId(int position){
//            return position;
//        }

    }
}