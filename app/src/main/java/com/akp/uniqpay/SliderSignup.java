package com.akp.uniqpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SliderSignup extends AppCompatActivity {
    ImageView menuImg;
    String UserId;
    AppCompatButton btn_submit;
    EditText sponsor_id,name_et,mobile_et,email_et,pan_et,aadhar_et,pincode_et,country_et,state_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_signup);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        FindId();
        btn_submit=findViewById(R.id.btn_submit);
        menuImg=findViewById(R.id.menuImg);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sponsor_id.getText().toString().equalsIgnoreCase("")){
                    sponsor_id.setError("Field can't be blank!");
                    sponsor_id.requestFocus();
                }
                else if (name_et.getText().toString().equalsIgnoreCase("")){
                    name_et.setError("Field cant be blank!");
                    name_et.requestFocus();
                }
                else if (mobile_et.getText().toString().equalsIgnoreCase("")){
                    mobile_et.setError("Fields can't be blank!");
                    mobile_et.requestFocus();
                }
                else if (email_et.getText().toString().equalsIgnoreCase("")){
                    email_et.setError("Fields can't be blank!");
                    email_et.requestFocus();
                }
                else if (pan_et.getText().toString().equalsIgnoreCase("")){
                    pan_et.setError("Fields can't be blank!");
                    pan_et.requestFocus();
                }
                else if (aadhar_et.getText().toString().equalsIgnoreCase("")){
                    aadhar_et.setError("Fields can't be blank!");
                    aadhar_et.requestFocus();
                }
                else if (pincode_et.getText().toString().equalsIgnoreCase("")){
                    pincode_et.setError("Fields can't be blank!");
                    pincode_et.requestFocus();
                }
                else if (country_et.getText().toString().equalsIgnoreCase("")){
                    country_et.setError("Fields can't be blank!");
                    country_et.requestFocus();
                }
                else if (state_et.getText().toString().equalsIgnoreCase("")){
                    state_et.setError("Fields can't be blank!");
                    state_et.requestFocus();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void FindId() {
        sponsor_id=findViewById(R.id.sponsor_id);
        name_et=findViewById(R.id.name_et);
        mobile_et=findViewById(R.id.mobile_et);
        email_et=findViewById(R.id.email_et);
        pan_et=findViewById(R.id.pan_et);
        aadhar_et=findViewById(R.id.aadhar_et);
        pincode_et=findViewById(R.id.pincode_et);
        country_et=findViewById(R.id.country_et);
        state_et=findViewById(R.id.state_et);

    }
}