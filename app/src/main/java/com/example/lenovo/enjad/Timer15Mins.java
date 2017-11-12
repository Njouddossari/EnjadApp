package com.example.lenovo.enjad;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import static android.app.PendingIntent.getActivity;


/**
 * Created by USER on 11/12/2017.
 */



//Class to initiate an Alarm to call service every 15 minutes

public class Timer15Mins extends TimerTask {

    private Handler _Handler;

    public Timer15Mins(Handler handler) {
        _Handler = handler;
    }
    @Override
    public void run() {
        //call update service
       // _Handler.sendEmptyMessage(0);
       /* Toast toast = Toast.makeText(getApplicationContext(), "Here is alaram manager calling update location service", Toast.LENGTH_LONG).show();*/
    }


  /*  private Handler _taskHandler = new Handler(){
        public void dispatchMessage(android.os.Message msg) {
            // do cleanup, close db cursors, file handler, etc.
            // start your target activity
            Intent viewTargetActivity = new Intent(Timer15Mins.this, HomPageActivity.class);

        };
    };*/
}
