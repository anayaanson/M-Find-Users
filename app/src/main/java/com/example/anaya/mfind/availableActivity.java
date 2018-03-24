package com.example.anaya.mfind;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class availableActivity extends AppCompatActivity {
    String title,place,medname,price,qty,time,contact;
    TextView txttitle,txtplace,txtmedname,txtprice,txtqty,txttime,txtcontact;
    ImageView txtCall;
    AdRequest adRequest;
    AdView adView;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);
       medname =  getIntent().getExtras().getString("medname");
       qty = getIntent().getExtras().getString("medqty");
       price = getIntent().getExtras().getString("medprice");
       title = getIntent().getExtras().getString("shopname");
       time = getIntent().getExtras().getString("shoptime");
       place = getIntent().getExtras().getString("place_dis");
       contact = getIntent().getExtras().getString("contact");
       typeface = Typeface.createFromAsset(getAssets(),"fonts/font1.ttf");
       
        txttitle = findViewById(R.id.shop_name);
        txtplace = findViewById(R.id.place_dis);
        txtmedname = findViewById(R.id.med_name);
        txtcontact = findViewById(R.id.contact_);
        txtprice = findViewById(R.id.price_);
        txtqty = findViewById(R.id.qty_);
        txttime = findViewById(R.id.time_);
        txtCall = findViewById(R.id.txtCall);
        adView = findViewById(R.id.adView);

        txttitle.setTypeface(typeface);
        txtplace.setTypeface(typeface);
        txtmedname.setTypeface(typeface);
        txtcontact.setTypeface(typeface);
        txtprice.setTypeface(typeface);
        txtqty.setTypeface(typeface);
        txttime.setTypeface(typeface);

        txttitle.setText(title);
        txtplace.setText(place);
        txtmedname.setText(medname);
        txtcontact.setText("Contact: "+contact);
        txtprice.setText(getResources().getString(R.string.rupee)+" "+price+"/-");
        txtqty.setText("Available Qty(s): "+qty);
        txttime.setText("Opened Between "+time);




        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},99);

        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            callPhone(contact);
            }
        });
        
        
       
    }

    void callPhone(String mobile)
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobile));
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {

            startActivity(callIntent);
        }
        catch (Exception e)
        {
            Log.d("ErrorCall",e.getMessage());
            Toast.makeText(getApplicationContext(),"Calling Permission denied",Toast.LENGTH_SHORT).show();
        }

    }
}
