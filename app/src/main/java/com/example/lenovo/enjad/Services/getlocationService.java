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
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
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
        Toast.makeText(getBaseContext(), "onStart()have been successfully called ", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getBaseContext(), "onLocationChanged()have been called: ", Toast.LENGTH_LONG).show();
            Location currentLocation = new Location(provider);
             newLong =location.getLongitude();
             newLat =location.getLatitude();
            Log.v("loggg", "IN ON LOCATION CHANGE, lat=" +newLat + ", lon=" + newLong);
           // if (calculateDistance(newLat,newLong,currentLocation.getLatitude(), currentLocation.getLongitude()) && isBetterLocation(location, currentLocation)) {
                lat = newLat;
                lng = newLong;
                currentLocation = location;
                Toast.makeText(getBaseContext(), "lat and lng" + lat + ", " + lng, Toast.LENGTH_LONG).show();
                boolean val = updatelocation(lng,lat);
                if (!val) {
                    Toast.makeText(getBaseContext(), "lat and lng NOT stored", Toast.LENGTH_LONG).show();
                } else if (val)
                    Toast.makeText(getBaseContext(), "lat and lng stored", Toast.LENGTH_LONG).show();
        //    } else {
                Toast.makeText(getBaseContext(), "Same Location", Toast.LENGTH_LONG).show();
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
            GeoFire geoFire = new GeoFire(myRef);
            if (lat != null) {

                //myRef.child("user").child(userid.getUid()).child("location_lat").setValue(lat);
                if (lng != null) {
                    geoFire.setLocation(userid.getUid(), new GeoLocation(lat,lng));
                    //myRef.child("user").child(userid.getUid()).child("location_lang").setValue(lng);
                }
                return true;
            } else {
                return false;
            }

        }

    /**
     * Determines whether user Location reading is the same as current Location
     *
     *  userLat
     * userLng are new user location
     * venueLat
     *  venueLng are the old user location
     */
/*
    public Boolean calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {
        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);
        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = 6371 * c;

        if (dist < 0.01) {
                /* If it's within 10m, we assume we're not moving */
           /* Toast.makeText(getBaseContext(), "Same region ", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (dist > 0.01){
            Toast.makeText(getBaseContext(), "User moved ", Toast.LENGTH_LONG).show();
            return true;
        }
        return true;
    }


        private static final int FIFTEEN_MINUTES = 1000 * 60 * 15;

        /**
         * Determines whether one Location reading is better than the current Location fix
         *
         * @param location            The new Location that you want to evaluate
         * @param currentBestLocation The current Location fix, to which you want to compare the new one
         */
        /*protected boolean isBetterLocation(Location location, Location currentBestLocation) {
            if (currentBestLocation == null) {
                // A new location is always better than no location
             Toast.makeText(getBaseContext(), "Current Location is null ", Toast.LENGTH_LONG).show();

                return true;
            }

            // Check whether the new location fix is newer or older
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > FIFTEEN_MINUTES;
            boolean isSignificantlyOlder = timeDelta < -FIFTEEN_MINUTES;
            boolean isNewer = timeDelta > 0;

            // If it's been more than 15 minutes since the current location, use the new location
            // because the user has likely moved
            if (isSignificantlyNewer) {
                Toast.makeText(getBaseContext(), "Current Location is 15mins old ", Toast.LENGTH_LONG).show();

                return true;
                // If the new location is more than two minutes older, it must be worse
            } else if (isSignificantlyOlder) {
                Toast.makeText(getBaseContext(), "Current Location is worse", Toast.LENGTH_LONG).show();

                return false;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(location.getProvider(),
                    currentBestLocation.getProvider());

            // Determine location quality using a combination of timeliness and accuracy
            if (isMoreAccurate) {
                Toast.makeText(getBaseContext(), "Current Location isMoreAccurate", Toast.LENGTH_LONG).show();
                return true;
            } else if (isNewer && !isLessAccurate) {
                Toast.makeText(getBaseContext(), "Current Location is NEW and MoreAccurate", Toast.LENGTH_LONG).show();
                return true;
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }
            return false;
        }

        /**
         * Checks whether two providers are the same
         */
       /* private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }*/

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
