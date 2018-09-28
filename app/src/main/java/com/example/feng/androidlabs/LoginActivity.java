package com.example.feng.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.util.Log.i(ACTIVITY_NAME, "In onCreate()");
        final Button button_login = (Button)findViewById(R.id.buttonLogin);
        final EditText editText = (EditText)findViewById(R.id.email);
        final SharedPreferences prefs = getSharedPreferences("User_Info", Context.MODE_PRIVATE);

        editText.setText(prefs.getString("email",""));


        button_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email",editText.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this,StartActivity.class);
                // Intent intent = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivity(intent);
                // Code here executes on main thread after user presses button
            }
        });
    }


    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
