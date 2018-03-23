package com.example.anaya.mfind;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anaya on 17-01-2018.
 */

public class Registration extends AsyncTask<String,String,String>   //This is not an Activity
{
    Context context;
    String result;

    public Registration(Context context) {
        this.context = context;
    } //constructor

    String URL = new getUrl().setUrl("register.php"); //GET  change the php file name
    JSONParserArray jParser = new JSONParserArray();
    JSONArray jArray;
    List<NameValuePair> params = new ArrayList<NameValuePair>();

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0
                ];  //this paramters are recived

        // here
        String emailid = strings[1];
        String password = strings[2];
        String mobile = strings[3];
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", emailid));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("mobile", mobile));
        jArray = jParser.makeHttpRequest(URL, "GET", params);
        try {

            Log.d("Response", jArray.getJSONArray(0).getString(0));
            result = jArray.getJSONArray(0).getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;  //this result is returned


    }

    @Override                     //and received here
    protected void onPostExecute(String s) {
        if (s.equals("User Registration Success")) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            Intent next = new Intent(context,Login.class);
            next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(next);
        }
        else
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}