package com.example.lenovo.enjad;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by USER on 11/13/2017.
 */

public class UpdateLocation extends Service {
    private UpdateLocation updateLocation;
    private static Timer timer = new Timer();
    private Context ctx;
  /*  @Override
    public void onCreate(Bundle savedInstanceState) { //To setup anything for the service "First thing to work"

      //  updateLocation = LocationServices.getFusedLocationProviderClient(this);//get location
        super.onCreate();
        ctx = this; //assign this to the context
        startService();
    }*/

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 900000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
           // toastHandler.sendEmptyMessage(0);
        }
    }

   /* private final Handler toastHandler = new Handler()
    {
        @Override
        public void handleMessage(Notification.MessagingStyle.Message msg)
        {
            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
        }
    };*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//To write the actual function "Second function to be called"
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {//In case of destroying the service, which is when the timer is canceled -> cause user log out
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {//No need for it here but needs to be override anyway
        return null;
    }


}
