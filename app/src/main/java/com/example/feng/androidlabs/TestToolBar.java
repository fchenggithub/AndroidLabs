package com.example.feng.androidlabs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TestToolBar extends AppCompatActivity {
    private String snackMessage ="You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        Toolbar lab8_toolbar = (Toolbar)findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar);
        final Button clickme = (Button)findViewById(R.id.click_me);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(clickme,"Message to show", Snackbar.LENGTH_SHORT).show();


            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem mi){

        switch(mi.getItemId()){
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");

                Snackbar.make(findViewById(R.id.lab8_toolbar), snackMessage, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case  R.id.action_two:
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolBar.this);
                builder.setTitle("Do you want to go back?");
// Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Response", "Here is my response");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        // User clicked OK button

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            case R.id.action_three:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

                final View view=this.getLayoutInflater().inflate(R.layout.custom_dialog,null);

                builder1.setView(view);

                builder1.setPositiveButton("New Message", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        EditText message = (EditText)view.findViewById(R.id.newMessage);

                       snackMessage = message.getText().toString();

                    }

                });

                builder1.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {



                    }

                });

                AlertDialog dialog1 = builder1.create();

                dialog1.show();

                break;

            case R.id.about:
                CharSequence text = "Version 1.0, by Feng Cheng";//
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                toast.show(); //display your message box
                break;

        }
        return true;
    }




}
