package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    String username ,type;
    EditText usrname_et, pass_et , email_et , health_et , mobile_et;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username=getIntent().getExtras().getString("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null ) // check if user is not logged in
        {
            //login activity here
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        user=firebaseAuth.getCurrentUser();

        usrname_et= (EditText) findViewById(R.id.Usrnameet);
        pass_et= (EditText) findViewById(R.id.Passet);

        email_et= (EditText) findViewById(R.id.Emailet);
        email_et.setText(user.getEmail());
        health_et= (EditText) findViewById(R.id.Helathet);
        mobile_et=(EditText) findViewById(R.id.Phoneet);



    }
}
