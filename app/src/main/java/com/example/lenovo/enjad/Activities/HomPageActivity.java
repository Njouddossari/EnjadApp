package com.example.lenovo.enjad.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import com.example.lenovo.enjad.R;
import com.example.lenovo.enjad.Services.ScreenOnOffService;
import com.example.lenovo.enjad.Services.getlocationService;
import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;


public class HomPageActivity extends AppCompatActivity {
        FirebaseAuth firebaseAuth;
    DatabaseReference dbRefuser;
        TextView txt_username;
        int PERMISSION_ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hom_page);
        Toolbar actionbar=(Toolbar) findViewById(R.id.action_bar);//Creating object toolbar
        setSupportActionBar(actionbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = "notification report";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser userid = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() == null ) // check if user is not logged in
        {
            //login activity here
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        else{ //starting 2 services only when user logged in
            //Starting ScreenOnOffService
            try {
                String recent_token = FirebaseInstanceId.getInstance().getToken(); //notification token
                if ( recent_token!=null ){
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("user").child(userid.getUid()).child("notificationTokens").child(recent_token).setValue(true);}
            }
            catch(RuntimeException r)
            {
                Log.v(" error", "Error "+ r.getMessage());
            }
            dbRefuser= FirebaseDatabase.getInstance().getReference("user");
            txt_username=(TextView) findViewById(R.id.textView);
            dbRefuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB

                    String username = dataSnapshot.child(userid.getUid()).child("username").getValue(String.class);


                    txt_username.setText(username);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            Intent i0 = new Intent(this,ScreenOnOffService.class);
            i0.setAction("Services.ScreenOnOffService");
            startService(i0); //listen to power button


            long interval = 1000 * 60 * 15; // 5 minutes in milliseconds
            Context ctx = getApplicationContext();
            Calendar cal = Calendar.getInstance();
            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            Intent serviceIntent = new Intent(ctx, getlocationService.class);
            PendingIntent servicePendingIntent =
                    PendingIntent.getService(ctx,
                            getlocationService.SERVICE_ID, // integer constant used to identify the service
                            serviceIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running

            // checking for location permission
           if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                        PERMISSION_ACCESS_COARSE_LOCATION);
            }
          //  else {//if permission granted
                am.setRepeating(
                        AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                        cal.getTimeInMillis(),
                        interval,
                        servicePendingIntent
                );
           // }

        }

        Button ReportsB = (Button) findViewById(R.id.Reportsbutton);
        Button ProfileB = (Button) findViewById(R.id.Profilebutton);
        Button ChatsB = (Button) findViewById(R.id.Chatsbutton);



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
                Intent chls = new Intent(getApplicationContext(), chatActivity.class);
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

    }


    @Override //when permission has ont been granted
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_COARSE_LOCATION ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // All good!
            } else {
                //Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
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
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User_location");
        GeoFire geoFire = new GeoFire(myRef);
        geoFire.removeLocation((firebaseAuth.getCurrentUser().getUid()));
        firebaseAuth.signOut();
        Toast.makeText(getApplicationContext(),getString(R.string.success_Log_out), Toast.LENGTH_LONG).show();
        finish();
        // stop the services
        //1
        Intent i0 = new Intent(this,ScreenOnOffService.class);
        i0.setAction("Services.ScreenOnOffService");
        stopService(i0);
        //2
        Intent i1 = new Intent(this,getlocationService.class);
        i1.setAction("Services.getlocationService");
        stopService(i1);
        //Stop AlarmManager
        long interval = 1000 * 60 * 15; // 5 minutes in milliseconds
        Context ctx = getApplicationContext();
        Calendar cal = Calendar.getInstance();
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(ctx, getlocationService.class);
        PendingIntent servicePendingIntent =
                PendingIntent.getService(ctx,
                        getlocationService.SERVICE_ID, // integer constant used to identify the service
                        serviceIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running

        am.cancel(servicePendingIntent);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        return super.onOptionsItemSelected(item);
    }


}
