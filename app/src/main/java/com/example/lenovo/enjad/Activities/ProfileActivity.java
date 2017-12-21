package com.example.lenovo.enjad.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.enjad.R;
import com.example.lenovo.enjad.FirebaseFiles.User;
import com.example.lenovo.enjad.FirebaseFiles.configuration;
import com.example.lenovo.enjad.FirebaseFiles.user_contactlist;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_LONG;

public class ProfileActivity extends AppCompatActivity {

    EditText usrname_et, pass_et , email_et , health_et , mobile_et ,  contact1_et , contact2_et , contact3_et;
    ImageView contact1, contact2, contact3;
    Button save, delete;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference dbRefuser;
    DatabaseReference dbRefconfig;
    DatabaseReference dbRefcontact;
    Switch providehelpSW , acesscontactsw;
    static Boolean flag = true;
    String con_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toolbar actionbar=(Toolbar) findViewById(R.id.action_bar);//Creating object toolbar
        setSupportActionBar(actionbar);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null ) // check if user is not logged in
        {//login activity here
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));}

        user=firebaseAuth.getCurrentUser();
        dbRefuser= FirebaseDatabase.getInstance().getReference("user"); //connect to the database to -node(table)-  user
        dbRefconfig=FirebaseDatabase.getInstance().getReference("configuration"); //connect to the database to -node(table)-  configuration
        dbRefcontact=FirebaseDatabase.getInstance().getReference("user_contactlist");//connect to the database to -node(table)- user_contactlist

        con_id="";
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
        contact1 = (ImageView) findViewById(R.id.contact1);
        contact2 = (ImageView) findViewById(R.id.contact2);
        contact3 = (ImageView) findViewById(R.id.contact3);
        save = (Button) findViewById(R.id.savechngeBU2);
        delete = (Button) findViewById(R.id.DeletBU);

        dbRefuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  //upload user_info from dB

                String username = dataSnapshot.child(user.getUid()).child("username").getValue(String.class);
                String health_info = dataSnapshot.child(user.getUid()).child("health_info").getValue(String.class);
                String phone = dataSnapshot.child(user.getUid()).child("phone").getValue(String.class);
                String password = dataSnapshot.child(user.getUid()).child("password").getValue(String.class);
            //    String help = dataSnapshot.child(user.getUid()).child("password").getValue(String.class);

                usrname_et.setText(username);
                health_et.setText(health_info);
                mobile_et.setText(phone);
                pass_et.setText(password);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //upload switch infor from DB --> Njoud
        dbRefconfig.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  // providehelpSW.setChecked(dataSnapshot.child(user.getUid()).child("act_as_helper").getValue(Boolean.class));
                   // acesscontactsw.setChecked(dataSnapshot.child(user.getUid()).child("inform_contact_list").getValue(Boolean.class));
                providehelpSW.setChecked(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        acesscontactsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ==true )
                {
                    contact1_et.setVisibility(View.VISIBLE);
                    contact2_et.setVisibility(View.VISIBLE);
                    contact3_et.setVisibility(View.VISIBLE);
                    contact1.setVisibility(View.VISIBLE);
                    contact2.setVisibility(View.VISIBLE);
                    contact3.setVisibility(View.VISIBLE);

                }
                else
                {
                    contact1_et.setVisibility(View.INVISIBLE);
                    contact2_et.setVisibility(View.INVISIBLE);
                    contact3_et.setVisibility(View.INVISIBLE);
                    contact1.setVisibility(View.INVISIBLE);
                    contact2.setVisibility(View.INVISIBLE);
                    contact3.setVisibility(View.INVISIBLE);

                }
            }
        });
        //set Buttons to save an delete user information
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //move to Profile page
                saveInfobu(view);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(view);
            }
        });

    }


    public final Pattern phone_PATTERN = Pattern
            .compile("^[0-9]{10}$");
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"); //email pattern



    public void save_config() { //save configuration info. in DB

        String flag_provideHelp , flag_access_contact ;


        if (providehelpSW.isChecked())
        {flag_provideHelp="1";}
        else
        {flag_provideHelp="0";}

        if (acesscontactsw.isChecked()) // add the contact phone to db
        {flag_access_contact="1";}
        else
        {flag_access_contact="0";}

        if ( flag ) { //to generate the key once
            con_id = dbRefconfig.child(user.getUid()).push().getKey(); //generate unique id
            flag=false;
        }
        //con_id = dbRefconfig.child(user.getUid()).getKey();
        con_id="1";
        configuration con = new configuration(con_id,flag_provideHelp,flag_access_contact );
        dbRefconfig.child(user.getUid()).setValue(con); //insert to db to configuration table
   }

    public String save_contactlist(String username ) {//save contactlist info. in DB
        String contact1 , contact2, contact3;
        user_contactlist contact;
        if (acesscontactsw.isChecked() ) // add the contact phone to db
        {
            contact1= contact1_et.getText().toString().trim();
            if (!TextUtils.isEmpty(contact1)) {
                contact = new user_contactlist(username, contact1);
                dbRefcontact.child(user.getUid()).child("1").setValue(contact);
                contact2= contact2_et.getText().toString().trim();
                if (!TextUtils.isEmpty(contact2)) {
                    contact = new user_contactlist(username, contact2);
                    dbRefcontact.child(user.getUid()).child("2").setValue(contact);
                    contact3= contact3_et.getText().toString().trim();
                    if (!TextUtils.isEmpty(contact3)) {
                        contact = new user_contactlist(username, contact3);
                        dbRefcontact.child(user.getUid()).child("3").setValue(contact);
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

        if (EMAIL_ADDRESS_PATTERN.matcher(email_et.getText().toString().trim()).matches() == false) {
            Toast.makeText(this, getString(R.string.Msg_FormateEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone_PATTERN.matcher(mobile_et.getText().toString().trim()).matches() == false) {
            Toast.makeText(this, getString(R.string.Msg_Formatephone), Toast.LENGTH_SHORT).show();
            return;
        }

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

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //to tell if item is selected from the menu

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User_location");
        GeoFire geoFire = new GeoFire(myRef);
        geoFire.removeLocation((firebaseAuth.getCurrentUser().getUid()));
        firebaseAuth.signOut();
        Toast.makeText(getApplicationContext(),getString(R.string.success_Log_out), LENGTH_LONG).show();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public void deleteUser(View view) {

        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("User_location");
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();
        myRef2.child("user").child(user.getUid()).setValue(null);
        myRef2.child("configuration").child(user.getUid()).setValue(null);
        myRef2.child("Report").child(user.getUid()).setValue(null);
        myRef2.child("LastReport").child(user.getUid()).setValue(null);
        GeoFire geoFire = new GeoFire(myRef1);
        geoFire.removeLocation(user.getUid());

        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), getString(R.string.deleteuser), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (getApplicationContext(),MainActivity.class));

                }
            }
        });

    }
}
