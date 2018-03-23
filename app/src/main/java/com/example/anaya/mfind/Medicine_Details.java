package com.example.anaya.mfind;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Medicine_Details extends AppCompatActivity {
ArrayList<String> med_details;
String medname = null,district = null,place = null;
ImageView img = null;
String mDrawableName;
TextView title,med_name,brand,pref,desc,price;
Button proceed;
Typeface typeface;
Context context;
HashMap<String,String> idMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine__details);
        img = findViewById(R.id.medicine_image);
        title = findViewById(R.id.title);
        med_name = findViewById(R.id.medicine_name);
        brand = findViewById(R.id.medbrand);
        pref = findViewById(R.id.medpref);
        desc = findViewById(R.id.description);
        price = findViewById(R.id.medprice);
        proceed = findViewById(R.id.ok);
        context = this;


        mDrawableName = getIntent().getExtras().getString("medicine_image");
        idMap = (HashMap<String, String>) getIntent().getExtras().getSerializable("medicine_map");
        Log.d("Drawimg",mDrawableName);
        medname = getIntent().getExtras().getString("medicine_name");
        med_details = getIntent().getExtras().getStringArrayList("medicine_details");
        int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        img.setBackgroundResource(resID);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/font1.ttf");
        med_name.setText("Medicine Name:  "+medname);
        brand.setText(("Brand: "+ med_details.get(0)));
        pref.setText(("Preference: "+ med_details.get(1)));
        desc.setText(("Description: "+ med_details.get(2)));
        price.setText(("Price: "+ med_details.get(3)));
        district = getIntent().getExtras().getString("district");
        place = getIntent().getExtras().getString("place");

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getShops(context,idMap).execute(district,place,medname);
            }
        });
    }
}
