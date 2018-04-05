package com.example.anaya.mfind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopListActivity extends AppCompatActivity {
    ArrayList<String> shop_names = new ArrayList<>();
    ArrayList<String> shop_contact = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> item_name = new ArrayList<>();
    ArrayList<String> item_qty = new ArrayList<>();
    ArrayList<String> item_price = new ArrayList<>();
    ArrayList<String> item_des = new ArrayList<>();
    ArrayList<String> item_preference = new ArrayList<>();
    ArrayList<String> item_brand = new ArrayList<>();


    TextView district,place,num_shops,no_shops;
    ListView  listView;
    Typeface t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        num_shops = findViewById(R.id.shops_text);
        place = findViewById(R.id.place);
        district = findViewById(R.id.district);
        listView = findViewById(R.id.listview);
        no_shops = findViewById(R.id.no_shops);
        t1 = Typeface.createFromAsset(getAssets(),"fonts/font1.ttf");
        t2 = Typeface.createFromAsset(getAssets(),"fonts/font2.ttf");
        num_shops.setTypeface(t1);
        no_shops.setTypeface(t2);
        place.setTypeface(t1);
        district.setTypeface(t2);

       // district.setText(getIntent().getExtras().getString("district"));
    //    place.setText(getIntent().getExtras().getString("place"));

        if (getIntent().getExtras().getInt("noshops") == 1) {
            num_shops.setVisibility(View.INVISIBLE);
            no_shops.setVisibility(View.VISIBLE);
            no_shops.setText("You have no Shops for "+getIntent().getExtras().getString("medicine")+" at  "
                    +getIntent().getExtras().getString("place"));
        } else {

            no_shops.setVisibility(View.INVISIBLE);

            shop_names = getIntent().getExtras().getStringArrayList("shop_names");
            shop_contact = getIntent().getExtras().getStringArrayList("shop_contact");
            time = getIntent().getExtras().getStringArrayList("time");
            item_name = getIntent().getExtras().getStringArrayList("item_name");
            item_qty = getIntent().getExtras().getStringArrayList("item_qty");
            item_preference = getIntent().getExtras().getStringArrayList("item_preference");
            item_price = getIntent().getExtras().getStringArrayList("item_price");
            item_brand = getIntent().getExtras().getStringArrayList("item_brand");
            place.setText(getIntent().getExtras().getString("place"));
            district.setText(getIntent().getExtras().getString("district"));

            num_shops.setText(shop_names.size()+" Shops have the Medicine");
            listAdapter adp =new listAdapter(this,shop_names,shop_contact,time,item_name,
                    item_qty,item_price,null,item_preference,item_brand,getIntent().getExtras().getString("district"),
                    getIntent().getExtras().getString("place"));
            listView.setAdapter(adp);

        }
    }

    class listAdapter extends BaseAdapter{
        ArrayList<String> shop_names = new ArrayList<>();
        ArrayList<String> shop_contact = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> item_name = new ArrayList<>();
        ArrayList<String> item_qty = new ArrayList<>();
        ArrayList<String> item_price = new ArrayList<>();
       // ArrayList<String> item_des = new ArrayList<>();
        ArrayList<String> item_preference = new ArrayList<>();
        ArrayList<String> item_brand = new ArrayList<>();
        Context context;
        String district,place;

        public listAdapter(Context context,ArrayList<String> shop_names, ArrayList<String> shop_contact, ArrayList<String> time,
                           ArrayList<String> item_name, ArrayList<String> item_qty,
                           ArrayList<String> item_price, ArrayList<String> item_des,
                           ArrayList<String> item_preference, ArrayList<String> item_brand,String district,String place) {
            this.shop_names = shop_names;
            this.shop_contact = shop_contact;
            this.time = time;
            this.item_name = item_name;
            this.item_qty = item_qty;
            this.item_price = item_price;
          //  this.item_des = item_des;
            this.item_preference = item_preference;
            this.item_brand = item_brand;
            this.context = context;
            this.district = district;
            this.place  = place;
        }

        @Override
        public int getCount() {
            return shop_names.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            if(view == null)
                view = View.inflate(context,R.layout.shop_contents,null);
            
            final TextView shopname = view.findViewById(R.id.shop_name);
            TextView times = view.findViewById(R.id.shop_time);
            final TextView contact = view.findViewById(R.id.shop_contact);
            shopname.setText(shop_names.get(position));
            times.setText("Time "+this.time.get(position));
            contact.setText("Contact  "+this.shop_contact.get(position));
            shopname.setTypeface(t2);
            times.setTypeface(t2);
            contact.setTypeface(t2);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(context,availableActivity.class);
                    next.putExtra("medname",item_name.get(position));
                    next.putExtra("medqty",item_qty.get(position));
                    next.putExtra("medprice",item_price.get(position));
                    next.putExtra("shopname",shop_names.get(position));
                    next.putExtra("shoptime",time.get(position));
                    next.putExtra("place_dis",place+","+district);
                    next.putExtra("contact",shop_contact.get(position));
                    next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(next);


                }
            });

            return view;
        }
    }
}
