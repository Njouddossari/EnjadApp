package com.example.lenovo.enjad.JavaClasses;

/**
 * Created by LENOVO on 18/11/17.
 */

public class Report {

    private static int report_id=0;
    private String severity;
    private String Emerg_type;
    private String Emerg_status;
    public String username;

    public Report( String sev, String type, String status, String name) {
        this.report_id++;
        this.severity = sev;
        this.Emerg_type = type;
        this.Emerg_status = status;
        this.username = name;
    }

    public String getReport_id() {
        return String.valueOf(report_id);
    }

    public String getEmerg_status() {
        return Emerg_status;
    }

    public String getSeverity() {
        return severity;
    }

    public String getEmerg_type() {
        return Emerg_type;
    }
}
