package com.example.lenovo.enjad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    Button busign; EditText usernameet, passwordet , emailet, reenterpasset , healthet , mobileet;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        busign= (Button) findViewById(R.id.signbu);
        usernameet= (EditText) findViewById(R.id.usrnameet);
        passwordet= (EditText) findViewById(R.id.passwrdet);
        emailet= (EditText) findViewById(R.id.emailet);
        reenterpasset=(EditText) findViewById(R.id.reenterpasset);
        healthet= (EditText) findViewById(R.id.healthet);
        mobileet=(EditText) findViewById(R.id.phoneet);

    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"); //email pattern

    public void signuponclick(View view) {

        if (usernameet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredUserName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (EMAIL_ADDRESS_PATTERN.matcher(emailet.getText().toString().trim()).matches() == false) {
            Toast.makeText(this, getString(R.string.Msg_FormateEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (healthet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredHealth), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobileet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_Requiredphone), Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredPassword), Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordet.getText().toString().trim().equals(reenterpasset.getText().toString().trim()) == false)//confirm the password
        {
            Toast.makeText(this, getString(R.string.Msg_ConfirmPassword), Toast.LENGTH_SHORT).show();
            return;
        }

        username = usernameet.getText().toString().trim();
        if (username.contains(",") || username.contains("-")
                || username.contains("+") || username.contains("#")
                || username.contains("$") || username.contains("%")
                || username.contains("^") || username.contains("|")
                || username.contains("{") || username.contains("}")
                || username.contains("(") || username.contains(")")
                || username.contains("[") || username.contains("]")
                || username.contains("<") || username.contains(">")
                || username.contains(" ") || username.contains("!")
                || username.contains("~")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.Msg_ErrorTitle));
            alertDialog.setMessage(getString(R.string.Msg_FormatUserName));
            alertDialog.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            alertDialog.setIcon(R.drawable.ic_launcher);
            alertDialog.show();
            return;
        }



        String type = "signup";
        registerDataLoaderBW bw = new registerDataLoaderBW(this);
        bw.execute(type, usernameet.getText().toString().trim(), passwordet.getText().toString(),  emailet.getText().toString().trim(),healthet.getText().toString().trim(), mobileet.getText().toString().trim());
    }


    public void getDataSign(String result)
    {
        if (result.toString().trim().equals("1")) //trim() omit the white space from starting and the ending in the varible
        {
            Intent signup = new Intent (this,HomPageActivity.class);
            signup.putExtra("username", usernameet.getText().toString().trim());
            startActivity(signup);
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.error_confrim), Toast.LENGTH_LONG).show();}
    }

}


