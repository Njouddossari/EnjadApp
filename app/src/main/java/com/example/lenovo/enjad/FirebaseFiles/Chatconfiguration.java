package com.example.lenovo.enjad.FirebaseFiles;



public class Chatconfiguration {
    private String label;
    private String value;
    private int icon;

    public Chatconfiguration(String label, String value, int icon){
        this.label = label;
        this.value = value;
        this.icon = icon;
    }

    public String getLabel(){
        return this.label;
    }

    public String getValue(){
        return this.value;
    }

    public int getIcon(){
        return this.icon;
    }
}
