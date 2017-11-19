package com.example.lenovo.enjad.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.enjad.JavaClasses.User;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by USER on 11/18/2017.
 */

public class getlocationService extends Service implements LocationListener {

    public static final int SERVICE_ID = 1;
    private LocationManager locationmanager;
    private String provider;
    private final int FINE_LOCATION_PERMISSION = 9999;
    boolean mRequestingLocationUpdates = false;


    @Override
    public void onCreate() {

        //location manager
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationmanager.getBestProvider(new Criteria(), false);
        /*  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[](Manifest.permission.ACCESS_FINE_LOCATION), FINE_LOCATION_PERMISSION);
            return;
        }*/
        Location location = locationmanager.getLastKnownLocation(provider);
        if (location != null)
            Log.i("Log info", "Location Archived");
        else
            Log.i("Log info", "No location");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getBaseContext(), "onStart()have been successfully called ", Toast.LENGTH_LONG).show();
      /*  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[](Manifest.permission.ACCESS_FINE_LOCATION), FINE_LOCATION_PERMISSION);
            return;
        }*/
        locationmanager.requestLocationUpdates(provider, 400, 1, this);
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
    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(getBaseContext(), "onLocationChanged()have been called: ", Toast.LENGTH_LONG).show();
        Double lat = 0.00;
        Double lng = 0.00;

        if (lat == location.getLatitude()  ||  lng == location.getAltitude()){
            Toast.makeText(getBaseContext(), "Same Location", Toast.LENGTH_LONG).show();
            return;}
        else {
            lat = location.getLatitude();
            lng = location.getAltitude();
            Toast.makeText(getBaseContext(), "lat and lng" + lat + ", " + lng, Toast.LENGTH_LONG).show();
            boolean val = updatelocation(lat, lng);
            if (!val) {
                Toast.makeText(getBaseContext(), "lat and lng NOT stored", Toast.LENGTH_LONG).show();
            } else if (val)
                Toast.makeText(getBaseContext(), "lat and lng stored", Toast.LENGTH_LONG).show();
        }



    }

    private boolean updatelocation(Double lat, Double lng) {
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser userid =  firebaseAuth.getCurrentUser();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        if(lat != null){
            myRef.child("user").child(userid.getUid()).child("location_lat").setValue(lat);
         if(lng!= null){
            myRef.child("user").child(userid.getUid()).child("location_lang").setValue(lng);}
            return true;}
        else{
        return false;}

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
