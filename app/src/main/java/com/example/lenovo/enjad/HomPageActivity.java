package com.example.lenovo.enjad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;


    public class HomPageActivity extends AppCompatActivity {
        FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom_page);
        //starting updatelocation service
        startService(new Intent(HomPageActivity.this, UpdateLocation.class));


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

        //Start the timer to call the service after 15 minute
    //    myTimer.scheduleAtFixedRate(myTimerTask, 0, 900000);


    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //to tell if item is selected from the menu
        //Log out user
        firebaseAuth.signOut();
        Toast.makeText(getApplicationContext(),getString(R.string.success_Log_out), Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        //stop service from working
        stopService(new Intent(HomPageActivity.this, UpdateLocation.class));
        return super.onOptionsItemSelected(item);
    }


}
