package com.example.lenovo.enjad;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Button bulogin; EditText emailet, passwordet;
    TextView toregister;
    String email , password;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null ) // check if user is already logged in
        {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),HomPageActivity.class));
        }
        bulogin= (Button) findViewById(R.id.loginbu);
        emailet= (EditText) findViewById(R.id.emailET);
        passwordet= (EditText) findViewById(R.id.Passwordet);
        progressDialog= new ProgressDialog(this);


        toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }});

    }
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"); //email pattern

    public void loginonclick(View view) {
        if (emailet.getText().toString().trim().length() == 0) {
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle(getString(R.string.Msg_ErrorTitle));
            ad.setMessage(getString(R.string.Msg_RequiredEmail));
            ad.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            ad.setIcon(R.drawable.enjadapplogoicon); //i put the icon of app //Njoud changed app icon for better resolution
            ad.show();
            return;
        }

        if (EMAIL_ADDRESS_PATTERN.matcher(emailet.getText().toString().trim()).matches() == false) {
            Toast.makeText(this, getString(R.string.Msg_FormateEmail), Toast.LENGTH_SHORT).show();
            return;
        }
        if ( passwordet.getText().toString().equals("") ) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.Msg_ErrorTitle));
            alertDialog.setMessage(getString(R.string.Msg_Requiredpass));
            alertDialog.setButton(getString(R.string.Msg_ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // here you can add functions
                        }
                    });
            alertDialog.setIcon(R.drawable.enjadapplogoicon);
            alertDialog.show();
            return;
        }
       password= passwordet.getText().toString().trim();
        email=emailet.getText().toString().trim();
        progressDialog.setMessage("Login .... ");
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),getString(R.string.success_Log_in), Toast.LENGTH_LONG).show();
                            //user is successfully logged to the system
                            //start home page activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),HomPageActivity.class));


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), getString(R.string.error_confrim), Toast.LENGTH_LONG).show();
                        }

                    }
                });



           // backgrandworker.execute(type, usernameet.getText().toString(), passwordet.getText().toString());

        }
        //Intent login = new Intent (this,HomPageActivity.class);

        // startActivity(login);
        public void getDataLogin(String result)
        {
            /*if (result.toString().trim().equals("1")) //trim() omit the white space from starting and the ending in the varible
            {
                Intent login = new Intent(this, HomPageActivity.class);
                login.putExtra("username",usernameet.getText().toString().trim()); // to move username data to Home page avtivity
                startActivity(login);
            }
            else
            {
                Toast.makeText(getApplicationContext(), getString(R.string.failed_login), Toast.LENGTH_LONG).show(); //message
            }*/

        }


}



