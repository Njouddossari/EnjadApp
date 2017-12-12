package com.example.lenovo.enjad.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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
        usrname=(TextView) findViewById(R.id.usser_name);
        severity=(TextView) findViewById(R.id.severity);
        type=(TextView) findViewById(R.id.emergtype);
        location=(TextView) findViewById(R.id.Location);
        healthinfo=(TextView) findViewById(R.id.info);
        //Button leave = (Button) findViewById(R.id.button);


        lng=obj.getLng();
        lat=obj.getLat();
        url_location="http://www.google.com/maps/place/"+lat+","+lng;

       // usrname.setText("@string/username1" + obj.getUser_name());
       // severity.setText("@string/severity"+ obj.getReportSeverity());
      //  type.setText("@string/emergytype" + obj.getReportType());
       // healthinfo.setText("@string/info" + obj.getHealthInfo());
      //  location.setText( url_location);

        Linkify.addLinks(location,Linkify.WEB_URLS);
    }
}
