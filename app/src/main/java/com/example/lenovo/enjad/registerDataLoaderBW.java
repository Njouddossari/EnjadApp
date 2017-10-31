package com.example.lenovo.enjad;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import com.example.lenovo.enjad.SignupActivity;


/**
 * Created by LENOVO on 29/10/17.
 */

public class registerDataLoaderBW extends AsyncTask <String,Void,String> {

    SignupActivity activity;
   // AlertDialog alertDialog;
   String result;

    registerDataLoaderBW (SignupActivity a )
    {

        activity=a;
    }


    @Override
    protected String doInBackground(String... params) {

    String type= params[0];
    String signup_url= "http://192.168.8.106/Myphpfiles/enjad/register.php";
        if ( type.equals("signup"))
    {
        try {
            String username=params[1];
            String password=params[2];
            String email=params[3];
            String health=params[4];
            String mobile=params[5];
            URL url= new URL (signup_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream= httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                    +URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                    +URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                    +URLEncoder.encode("user_health","UTF-8")+"="+URLEncoder.encode(health,"UTF-8")+"&"
                    +URLEncoder.encode("user_mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
// to read from php file
            InputStream inputStream= httpURLConnection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result="";
            String line="";

            while ( (line=bufferedReader.readLine())!= null )
            {
                result+=line;

            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


        return null;
}


    @Override
    protected void onPreExecute() {
       // alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("register Status");
    }

    @Override
    protected void onPostExecute(String result) {
        this.result=result;
        activity.getDataSign(this.result);
        //alertDialog.setMessage(result);
       // alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
