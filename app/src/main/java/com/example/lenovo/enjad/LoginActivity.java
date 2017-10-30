package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    Button bulogin; EditText usernameet, passwordet;
    TextView toregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        String username= usernameet.getText().toString(); //take the values of edit text
        String password= passwordet.getText().toString();
        String type="login";

        //loginDataLoaderBW backgrandworker = new loginDataLoaderBW(this);
        //backgrandworker.execute(type,username,password);

        //Intent login = new Intent (this,HomPageActivity.class);

        // startActivity(login);


    }
}
