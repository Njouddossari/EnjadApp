package com.example.lenovo.enjad.Activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lenovo.enjad.FirebaseFiles.Report;
import com.example.lenovo.enjad.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HelpRequest extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    String username;
    RadioButton userInput1 , userInput2 , userInput3 ,  userInput4, userInput5;
    TextView others;
    public Double lng , lat;
    List <String >  nearest_helpers_id = new ArrayList<>(); // to store nearest helpers id
    List <String > helpers_id = new ArrayList<>(); // to store helpers id

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
        setContentView(R.layout.activity_test);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null ) // check if user is not logged in
        {
            //login activity here
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        user=firebaseAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB
                username = dataSnapshot.child(user.getUid()).child("username").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


        userInput1 = (RadioButton) findViewById(R.id.radioButton);
        userInput1.setOnCheckedChangeListener(this);
        userInput2 = (RadioButton) findViewById(R.id.radioButton2);
        userInput2.setOnCheckedChangeListener(this);
        userInput3 = (RadioButton) findViewById(R.id.radioButton3);
        userInput3.setOnCheckedChangeListener(this);
        userInput4 = (RadioButton) findViewById(R.id.radioButton4);
        userInput4.setOnCheckedChangeListener(this);
        userInput5 = (RadioButton) findViewById(R.id.radioButton5);
        userInput5.setOnCheckedChangeListener(this);
        others = (TextView) findViewById(R.id.othertype);
        Button send_report_BU = (Button) findViewById(R.id.btnsend);


        //to send the report with no report type selected after 10 secoend --> Njoud
        new CountDownTimer(10000,  1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                //send report with unknown type
                Report new_report = new Report ("عالي", "غيرمعروف", "نشط" , username);
                SaveReport(new_report);
                finish();
            }
        }.start();




        send_report_BU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //intaite the report

                // we can select the severity based on   the user choice if the user not select the type
                // , the severity  of the report = unknown
                String type, severity;
                if (userInput1.isChecked() ){
                    type="حادث";
                    severity ="عالي";
                    Report new_report = new Report (severity, type, "نشط" , username);
                    SaveReport(new_report);
                }
                else if (userInput2.isChecked() ){
                    type="حالة إسعافية";
                    severity="عالي";
                    Report new_report = new Report (severity, type, "نشط" , username);
                    SaveReport(new_report);
                }
                else if (userInput3.isChecked() ){
                    type="سرقة";
                    severity="منخفض";
                    Report new_report = new Report (severity, type, "نشط" , username);
                    SaveReport(new_report);
                }
                else if (userInput4.isChecked() ){
                    type="حريق";
                    severity="عالي";
                    Report new_report = new Report (severity, type, "نشط" , username);
                    SaveReport(new_report);
                }
                else if (userInput5.isChecked() ){
                    if (others != null){
                        type = others.getText().toString().trim();
                        severity="منخفض";
                        Report new_report = new Report (severity, type, "نشط" , username);
                        SaveReport(new_report);
                    }
                    else{
                        type="غيرمعروف";
                        severity="عالي";
                        Report new_report = new Report (severity, type, "نشط" , username);
                        SaveReport(new_report);
                    }
                }
                //---
                //now we search for nearest user through Geofire query
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User_location");
                GeoFire geoFire = new GeoFire(myRef);
                geoFire.getLocation(user.getUid(), new LocationCallback() { // get the current location of reporter
                    @Override
                    public void onLocationResult(String key, GeoLocation location) {
                        if (location != null) {
                            lat=location.latitude;
                            lng=location.longitude;
                            Log.v("location is saved", " location lat "+ lat);
                        } else {
                            Log.v("location error", "There is no location");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.v(" error", "Error with Geo fire location : " + databaseError);
                    }
                });

                //  3 KM range
                try {

                    GeoQuery find_nearest_helpers_query = geoFire.queryAtLocation(new GeoLocation(lat, lng), 3);
                    find_nearest_helpers_query.removeAllListeners();//find nearest helpers
                    Log.v(" remove all lisiner", "Testing ");
                    find_nearest_helpers_query.addGeoQueryEventListener(new GeoQueryEventListener() {
                        @Override
                        public void onKeyEntered(String key, GeoLocation location) { // The location of a key now matches the query criteria.
                            DatabaseReference configRef = FirebaseDatabase.getInstance().getReference("configuration");
                            int helper = configRef.child("key").child("act_as_helper").toString().compareTo("1");
                            if(helper == 1){
                                nearest_helpers_id.add(key);
                                Log.v("matched", "User added with id: " + key);
                            }
                        }

                        @Override
                        public void onKeyExited(String key) {

                        }

                        @Override
                        public void onKeyMoved(String key, GeoLocation location) {

                        }

                        @Override
                        public void onGeoQueryReady() {
                            Log.v("Finish", "the find nearest Query was executed");
                            nearest_helpers_id.remove(user.getUid());
                        }

                        @Override
                        public void onGeoQueryError(DatabaseError error) {

                        }
                    });

//check if the helper from contact
                    //first check the his configuration
                    DatabaseReference configRef = FirebaseDatabase.getInstance().getReference("configuration");
                    configRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB
                            String check_contact= dataSnapshot.child(user.getUid()).child("inform_contact_list").getValue(String.class);

                            if ( check_contact=="1"){// if it is enabled

                                //check for contact
                                DatabaseReference contactRef = FirebaseDatabase.getInstance().getReference("user_contactlist");
                                contactRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB
                                        final String contact_1 = dataSnapshot.child(user.getUid()).child("1").child("contact_phone").getValue(String.class);
                                        final String contact_2 = dataSnapshot.child(user.getUid()).child("2").child("contact_phone").getValue(String.class);
                                        final String contact_3 = dataSnapshot.child(user.getUid()).child("3").child("contact_phone").getValue(String.class);
                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user");
                                        userRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB
                                                for ( String id : nearest_helpers_id){
                                                    if (  (dataSnapshot.child(id).child("phone").getValue(String.class)).equals(contact_1)){
                                                        helpers_id.add(id);
                                                        continue;
                                                    }else  if (  (dataSnapshot.child(id).child("phone").getValue(String.class)).equals(contact_2)){
                                                        helpers_id.add(id);
                                                        continue;
                                                    }else  if (  (dataSnapshot.child(id).child("phone").getValue(String.class)).equals(contact_3)){
                                                        helpers_id.add(id);
                                                        continue;
                                                    }
                                                    if ( helpers_id.size() == 3 ) // 3 contacts all from nearest helper
                                                        break;

                                                }
                                                if ( helpers_id.size() == 0 ) // there is no matched
                                                    helpers_id=nearest_helpers_id;
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {}
                                        });
                                        //if ( userRef.child(user.getUid()).child("phone").ge


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });

                            }
                            else
                            {
                                helpers_id=nearest_helpers_id;
                                Log.v("At the end", "helpers_id: "+ helpers_id.get(0));

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                    DatabaseReference nearestRef = FirebaseDatabase.getInstance().getReference("nearest");
                    for (String nearest_id : helpers_id)
                    {
                        nearestRef.child(nearest_id).child(user.getUid()).setValue(true);
                    }

                }catch(RuntimeException r)
                {
                    Log.v(" error", "Error "+ r.getMessage());
                }


                Intent R = new Intent(getApplicationContext(),HomPageActivity.class);
                startActivity(R);

            }

        });
    }

    public void SaveReport(Report obj) { //to send the report to DB
        user=firebaseAuth.getCurrentUser();// which user is signed in system
        DatabaseReference ReportRef ,ReportRef1;
        ReportRef= FirebaseDatabase.getInstance().getReference("Report");
        ReportRef1= FirebaseDatabase.getInstance().getReference("LastReport");
        String id= obj.getReport_id();
        ReportRef.child(user.getUid()).child( id).setValue(obj);
        ReportRef1.child(user.getUid()).setValue(obj); // for notification purpose
        Log.v(" save", "seve report succuess");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.radioButton) {
                userInput2.setChecked(false);
                userInput3.setChecked(false);
                userInput4.setChecked(false);
                userInput5.setChecked(false);
            }
            if (buttonView.getId() == R.id.radioButton2) {
                userInput1.setChecked(false);
                userInput3.setChecked(false);
                userInput4.setChecked(false);
                userInput5.setChecked(false);
            }
            if (buttonView.getId() == R.id.radioButton3) {
                userInput1.setChecked(false);
                userInput2.setChecked(false);
                userInput4.setChecked(false);
                userInput5.setChecked(false);
            }
            if (buttonView.getId() == R.id.radioButton4) {
                userInput1.setChecked(false);
                userInput3.setChecked(false);
                userInput2.setChecked(false);
                userInput5.setChecked(false);
            }
            if (buttonView.getId() == R.id.radioButton5) {
                userInput1.setChecked(false);
                userInput3.setChecked(false);
                userInput2.setChecked(false);
                userInput4.setChecked(false);
            }
        }
    }
}
