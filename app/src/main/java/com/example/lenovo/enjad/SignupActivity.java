package com.example.lenovo.enjad;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    Button busign; EditText usernameet, passwordet , emailet, reenterpasset , healthet , mobileet;
    String username;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null ) // check if user is not logged in
        {
           firebaseAuth.signOut();
        }

        databaseReference= FirebaseDatabase.getInstance().getReference();


        busign= (Button) findViewById(R.id.signbu);
        usernameet= (EditText) findViewById(R.id.usrnameet);
        passwordet= (EditText) findViewById(R.id.passwrdet);
        emailet= (EditText) findViewById(R.id.emailet);
        reenterpasset=(EditText) findViewById(R.id.reenterpasset);
        healthet= (EditText) findViewById(R.id.healthet);
        mobileet=(EditText) findViewById(R.id.phoneet);

    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"); //email pattern

    public void signuponclick(View view) {

        if (usernameet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredUserName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (emailet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (EMAIL_ADDRESS_PATTERN.matcher(emailet.getText().toString().trim()).matches() == false) {
            Toast.makeText(this, getString(R.string.Msg_FormateEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if (healthet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredHealth), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobileet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_Requiredphone), Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordet.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(this, getString(R.string.Msg_RequiredPassword), Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordet.getText().toString().trim().equals(reenterpasset.getText().toString().trim()) == false)//confirm the password
        {
            Toast.makeText(this, getString(R.string.Msg_ConfirmPassword), Toast.LENGTH_SHORT).show();
            return;
        }

        username = usernameet.getText().toString().trim();
        if (username.contains(",") || username.contains("-")
                || username.contains("+") || username.contains("#")
                || username.contains("$") || username.contains("%")
                || username.contains("^") || username.contains("|")
                || username.contains("{") || username.contains("}")
                || username.contains("(") || username.contains(")")
                || username.contains("[") || username.contains("]")
                || username.contains("<") || username.contains(">")
                || username.contains(" ") || username.contains("!")
                || username.contains("~")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.Msg_ErrorTitle));
            alertDialog.setMessage(getString(R.string.Msg_FormatUserName));
            alertDialog.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            alertDialog.setIcon(R.drawable.enjadapplogoicon);
            alertDialog.show();
            return;
        }



        firebaseAuth.createUserWithEmailAndPassword(emailet.getText().toString().trim(),passwordet.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if ( task.isSuccessful())
                        {
                            //Toast.makeText(getApplicationContext(), getString(R.string.success_sign_in), Toast.LENGTH_LONG).show();
                            //user is successfully registered
                            //insert all user info. to the DB
                            saveUserInfo(usernameet.getText().toString().trim(),emailet.getText().toString().trim(),healthet.getText().toString().trim()
                                    , mobileet.getText().toString().trim(), passwordet.getText().toString());
                            //logged in system and start home page activity
                            startActivity(new Intent (getApplicationContext(),HomPageActivity.class));


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_confrim), Toast.LENGTH_LONG).show();
                            return ;
                        }

                    }
                });


        //bw.execute(type, usernameet.getText().toString().trim(), passwordet.getText().toString(),  emailet.getText().toString().trim(),healthet.getText().toString().trim(), mobileet.getText().toString().trim());
    }

    public void saveUserInfo(String name , String email, String health_info , String phone , String pass  )
    {
        String username1=name;
        String Email=email;
        String H_info=health_info;
        String mobile=phone;
        String password=pass;
        User newuser= new User (username1, Email, H_info,mobile,password);
        user=firebaseAuth.getCurrentUser();
        databaseReference.child("user").child(user.getUid()).setValue(newuser);




    }




}


