package com.example.lenovo.enjad.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.enjad.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        process();
        Button loginB = (Button) findViewById(R.id.signin);
        TextView signupTV= (TextView) findViewById(R.id.signuptextview);


        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //move to login page
                Intent R = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(R);
            }
        });
        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //move to signup page
                Intent R = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(R);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        process();

    }

    public void process (){

        Bundle extras=getIntent().getExtras();

        if (extras!=null)
        {
            if (extras.getString("title").equals("حالة طارئة")){
                firebaseAuth= FirebaseAuth.getInstance();
                if (firebaseAuth.getCurrentUser() != null ) // check if user is already logged in
                     {
                         Intent R = new Intent(getApplicationContext(),chatActivity.class); // here must popup activity for receiving notification
                         finish();
                         startActivity(R);
                     }
            }
        }
        else
        {
            firebaseAuth= FirebaseAuth.getInstance();
            if (firebaseAuth.getCurrentUser() != null ) // check if user is already logged in
            {
                //profile activity here
                finish();
                startActivity(new Intent(getApplicationContext(),HomPageActivity.class));
            }
        }
    }
}
