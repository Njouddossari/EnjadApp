package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button bulogin= (Button) findViewById(R.id.loginbu);

    }

    public void loginonclick(View view) {

        Intent login = new Intent (this,HomPageActivity.class);

        startActivity(login);


    }
}
