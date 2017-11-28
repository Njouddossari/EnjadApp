package com.example.lenovo.enjad.Services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by LENOVO on 22/11/17.
 */

public class FCMTokenInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        Log.w("Token Refershed", true + "");
        String recent_token= FirebaseInstanceId.getInstance().getToken(); //notification token
        Log.w("REGESTRAITON Token", recent_token+ "");
        //send the token to the firebase database
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser userid = firebaseAuth.getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        if ( firebaseAuth.getCurrentUser() != null ){
        myRef.child("user").child(userid.getUid()).child("notificationTokens").child(recent_token).setValue(true);}

    }
}
