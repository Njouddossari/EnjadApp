package com.example.lenovo.enjad.JavaClasses;

import android.widget.TextView;

/**
 * Created by LENOVO on 24/11/17.
 */

public class reporterInfo {

    public static String user_name="";
    public static String healthInfo="";
    public static String reportType="";
    public static String reportSeverity="";
    public static Double lng=0.0 , lat=0.0;

    public reporterInfo() {
    }
    public reporterInfo(String name , String health, String type , String sev , Double lngitude , Double latitude  ) {

        this.user_name=name;
        this.healthInfo=health;
        this.reportType=type;
        this.reportSeverity=sev;
        this.lng=lngitude;
        this.lat=latitude;
    }


}
