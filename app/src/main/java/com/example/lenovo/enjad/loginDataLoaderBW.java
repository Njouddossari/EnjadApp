package com.example.lenovo.enjad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.lenovo.enjad.LoginActivity;

import static com.example.lenovo.enjad.R.string.username;

/**
 * Created by LENOVO on 26/10/17.
 */

public class loginDataLoaderBW extends AsyncTask<String,Void,String> {
    LoginActivity activity;
   // AlertDialog alertDialog;
    String result;

    loginDataLoaderBW(LoginActivity a )
    {

        activity=a;
    }
    @Override
    protected String doInBackground(String... params) {
        String type= params[0];
        String login_url= "http://192.168.8.106/Myphpfiles/enjad/login.php";
        if ( type.equals("login"))
        {
            try {
                String username=params[1];
                String password=params[2];
                URL url= new URL (login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream= httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
        //alertDialog.setTitle("Login Status");
    }
    public String getresult() {
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        this.result=result;
        activity.getDataLogin(this.result);
       //save  the response from DB
        //alertDialog.setMessage(result);
        //alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
