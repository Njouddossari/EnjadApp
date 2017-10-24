package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void signuponclick(View view) {

        Intent signup = new Intent (this,HomPageActivity.class);

        startActivity(signup);
    }
}
