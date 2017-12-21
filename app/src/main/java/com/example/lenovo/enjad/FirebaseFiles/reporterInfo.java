package com.example.lenovo.enjad.FirebaseFiles;

/**
 * Created by LENOVO on 24/11/17.
 */

public class reporterInfo {

    private static String user_name="";
    private static String healthInfo="";
    private static String reportType="";
    private static String reportSeverity="";
    private static String lng="" , lat="";

    public reporterInfo() {
    }
    public reporterInfo(String name , String health, String type , String sev , String lngitude , String latitude  ) {

        this.user_name=name;
        this.healthInfo=health;
        this.reportType=type;
        this.reportSeverity=sev;
        this.lng=lngitude;
        this.lat=latitude;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static String getHealthInfo() {
        return healthInfo;
    }

    public static String getReportType() {
        return reportType;
    }

    public static String getReportSeverity() {
        return reportSeverity;
    }

    public static String getLng() {
        return lng;
    }

    public static String getLat() {
        return lat;
    }

    public static void setUser_name(String user_name) {
        reporterInfo.user_name = user_name;
    }

    public static void setHealthInfo(String healthInfo) {
        reporterInfo.healthInfo = healthInfo;
    }

    public static void setReportType(String reportType) {
        reporterInfo.reportType = reportType;
    }

    public static void setReportSeverity(String reportSeverity) {
        reporterInfo.reportSeverity = reportSeverity;
    }

    public static void setLng(String lng) {
        reporterInfo.lng = lng;
    }

    public static void setLat(String lat) {
        reporterInfo.lat = lat;
    }
}
