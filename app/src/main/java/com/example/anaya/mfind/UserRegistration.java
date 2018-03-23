package com.example.anaya.mfind;

import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserRegistration extends AppCompatActivity {
    TextView Registration;
    TextView CheckAvailability,availreg;
    EditText Name,Mobilenumber,password,Emailid;
    Button Register;
    int flag=0;

    Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z]+.com");
    Pattern pt = Pattern.compile("[A-Za-z]*");
    Matcher math = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        Registration = (TextView)findViewById(R.id.txtuserreg);
        CheckAvailability= (TextView)findViewById(R.id.avail);
        Emailid=(EditText)findViewById(R.id.emailid);
        password=(EditText)findViewById(R.id.password);
        Mobilenumber=(EditText)findViewById(R.id.mobileno);
        Name=(EditText)findViewById(R.id.username);
        Register= (Button)findViewById(R.id.login);
        availreg = (TextView)findViewById(R.id.availreg);

        CheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Emailid.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter  an Email",Toast.LENGTH_SHORT).show();
                }
                 else
                {
                    math = pattern.matcher(Emailid.getText().toString());
                    if(math.matches())
                    {

                        new checkUser().execute(Emailid.getText().toString());
                    }
                   else Toast.makeText(getApplicationContext(),"Not Email Format",Toast.LENGTH_SHORT).show();

                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1) {
                    String email = Emailid.getText().toString();
                    String pass = password.getText().toString();
                    String mobile = Mobilenumber.getText().toString();
                    String name = Name.getText().toString();

                    if (email.equals("") || pass.equals("") || mobile.equals("") || name.equals("")) {
                        Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else if (mobile.length() > 10 || mobile.length() < 10) {
                        Toast.makeText(getApplicationContext(), "Enter a valid mobile number", Toast.LENGTH_SHORT).show();

                    } else if (pass.length() < 5) {
                        Toast.makeText(getApplicationContext(), "Password should be minimum length 5", Toast.LENGTH_SHORT).show();

                    } else {
                        math = pt.matcher(name);
                        if (!math.matches())
                            Toast.makeText(getApplicationContext(), "Name Should only contain alphabets", Toast.LENGTH_SHORT).show();
                        else
                            new Registration(getApplicationContext()).execute(name,email,pass,mobile);
                        // see this is  function it have parameters...from here we pass values frome here


                    }
                }
                   else
                    {
                        Toast.makeText(getApplicationContext(), "Provide an Email to continue", Toast.LENGTH_SHORT).show();

                    }
                    //  else
                     //   new checkUser().execute(Emailid.getText().toString());



            }
        });



    }

    class checkUser extends AsyncTask<String,String,String>{

        String result;
        String URL = new getUrl().setUrl("checkavail.php");
        JSONParserArray jParser = new JSONParserArray();
        JSONArray jArray;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        @Override
        protected String doInBackground(String... values) {
            // values[0]=email;
            //values[1]=password;
            String emailid = values[0];

            params.add(new BasicNameValuePair("emailid",emailid));


            jArray = jParser.makeHttpRequest(URL, "GET", params);
            try {

                Log.d("Response",jArray.getJSONArray(0).getString(0));
                result = jArray.getJSONArray(0).getString(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }
        @Override
        protected void onPostExecute(String result) {
            if(result.equals("true")) {
                Toast.makeText(getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
            flag = 0;
            availreg.setText("Already Registered");
            availreg.setTextColor(Color.RED);
            }else{
                Toast.makeText(getApplicationContext(),"User-ID Available",Toast.LENGTH_SHORT).show();
                flag = 1;
                availreg.setText("User-ID Available for Registration");
                availreg.setTextColor(Color.GREEN);
            }


        }
    }
}
