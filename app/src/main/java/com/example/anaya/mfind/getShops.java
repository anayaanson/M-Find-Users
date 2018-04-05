package com.example.anaya.mfind;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anaya on 12-03-2018.
 */

public class getShops extends AsyncTask<String,String,String> {
    Context context;
    String URL = new getUrl().setUrl("getShops.php"); //GET  change the php file name
    JSONParserArray jParser = new JSONParserArray();
    JSONArray jArray;
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    String district,place,medicine;
    HashMap<String,String> medMap;

    ArrayList<String> shop_names = new ArrayList<>();
    ArrayList<String> shop_contact = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> item_name = new ArrayList<>();
    ArrayList<String> item_qty = new ArrayList<>();
    ArrayList<String> item_price = new ArrayList<>();
    ArrayList<String> item_des = new ArrayList<>();
    ArrayList<String> item_preference = new ArrayList<>();
    ArrayList<String> item_brand = new ArrayList<>();
    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
    public getShops(Context context, HashMap<String,String> medMap) {
    this.context = context;
    this.medMap = medMap;
    }



    @Override
    protected String doInBackground(String... strings) {
        try {
            String medid = null;
            for (Map.Entry<String, String> entry : medMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if(entry.getValue().equals(strings[2]))
                {
                    medid = key;
                    break;
                }
                // ...
            }

            district = strings[0];
            place = strings[1];
            medicine = strings[2];
            params.add(new BasicNameValuePair("district", strings[0]));
            params.add(new BasicNameValuePair("place", strings[1]));
            params.add(new BasicNameValuePair("medicineid", medid));
            jArray = jParser.makeHttpRequest(URL, "GET", params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            Intent intent = new Intent(context,ShopListActivity.class);
            Log.d("jsonarrayshoplist",jArray.toString());
            if(jArray.getJSONArray(1).length()> 0)
            {
                int start = 0 , end = jArray.getJSONArray(1).length(),sub = 0;
                        {
                    while (start < end) {
                        if(!jArray.getJSONArray(0).get(start).toString().equals("noval")) {
                            shop_names.add(jArray.getJSONArray(0).get(start).toString());
                            shop_contact.add(jArray.getJSONArray(1).get(start).toString());
                            String t = _12HourSDF.format(_24HourSDF.parse(jArray.getJSONArray(2).get(start).toString())) + " - " +
                                    _12HourSDF.format(_24HourSDF.parse(jArray.getJSONArray(3).get(start).toString()));
                            time.add(t);

                            item_name.add(jArray.getJSONArray(4).get(sub).toString());
                            item_brand.add(jArray.getJSONArray(4).get(sub + 1).toString());
                            item_preference.add(jArray.getJSONArray(4).get(sub + 2).toString());
                            item_qty.add(jArray.getJSONArray(4).get(sub + 3).toString());
                            item_price.add(jArray.getJSONArray(4).get(sub + 4).toString());
                            sub += 5;

                        }

                        else
                            sub += 5;
                        start++;
                    }
                    if(shop_names.isEmpty())
                    intent.putExtra("noshops", 1);
                    else
                        intent.putExtra("noshops", 0);
                    intent.putExtra("shop_names", shop_names);
                    intent.putExtra("shop_contact", shop_contact);
                    intent.putExtra("time", time);
                    intent.putExtra("item_name", item_name);
                    intent.putExtra("item_brand", item_brand);
                    intent.putExtra("item_qty", item_qty);
                    intent.putExtra("item_price", item_price);
                }

            }
            else
                    intent.putExtra("noshops",1);


            intent.putExtra("district",district);
            intent.putExtra("place",place);
            intent.putExtra("medicine",medicine);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
