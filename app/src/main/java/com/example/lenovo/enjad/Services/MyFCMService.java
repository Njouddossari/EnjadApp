package com.example.lenovo.enjad.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.lenovo.enjad.Activities.HomPageActivity;
import com.example.lenovo.enjad.JavaClasses.reporterInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by LENOVO on 22/11/17.
 */

public class MyFCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String Reportername = remoteMessage.getData().get("username");
        //Double Reporterlng = Double.parseDouble((remoteMessage.getData().get("lng"))); //reporter location
        //Double Reporterlat = Double.parseDouble((remoteMessage.getData().get("lat")));
        Double Reporterlng =1.1; //reporter location
        Double Reporterlat = 22.3;
        String reporterHealth= remoteMessage.getData().get("healthInfo");
        String Severity= remoteMessage.getData().get("severity");
        String Emrg_type= remoteMessage.getData().get("emergType");
// save the reporter info to attach in chat
        reporterInfo reporterinfo= new reporterInfo();
        reporterinfo.user_name=Reportername;
        reporterinfo.lng=Reporterlng;
        reporterinfo.lat=Reporterlat;
        reporterinfo.healthInfo=reporterHealth;
        reporterinfo.reportSeverity=Severity;
        reporterinfo.reportType=Emrg_type;

        sendNotification(title,body);

    }

    public void sendNotification(String title, String messageBody) {

        Intent intent= new Intent ( this, HomPageActivity.class);//here must be the emerg type pop up activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        //notificationBuilder.setSmallIcon(icon);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
