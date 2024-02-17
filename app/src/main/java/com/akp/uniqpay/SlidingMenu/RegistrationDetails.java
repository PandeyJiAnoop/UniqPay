package com.akp.uniqpay.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.akp.uniqpay.Basic.LoginScreen;
import com.akp.uniqpay.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrationDetails extends AppCompatActivity {
    String GetName,GetId,GetPass,GetSponor,GetSponsorName;
    TextView tv,tv1,tv2,tv3,tv4,tv5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);
        GetName=getIntent().getStringExtra("associate_name");
        GetId=getIntent().getStringExtra("associate_id");
        GetPass=getIntent().getStringExtra("password");
        GetSponor=getIntent().getStringExtra("sponsor_id");
        GetSponsorName=getIntent().getStringExtra("sponsor_name");
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        FindViewId();
        tv.setText(GetName);
        tv1.setText(GetId);
        tv2.setText(GetPass);
        tv3.setText(GetSponor);
        tv4.setText(GetSponsorName);
        tv5.setText(date);
    }

    private void FindViewId() {
        tv=findViewById(R.id.tv);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);
    }
}