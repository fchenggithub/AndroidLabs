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
import android.widget.TextView;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    private Button buttonLogin;
    private TextView textViewEmail;
    private String emailStored  = "email@domain.com";
    public static final String SETTINGS = "com.example.feng.lab3.settings";
    public static final String STORED_EMAIL = "storedEmail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                savedLogin();
                Intent intent = new Intent(LoginActivity.this,StartActivity.class);
                startActivity(intent);
            }
        });
        textViewEmail = (TextView)findViewById(R.id.textViewEmail);
        loadSavedLogin();
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }
    private void loadSavedLogin(){
        SharedPreferences preferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        emailStored = preferences.getString(STORED_EMAIL, emailStored);
        editor.commit();
        textViewEmail.setText(emailStored);
    }
    private void savedLogin(){
        SharedPreferences preferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        emailStored = textViewEmail.getText().toString();
        editor.putString(STORED_EMAIL, emailStored);
        editor.commit();
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
