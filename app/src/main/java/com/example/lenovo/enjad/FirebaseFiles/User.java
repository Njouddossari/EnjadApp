package com.example.lenovo.enjad.FirebaseFiles;

/**
 * Created by LENOVO on 05/11/17.
 */

public class User {

 public String username, Email,health_info, phone, password , notificationTokens;
 public Double location_lang , location_lat ;
    public Message message;
    public Status status;


    public User (){}

    public User (String name , String email , String health, String mobile , String pass)
    {
        this.username=name;
        this.Email=email;
        this.notificationTokens="";
        this.health_info=health;
        this.password=pass;
        this.phone=mobile;
        this.location_lang=0.0;
        this.location_lat=0.0;

        status = new Status();
        message = new Message();
        message.idReceiver = "0";
        message.idSender = "0";
        message.text = "";
        message.timestamp = 0;
        status.isOnline = false;
        status.timestamp = 0;

    }

    public Double getLocation_lang() {
        return location_lang;
    }

    public Double getLocation_lat() {
        return location_lat;
    }
}
