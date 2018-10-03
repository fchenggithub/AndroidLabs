package com.example.feng.androidlabs;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
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
    private ArrayList<String> arrayList;
    ChatAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = findViewById(R.id.chatView);
        editText = findViewById(R.id.editChat);
        button = findViewById(R.id.send_Chat);
        arrayList = new ArrayList<>();
        onClick();
        messageAdapter = new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
    }
    public void onClick(){
        button.setOnClickListener(e->{
            arrayList.add(editText.getText().toString());
            messageAdapter.notifyDataSetChanged();
            editText.setText("");
        });
    }

    class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        @Override
        public int getCount(){
            return arrayList.size();
        }
        @Override
        public String getItem(int position){
            return arrayList.get(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.messageText);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }
//        public long getId(int position){
//            return position;
//        }

    }
}