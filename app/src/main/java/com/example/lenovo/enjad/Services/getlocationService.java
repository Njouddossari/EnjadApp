package com.example.lenovo.enjad.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
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


    @Override
    public void onCreate() {

        //location manager
        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //getting location from gps
       // provider = LocationManager.GPS_PROVIDER;
        /* or could use users wifi*/
        provider =LocationManager.NETWORK_PROVIDER;

        //needs to check if permission granted, but already checked on main activity
        //it will have a redline but it works just fine
        Location location = locationmanager.getLastKnownLocation(provider);
        if (location != null)
            Log.i("Log info", "Location Archived");
        else
            Log.i("Log info", "No location");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        //needs to check if permission granted, but already checked on main activity
        //it will have a redline but it works just fine
        locationmanager.requestLocationUpdates(provider, 100000, 10, this);
        Location lastKnownLocation = locationmanager.getLastKnownLocation(provider);
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

        public void onLocationChanged(Location location) {
            Double lat , lng ,newLong,newLat;
            // Called when a new location is found by the network location provider.
            Location currentLocation = new Location(provider);
             newLong =location.getLongitude();
             newLat =location.getLatitude();
           // if (calculateDistance(newLat,newLong,currentLocation.getLatitude(), currentLocation.getLongitude()) && isBetterLocation(location, currentLocation)) {
                lat = newLat;
                lng = newLong;
                currentLocation = location;

                boolean val = updatelocation(lng,lat);
                if (!val) {
                    Toast.makeText(getBaseContext(), "lat and lng NOT stored", Toast.LENGTH_LONG).show();
                } else if (val)
                    Toast.makeText(getBaseContext(), "lat and lng stored", Toast.LENGTH_LONG).show();
        //    } else {
               // Toast.makeText(getBaseContext(), "Same Location", Toast.LENGTH_LONG).show();
          //  }

           // locationmanager.removeUpdates(locationListener);

            //  makeUseOfNewLocation(location);
        }

        //function to store location on firease
        private boolean updatelocation(Double lng, Double lat) {
            // FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser userid = firebaseAuth.getCurrentUser();
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User_location");
            DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
            GeoFire geoFire = new GeoFire(myRef);
            if (lat != null) {

                myRef1.child("user").child(userid.getUid()).child("location_lat").setValue(lat);
                if (lng != null) {
                    geoFire.setLocation(userid.getUid(), new GeoLocation(lat,lng));
                    myRef1.child("user").child(userid.getUid()).child("location_lang").setValue(lng);
                }
                return true;
            } else {
                return false;
            }

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
