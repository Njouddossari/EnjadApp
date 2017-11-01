package com.example.lenovo.enjad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button bulogin; EditText usernameet, passwordet;
    TextView toregister;
    String username , password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        bulogin= (Button) findViewById(R.id.loginbu);
        usernameet= (EditText) findViewById(R.id.usernameet);
        passwordet= (EditText) findViewById(R.id.Passwordet);
        toregister=(TextView) findViewById(R.id.toregistertv);

        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }});

    }


    public void loginonclick(View view) {
        if (usernameet.getText().toString().trim().length() == 0) {
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle(getString(R.string.Msg_ErrorTitle));
            ad.setMessage(getString(R.string.Msg_RequiredUserName));
            ad.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            ad.setIcon(R.drawable.enjadapplogoicon); //i put the icon of app //Njoud changed app icon for better resolution
            ad.show();
            return;
        }

        username= usernameet.getText().toString().trim(); //take the values of edit text
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
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            alertDialog.setIcon(R.drawable.enjadapplogoicon);
            alertDialog.show();
            return;
        }

        if ( passwordet.getText().toString().equals("") ) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.Msg_ErrorTitle));
            alertDialog.setMessage(getString(R.string.Msg_Requiredpass));
            alertDialog.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            alertDialog.setIcon(R.drawable.enjadapplogoicon);
            alertDialog.show();
            return;
        }
       password= passwordet.getText().toString().trim();
        if (username == "") {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.Msg_ErrorTitle));
            alertDialog.setMessage(getString(R.string.Msg_RequiredUserName));
            alertDialog.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            alertDialog.setIcon(R.drawable.enjadapplogoicon);
            alertDialog.show();
            return;
        }


            String type = "login";
            loginDataLoaderBW backgrandworker = new loginDataLoaderBW(this);
            backgrandworker.execute(type, usernameet.getText().toString(), passwordet.getText().toString());

        }
        //Intent login = new Intent (this,HomPageActivity.class);

        // startActivity(login);
        public void getDataLogin(String result)
        {
            if (result.toString().trim().equals("1")) //trim() omit the white space from starting and the ending in the varible
            {
                Intent login = new Intent(this, HomPageActivity.class);
                login.putExtra("username",usernameet.getText().toString().trim()); // to move username data to Home page avtivity
                startActivity(login);
            }
            else
            {
                Toast.makeText(getApplicationContext(), getString(R.string.failed_login), Toast.LENGTH_LONG).show(); //message
            }

        }


}



