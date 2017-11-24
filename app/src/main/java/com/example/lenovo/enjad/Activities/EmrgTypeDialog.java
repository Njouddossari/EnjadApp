package com.example.lenovo.enjad.Activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.lenovo.enjad.R;

public  class EmrgTypeDialog extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // these flags to start activity on lock screen

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toast.makeText(this, "On emergencey dialog", Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);

        // setContentView(R.layout.dialog_view);
        //public void onClick(View v) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.activity_emrg_type_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        //type choices
        final RadioButton userInput1 = (RadioButton) promptsView
                .findViewById(R.id.radioButton);
        final RadioButton userInput2 = (RadioButton) promptsView
                .findViewById(R.id.radioButton2);
        final RadioButton Dialog1 = (RadioButton) findViewById(R.id.radioButton3);

        //Send button
        final Button Dialog2 = (Button) findViewById(R.id.button3);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                Dialog1.setText(userInput1.getText());
                                Dialog2.setText(userInput2.getText());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}

/*  LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.activity_emrg_type_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set activity_emrg_type_dialog.xml to alertdialog builder
        builder.setView(promptsView);

        //type choices
        final RadioButton userInput1 = (RadioButton) promptsView
                .findViewById(R.id.radioButton);
        final RadioButton userInput2 = (RadioButton) promptsView
                .findViewById(R.id.radioButton2);
        final RadioButton Dialog1 = (RadioButton) findViewById(R.id.radioButton3);

        //Send button
        final Button Dialog2 = (Button) findViewById(R.id.button3);

        builder
                .setTitle("@strings/emrgType")
                .setIcon(Integer.parseInt("@drawable/enjadapplogoicon"))
                .setCancelable(false)
                .setPositiveButton("@strings/send", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //Here is the code for after pressing Send

                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.show();
    }
}
*/