package com.example.lenovo.enjad;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomPageActivity extends AppCompatActivity {
    String username;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom_page);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null ) // check if user is not logged in
        {
            //login activity here
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        Button ReportsB = (Button) findViewById(R.id.Reportsbutton);
        Button ProfileB = (Button) findViewById(R.id.Profilebutton);
        Button ChatsB = (Button) findViewById(R.id.Chatsbutton);

        Toolbar actionbar=(Toolbar) findViewById(R.id.action_bar);//Creating object toolbar
        setSupportActionBar(actionbar);


        ProfileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //move to Profile page
                Intent R = new Intent(getApplicationContext(),ProfileActivity.class);
                R.putExtra("username",username);
                startActivity(R);
            }
        });

        ChatsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // move to chat list page
                Intent chls = new Intent(getApplicationContext(), chatlistActivity.class);
                startActivity(chls);
            }
        });

        ReportsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //move to report list page
                Intent rep = new Intent(getApplicationContext(), ReportlistActivity.class);
                startActivity(rep);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //to fill the menu with the items in menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //to tell if item is selected from the menu
        return super.onOptionsItemSelected(item);
    }

    /*
    function logout
    {
            firebaseAuth.signOut();
            Toast.makeText(getApplicationContext(),قgetString(R.string.success_Log_out), Toast.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

     */
}
