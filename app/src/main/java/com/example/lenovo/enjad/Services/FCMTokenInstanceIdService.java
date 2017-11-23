package com.example.lenovo.enjad.Services;

import android.util.Log;

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
    }
}
