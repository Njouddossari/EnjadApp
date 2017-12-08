package com.example.lenovo.enjad.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.text.util.Linkify;

import com.example.lenovo.enjad.JavaClasses.reporterInfo;
import com.example.lenovo.enjad.R;

public class reportInfoDailog extends AppCompatActivity {

    TextView usrname, severity, type,location,healthinfo;
    String url_location , lng,lat;
    reporterInfo obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_info_dialog);
        obj=new reporterInfo();
        usrname=(TextView) findViewById(R.id.txtusername);
        severity=(TextView) findViewById(R.id.severity1);
        type=(TextView) findViewById(R.id.emergtype1);
        location=(TextView) findViewById(R.id.Location1);
        healthinfo=(TextView) findViewById(R.id.info1);


        lng=obj.getLng();
        lat=obj.getLat();
        url_location="http://www.google.com/maps/place/"+lat+","+lng;

        usrname.setText(obj.getUser_name());
        severity.setText(obj.getReportSeverity());
        type.setText(obj.getReportType());
        healthinfo.setText(obj.getHealthInfo());
        location.setText( url_location);

        Linkify.addLinks(location,Linkify.WEB_URLS);
    }
}
