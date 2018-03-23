package com.example.anaya.mfind;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;


import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    TextView login;
    TextView newuser;
    EditText email,password;
    Button Login;
    Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (TextView)findViewById(R.id.login);
        newuser= (TextView)findViewById(R.id.newuser);
        email=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        Login= (Button)findViewById(R.id.signin);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/font2.ttf");

        login.setTypeface(typeface);
        newuser.setTypeface(typeface);
        email.setTypeface(typeface);
        password.setTypeface(typeface);
        Login.setTypeface(typeface);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(email.getText().toString().equals(""))
              {
                  Toast.makeText(getApplicationContext(),"Enter an Email-ID",Toast.LENGTH_SHORT).show();
              }
              else if(password.getText().toString().equals(""))
              {
                  Toast.makeText(getApplicationContext(),"Enter the Password",Toast.LENGTH_SHORT).show();
              }
              else
                 new fetchOperation().execute(email.getText().toString(),password.getText().toString());

            }
        });
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registration = new Intent(Login.this,UserRegistration.class);
                startActivity(registration);
            }
        });
    }

    class fetchOperation extends AsyncTask<String,String,String>
    {

       String result;
        String URL = new getUrl().setUrl("login.php");  //GET
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        @Override
        protected String doInBackground(String... values) {
                   // values[0]=email;
            //values[1]=password;
            String username = values[0];
            String password = values[1];
            params.add(new BasicNameValuePair("userid",username));
            params.add(new BasicNameValuePair("password",password));

             jArray = jParser.makeHttpRequest(URL, "GET", params);
            try {
                int count=jArray.getJSONArray(0).length();
                Log.d("Response",jArray.getJSONArray(0).getString(0));
               result = jArray.getJSONArray(0).getString(0);
            } catch (Exception e) {
                   e.printStackTrace();
            }
            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("No Record"))
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            else if (result.equals("Error Response"))
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
         else
            {
                Intent search = new Intent(Login.this,SearchActivity.class);
                search.putExtra("name",result);
                startActivity(search);
            }
        }
    }

}
