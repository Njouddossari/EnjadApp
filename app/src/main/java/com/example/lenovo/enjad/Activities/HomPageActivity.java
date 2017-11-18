package com.example.lenovo.enjad.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;

import com.example.lenovo.enjad.R;
import com.example.lenovo.enjad.Services.ScreenOnOffService;
import com.example.lenovo.enjad.Services.getlocationService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;


public class HomPageActivity extends AppCompatActivity {
        FirebaseAuth firebaseAuth;
        int PERMISSION_ACCESS_COARSE_LOCATION;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // these flags to start activity on lock screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_hom_page);

        //Starting ScreenOnOffService
        Intent i0 = new Intent(this,ScreenOnOffService.class);
        i0.setAction(".ScreenOnOffService");
        startService(i0); //listen to power button

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

        //setting Alarm manager if permission granted
        Context ctx = getApplicationContext();
        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        long interval = 1000 * 60 * 15; // 5 minutes in milliseconds
        Intent serviceIntent = new Intent(ctx, getlocationService.class);
        // make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
        PendingIntent servicePendingIntent =
                PendingIntent.getService(ctx,
                        getlocationService.SERVICE_ID, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
        // checking for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
        else {//if permission granted
            am.setRepeating(
                    AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                    cal.getTimeInMillis(),
                    interval,
                    servicePendingIntent
            );
        }

    }


    @Override //when permission has ont been granted
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_COARSE_LOCATION ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // All good!
            } else {
                Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
            }

            return;
        }
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

        return super.onOptionsItemSelected(item);
    }


}
