package com.example.feng.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import static com.example.feng.androidlabs.ChatDatabaseHelper.MESSAGE_FIELDS;
import static com.example.feng.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ChatAdapter chatAdapter;
    private Cursor cursor;
    private SQLiteDatabase database;
    private ChatDatabaseHelper chatDBHelper;
    private String chatWindow = ChatWindow.class.getSimpleName();
    private ListView listView;
    private EditText editText;
    private Button sendBtn;
    private ArrayList<String> stringArrayList;


    class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return stringArrayList.size();
        }

        public String getItem(int position){
            return stringArrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(getItem(position));//get the string at position
            return result;
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(chatDBHelper.KEY_ID));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.plain_text_input);
        sendBtn = (Button) findViewById(R.id.buttonSend);
        stringArrayList = new ArrayList<String>();
        chatAdapter = new ChatAdapter(this);
        listView.setAdapter(chatAdapter);
        chatDBHelper = new ChatDatabaseHelper(this);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("") == false) {
                    stringArrayList.add(editText.getText().toString());

                    chatDBHelper.insertEntry(editText.getText().toString());
                    query();
                    editText.setText("");
                }
            }
        });

        //Enter key on keyboard to send text
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            stringArrayList.add(editText.getText().toString());

                            chatDBHelper.insertEntry(editText.getText().toString());
                            query();
                            editText.setText("");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        //clicks on an item of Chat listView, shows the details fragment.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String item = adapterView.getItemAtPosition(position).toString();
                Long messageId = adapterView.getItemIdAtPosition(position);

                //Use a Bundle to pass the message string, and the database id of the selected item to the fragment in the FragmentTransaction
                if(findViewById(R.id.frameLayout) != null) {
                    MessageFragment messageFragment = new MessageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Message", item);
                    bundle.putString("MessageID", messageId + "");
                    bundle.putBoolean("isTablet", true);
                    messageFragment.setArguments(bundle);
                    FragmentTransaction ft =  getFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayout, messageFragment);
                    //Call transaction.addToBackStack(String name) if you want to undo this transaction with the back button.
                    ft.addToBackStack("A string");
                    ft.commit();

                    Log.i(ACTIVITY_NAME, "Run on Tablet?");
                } else {
                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra("Message", item);
                    intent.putExtra("MessageID", messageId + "");
                    startActivityForResult(intent, 1);

                    Log.i(ACTIVITY_NAME, "Run on Phone");
                }

            }
        });
    }

    public void query() {
        stringArrayList.clear();
        cursor = chatDBHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(chatWindow, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                stringArrayList.add(cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            chatAdapter.notifyDataSetChanged();//this restarts the process of getCount()/getView()
            Log.i(chatWindow, "Cursor's column count = " + cursor.getColumnCount());
        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(chatWindow, "Cursor's column name is " + (i + 1) + ". " + cursor.getColumnName(i));
        }
    }



    public void notifyChange() {
        listView.setAdapter(chatAdapter);
        database = chatDBHelper.getWritableDatabase();

        cursor = database.query(false, TABLE_NAME, MESSAGE_FIELDS, null, null, null, null, null, null);
        int numColumns = cursor.getColumnCount();
        int numResults = cursor.getCount();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
            stringArrayList.add(cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + numColumns);

        cursor.moveToFirst();
        for(int i = 0; i < numResults; i++) {
            Log.i(ACTIVITY_NAME, "The " + i + " row is " + cursor.getString(cursor.getColumnIndex(chatDBHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String id = data.getStringExtra("MessageID");
            chatDBHelper.deleteItem(id);
        }
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        chatDBHelper.openDatabase();
        query();

        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        chatDBHelper.closeDatabase();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

}
