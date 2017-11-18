package com.example.lenovo.enjad.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by USER on 11/18/2017.
 */

public class getlocationService extends Service implements LocationListener{

    public static final int SERVICE_ID = 1;
    private LocationManager locationmanager;
    private  String provider;


    @Override
    public void onCreate() {

        databaseReference= FirebaseDatabase.getInstance().getReference();
        //location manager
        locationmanager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        provider = locationmanager.getBestProvider(new Criteria(), false);
        Location location =locationmanager.getLastKnownLocation(provider);
        if(location!= null)
            Log.i("Log info", "Location Archived");
        else
            Log.i("Log info", "No location");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getBaseContext(), "onStart()have been successfully called ", Toast.LENGTH_LONG).show();
        locationmanager.requestLocationUpdates(provider, 400,1,this);
        super.onStart(intent, startId);
    }


    @Override
    public void onDestroy() {
        Toast.makeText(getBaseContext(), "onDestroy()have been successfully called ", Toast.LENGTH_LONG).show();
        locationmanager.removeUpdates(this);
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //Location's functions
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getBaseContext(), "onLocationChanged()have been called: ", Toast.LENGTH_LONG).show();
        //user =firebaseAuth.getCurrentUser();

        Double lat, lng;
        lat =location.getLatitude();
        lng = location.getAltitude();
        Toast.makeText(getBaseContext(), "lat and lng"+lat +", "+lng, Toast.LENGTH_LONG).show();
    /* if(lat != null)
           databaseReference.child("user").child(user.getUid()).child("location_lat").setValue(lat);
       if(lng!= null)
           databaseReference.child("user").child(user.getUid()).child("location_lang").setValue(lng);*/

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
