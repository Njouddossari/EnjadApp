package com.example.lenovo.enjad;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    EditText usrname_et, pass_et , email_et , health_et , mobile_et ,  contact1_et , contact2_et , contact3_et;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference dbRefuser;
    DatabaseReference dbRefconfig;
    DatabaseReference dbRefcontact;
    Switch providehelpSW , acesscontactsw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null ) // check if user is not logged in
        {//login activity here
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));}

        user=firebaseAuth.getCurrentUser();
        dbRefuser= FirebaseDatabase.getInstance().getReference("user"); //connect to the database to -node(table)-  user
        dbRefconfig=FirebaseDatabase.getInstance().getReference("configuration"); //connect to the database to -node(table)-  configuration
        dbRefcontact=FirebaseDatabase.getInstance().getReference("user_contactlist");//connect to the database to -node(table)- user_contactlist

        usrname_et= (EditText) findViewById(R.id.Usrnameet);
        pass_et= (EditText) findViewById(R.id.Passet);
        email_et= (EditText) findViewById(R.id.Emailet);
        email_et.setText(user.getEmail());
        health_et= (EditText) findViewById(R.id.Helathet);
        mobile_et=(EditText) findViewById(R.id.Phoneet);
        providehelpSW=(Switch) findViewById(R.id.providehelpSW);
        acesscontactsw= (Switch) findViewById(R.id.accesstocontctSW);
        contact1_et= (EditText) findViewById(R.id.contact1et);
        contact2_et= (EditText) findViewById(R.id.contact2et);
        contact3_et= (EditText) findViewById(R.id.contact3et);

        dbRefuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB

                String username = dataSnapshot.child(user.getUid()).child("username").getValue(String.class);
                String health_info = dataSnapshot.child(user.getUid()).child("health_info").getValue(String.class);
                String phone = dataSnapshot.child(user.getUid()).child("phone").getValue(String.class);
                String password = dataSnapshot.child(user.getUid()).child("password").getValue(String.class);

                usrname_et.setText(username);
                health_et.setText(health_info);
                mobile_et.setText(phone);
                pass_et.setText(password);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        acesscontactsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ==true )
                {
                    contact1_et.setVisibility(View.VISIBLE);
                    contact2_et.setVisibility(View.VISIBLE);
                    contact3_et.setVisibility(View.VISIBLE);

                }
                else
                {
                    contact1_et.setVisibility(View.INVISIBLE);
                    contact2_et.setVisibility(View.INVISIBLE);
                    contact3_et.setVisibility(View.INVISIBLE);}
            }
        });


    }




    public void save_config() { //save configuration info. in DB

        String flag_provideHelp , flag_access_contact ,con_id;

        if (providehelpSW.isChecked())
        {flag_provideHelp="1";}
        else
        {flag_provideHelp="0";}

        if (acesscontactsw.isChecked()) // add the contact phone to db
        {flag_access_contact="1";}
        else
        {flag_access_contact="0";}

        con_id=dbRefconfig.child(user.getUid()).push().getKey(); //generate unique id
        configuration con = new configuration(con_id,flag_provideHelp,flag_access_contact );
        dbRefconfig.child(user.getUid()).child(con_id).setValue(con); //insert to db to configuration table
   }

    public String save_contactlist(String username ) {//save contactlist info. in DB
        String contact1 , contact2, contact3;
        user_contactlist contact;
        if (acesscontactsw.isChecked() ) // add the contact phone to db
        {
            contact1= contact1_et.getText().toString().trim();
            if (!TextUtils.isEmpty(contact1)) {
                contact = new user_contactlist(username, contact1);
                dbRefcontact.child(user.getUid()).setValue(contact);
                contact2= contact2_et.getText().toString().trim();
                if (!TextUtils.isEmpty(contact2)) {
                    contact = new user_contactlist(username, contact2);
                    dbRefcontact.child(user.getUid()).setValue(contact);
                    contact3= contact3_et.getText().toString().trim();
                    if (!TextUtils.isEmpty(contact3)) {
                        contact = new user_contactlist(username, contact3);
                        dbRefcontact.child(user.getUid()).setValue(contact);
                        return "1";
                    }
                    else { Toast.makeText(this, getString(R.string.fill_contact), Toast.LENGTH_SHORT).show();
                        return "-1";}

                }
                else { Toast.makeText(this, getString(R.string.fill_contact), Toast.LENGTH_SHORT).show();
                    return "-1";}

            }
            else
            {
                Toast.makeText(this, getString(R.string.fill_contact), Toast.LENGTH_SHORT).show();
                return "-1";
            }
        }
        else
        {return "0";}


    }
    public void saveUserInfo(String name , String email, String health_info , String phone , String pass  )
    {
        String username1=name;
        String Email=email;
        String H_info=health_info;
        String mobile=phone;
        String password=pass;
        User newuser= new User (username1, Email, H_info,mobile,password); //save user information in object
        user=firebaseAuth.getCurrentUser();// which user is signed in system
        dbRefuser.child(user.getUid()).setValue(newuser); // insert the user info to DB


    }

    public void saveInfobu(View view) {
        String flag;
        //insert to db to configuration table
        save_config();


        flag= save_contactlist(usrname_et.getText().toString().trim());
        if ( flag =="1" || flag =="0")
        {
            //save the user info to DB
            saveUserInfo( usrname_et.getText().toString().trim(),email_et.getText().toString().trim(),health_et.getText().toString().trim()
                    , mobile_et.getText().toString().trim(), pass_et.getText().toString());
            Toast.makeText(this, getString(R.string.save_change), Toast.LENGTH_SHORT).show();
        }
        else
        {
            return;
        }



    }
}
