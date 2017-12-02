package com.example.lenovo.enjad.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.enjad.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chatlistActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        Toolbar actionbar=(Toolbar) findViewById(R.id.action_bar);//Creating object toolbar
        setSupportActionBar(actionbar);

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(chatlistActivity.this);
        pd.setMessage("Loading...");
        pd.show();
/////////////////////////////////////////////////////////////////////
        // Request a string response from the provided URL.
        String url = "https://enjadpp.firebaseio.com/chatlist";


    }
}


