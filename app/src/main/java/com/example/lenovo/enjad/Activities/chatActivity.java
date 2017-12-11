package com.example.lenovo.enjad.Activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.enjad.JavaClasses.reporterInfo;
import com.example.lenovo.enjad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

public class chatActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar actionbar=(Toolbar) findViewById(R.id.action_bar);//Creating object toolbar
        setSupportActionBar(actionbar);
    }


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) { //to tell if item is selected from the menu
        //Start Reporter info dialog

        final View addView = getLayoutInflater().inflate(R.layout.report_info_dialog, null);
       // ImageButton Mute = (ImageButton) findViewById(R.id.MuteButton);
      // final TextView info = (TextView) addView.findViewById(R.id.info1);
      //final   TextView Etype = (TextView) addView.findViewById(R.id.emergtype1);
      //final   TextView severity = (TextView) addView.findViewById(R.id.severity1);
      //final  TextView location = (TextView) addView.findViewById(R.id.Location1);

        mDatabase = FirebaseDatabase.getInstance().getReference("LastReport");


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String uid = mDatabase.push().getKey();
                    if (uid != null) {

                        // UID specific to the provider

                        String inform = dataSnapshot.child("LastReport").child(uid).child("Emerg_status").getValue(String.class);
                        String emergType = dataSnapshot.child("LastReport").child(uid).child("Emerg_type").getValue(String.class);
                        String Severity = dataSnapshot.child("LastReport").child(uid).child("severity").getValue(String.class);
                        String Location = dataSnapshot.child("User_location").child(uid).child("g").getValue(String.class);

                        //info.setText(inform);
                        //Etype.setText(emergType);
                        //severity.setText(Severity);
                        //location.setText(Location);

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        new AlertDialog.Builder(this).setTitle("معلومات البلاغ").setView(addView)
                .setPositiveButton("إغلاق", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();

        return (super.onOptionsItemSelected(item));}
}