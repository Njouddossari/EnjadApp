package com.example.lenovo.enjad.JavaClasses;

/**
 * Created by LENOVO on 05/11/17.
 */

public class User {

 public String username, Email,health_info, phone, password , notificationTokens;
 public Double location_lang , location_lat ;


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

    }

    public Double getLocation_lang() {
        return location_lang;
    }

    public Double getLocation_lat() {
        return location_lat;
    }
}
