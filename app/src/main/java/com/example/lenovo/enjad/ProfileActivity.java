package com.example.lenovo.enjad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ProfileActivity extends AppCompatActivity {
    String username ,type;
    EditText usrname_et, pass_et , email_et , health_et , mobile_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username=getIntent().getExtras().getString("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        usrname_et= (EditText) findViewById(R.id.Usrnameet);
        pass_et= (EditText) findViewById(R.id.Passet);
        email_et= (EditText) findViewById(R.id.Emailet);
        health_et= (EditText) findViewById(R.id.Helathet);
        mobile_et=(EditText) findViewById(R.id.Phoneet);

        type="set_activitydata";
        profileDataLoaderBW BW= new profileDataLoaderBW(this);
        BW.execute(type,username);

    }
}
