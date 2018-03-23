package com.example.anaya.mfind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    TextView welcome, search, district, place, medicinepick;
    Spinner splace, sdistrict;
    AutoCompleteTextView enterMedicine;
    Typeface typeface;
    Context context;
    Button medicine_desc;
    int flag = 0;
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,ArrayList<String>> medicine_Details =new HashMap<>();
    ArrayAdapter<String> districtAdapter,placeAdapter;
    String mDrawableName = "m6";
    HashMap<String,String> imageMap = new HashMap<>();

    RelativeLayout thislayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
     //   int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        welcome = (TextView) findViewById(R.id.welcome);
        search = (TextView) findViewById(R.id.title);
        district = (TextView) findViewById(R.id.district);
        place = (TextView) findViewById(R.id.place);
        medicinepick = (TextView) findViewById(R.id.medname);
        splace = (Spinner) findViewById(R.id.spinplace);
        sdistrict = (Spinner) findViewById(R.id.spindistrict);
        enterMedicine = (AutoCompleteTextView) findViewById(R.id.automedicine);
        medicine_desc = findViewById(R.id.search);
        thislayout = findViewById(R.id.thislayout);


        typeface = Typeface.createFromAsset(getAssets(), "fonts/font2.ttf");

        welcome.setTypeface(typeface);
        search.setTypeface(typeface);
        district.setTypeface(typeface);
        place.setTypeface(typeface);
        medicinepick.setTypeface(typeface);
        enterMedicine.setTypeface(typeface);
        context = this;
        //  districtAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,districts);
        //sdistrict.setAdapter(districtAdapter);
        new anaygetDistrict().execute();
        new getMedicines().execute();

        welcome.setText("Welcome " + getIntent().getExtras().getString("name"));
        medicine_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enterMedicine.getText().toString().equals("") || !hashMap.containsValue(enterMedicine.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Enter a Medicine name"+flag,Toast.LENGTH_SHORT).show();
                else
                {
                    Intent medicine = new Intent(SearchActivity.this,Medicine_Details.class);
                    medicine.putExtra("medicine_details",medicine_Details.get(enterMedicine.getText().toString()));
                    medicine.putExtra("medicine_name",enterMedicine.getText().toString());
                    medicine.putExtra("medicine_image",imageMap.get(enterMedicine.getText().toString()));
                    medicine.putExtra("medicine_map",hashMap);
                    medicine.putExtra("district",sdistrict.getSelectedItem().toString());
                    medicine.putExtra("place",splace.getSelectedItem().toString());
                    startActivity(medicine);

                }
                    //new getShops(getApplicationContext(),hashMap).execute(sdistrict.getSelectedItem().toString(),splace.getSelectedItem().toString(),enterMedicine.getText().toString());
            }
        });


    }

    class anaygetDistrict extends AsyncTask<String, String, String> {
        String URL = new getUrl().setUrl("getdistricts.php"); //GET  change the php file name
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        ArrayList<String> getDis = new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {

            try {

                jArray = jParser.makeHttpRequest(URL, "GET", params);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            int count, i;
            try {
                if (jArray.getJSONArray(0).getString(0).equals("rowstrue")) {
                    count = jArray.getJSONArray(0).length();
                    i = 1;
                    while (i < count) {
                        getDis.add(jArray.getJSONArray(0).getString(i));
                        i++;
                    }
                    districtAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, getDis);
                    sdistrict.setAdapter(districtAdapter);

                    sdistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast.makeText(getApplicationContext(), "Selected " + getDis.get(position), Toast.LENGTH_SHORT).show();

                            String district = getDis.get(position);
//
                            new getPlace().execute(district);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class getPlace extends AsyncTask<String, String, String> {
        String URL = new getUrl().setUrl("getplaces.php"); //GET  change the php file name
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        ArrayList<String> getPlace = new ArrayList<>();



        @Override
        protected String doInBackground(String... strings) {
            params.add(new BasicNameValuePair("district", strings[0]));
            jArray = jParser.makeHttpRequest(URL, "GET", params);

            return null;
        }

        @Override
            protected void onPostExecute(String s) {
                try {
                    if(jArray.getJSONArray(0).getString(0).equals("rowstrue"))
                    {
                        int count = jArray.getJSONArray(0).length();
                        int i = 1;
                        while(i<count)
                        {
                            getPlace.add(jArray.getJSONArray(0).getString(i));
                            i++;
                        }
                        placeAdapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, getPlace);
                        splace.setAdapter(placeAdapter);
                    }
                    else
                        Toast.makeText(context,"No places found",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        }

    class getMedicines extends  AsyncTask<String ,String,String>
    {
        String URL = new getUrl().setUrl("getMedicine.php");  //GET  change the php file name
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;

        ArrayAdapter<String> medicine ;
        ArrayList<String> medicine_details =new ArrayList<>();

        @Override
        protected String doInBackground(String... strings) {
            jArray = jParser.makeHttpRequest(URL, "GET", new ArrayList<NameValuePair>());



            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                ArrayList<String> values = new ArrayList<>();
                imageMap = new HashMap<>();
                if(jArray.getJSONArray(0).getString(0).equals("rowstrue"))
                {
                    int count = (jArray.getJSONArray(0).length()-1)/2;
                    Log.d("Medicine Array",jArray.toString());
                    int j = 1;
                    while(j<count)
                    {
                        String medID = (String) jArray.getJSONArray(0).get(j);
                        String medName = (String) jArray.getJSONArray(0).get(j+1);

                         hashMap.put(medID,medName);
                         String split[] = medID.split("M-");
                        imageMap.put(medName,"m"+split[1]+"");


                        medicine_details.add((String) jArray.getJSONArray(0).get(j+2));
                        medicine_details.add((String) jArray.getJSONArray(0).get(j+3));
                        medicine_details.add((String) jArray.getJSONArray(0).get(j+4));
                        medicine_details.add((String) jArray.getJSONArray(0).get(j+5));
                         medicine_Details.put(medName,medicine_details);
                        medicine_details =new ArrayList<>();

                         values.add(medName);
                        j+=6;
                    }
                    Log.d("imagemap",imageMap.toString());



                   medicine = new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item, values);
                   enterMedicine.setThreshold(1);
                    enterMedicine.setAdapter(medicine);
                }
                else
                    Toast.makeText(context,"No places found",Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
