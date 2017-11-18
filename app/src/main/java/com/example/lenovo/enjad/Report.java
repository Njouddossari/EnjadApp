package com.example.lenovo.enjad;

/**
 * Created by LENOVO on 18/11/17.
 */

public class Report {

    public static int report_id=0;
    public String severity;
    public String Emerg_type;
    public String Emerg_status;
    public String username;

    public Report( String sev, String type, String status, String name) {
        this.report_id++;
        this.severity = sev;
        Emerg_type = type;
        Emerg_status = status;
        this.username = name;
    }
}
