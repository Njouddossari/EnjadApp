package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    Button busign; EditText usernameet, passwordet , emailet, reenterpasset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        busign= (Button) findViewById(R.id.signbu);
        usernameet= (EditText) findViewById(R.id.usrnameet);
        passwordet= (EditText) findViewById(R.id.passwrdet);
        emailet= (EditText) findViewById(R.id.emailet);
        reenterpasset=(EditText) findViewById(R.id.reenterpasset);


    }

    public void signuponclick(View view) {

        String username= usernameet.getText().toString();
        String password= passwordet.getText().toString();
        String email= emailet.getText().toString();
        String type="signup";


        // registerDataLoaderBW bw = new registerDataLoaderBW(this);
        // bw.execute(type,username,password,email);
        //Intent signup = new Intent (this,HomPageActivity.class);

        //startActivity(signup);
    }
}
