package com.example.lenovo.enjad.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.enjad.Activities.TestActivity;
import com.example.lenovo.enjad.JavaClasses.HelperSharedPref;
import com.example.lenovo.enjad.Activities.HomPageActivity;


/**
 * Created by ahmed aljoaid on 14/11/2017.
 */

public class PowerButtonListenr extends BroadcastReceiver {
    private static final String TAG = "PowerButtonListenr";
    HelperSharedPref prf;
    private static int countPowerOff = 0;
    private boolean screenOff;


    @Override
    public void onReceive(Context context, Intent intent) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                countPowerOff=0;
                //Do something after 30 seconds

            }
        }, 30000);  //the time is in miliseconds

        prf = new HelperSharedPref(context);
        Log.w(TAG, "onReceive: " + "I am in bad situation");

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            countPowerOff++;
            screenOff = true;
            Log.w(TAG, "onReceive: " + countPowerOff);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            screenOff = false;

        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            Log.e("In on receive", "In Method:  ACTION_USER_PRESENT");

        }
        Intent i = new Intent(context, ScreenOnOffService.class);
        i.putExtra("screen_state", screenOff);
        context.startService(i);
        if (countPowerOff > 2) { // here we can initiate the report
            countPowerOff = 0;
            Toast.makeText(context, "MAIN ACTIVITY IS BEING CALLED ", Toast.LENGTH_LONG).show();
           Intent z = new Intent(context, TestActivity.class);
            z.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
           context.startActivity(z);
        }
    }



}
