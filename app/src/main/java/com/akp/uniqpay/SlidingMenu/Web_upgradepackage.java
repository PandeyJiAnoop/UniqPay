package com.akp.uniqpay.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.akp.uniqpay.R;
import com.google.android.material.textfield.TextInputEditText;

public class Web_upgradepackage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView menuImg;
    TextView wallet_amount_tv,credit_tv,debit_tv,add;
    String UserId,walletBalance,UserPass;
    AppCompatButton raise_add_tv;
    TextInputEditText amount_et;
    String[] courses = { "Select Package","BASIC-599.00", "CLASSIC-999.00"};
    Spinner spin;
    String SelectedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_upgradepackage);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserPass= sharedPreferences.getString("U_pass", "");
        findId();
        OnClick();
//        WalletAPI("",UserId);
//        DashboardAPI("4","",UserPass,UserId);
    }

    private void OnClick() {
        raise_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount_et.getText().toString().equalsIgnoreCase("")){
                    amount_et.setError("Fields can't be blank!");
                    amount_et.requestFocus();
                }
                else {
//                    PlanPurchaseWebviewlRequestAPI(walletBalance,amount_et.getText().toString(),UserId,SelectedValue);
                } }
        });
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RaiseAddRequest.class);
                startActivity(intent);
            }
        });
    }

    private void findId() {
        amount_et=findViewById(R.id.amount_et);
        menuImg=findViewById(R.id.menuImg);
        wallet_amount_tv=findViewById(R.id.wallet_amount_tv);
        credit_tv=findViewById(R.id.credit_tv);
        debit_tv=findViewById(R.id.debit_tv);
        raise_add_tv=findViewById(R.id.raise_add_tv);
        spin = findViewById(R.id.rupee_sp);
        add=findViewById(R.id.add);



        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);
    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method
    @Override
    public void onItemSelected(AdapterView arg0, View arg1, int position, long id)
    {
        SelectedValue=spin.getSelectedItem().toString();
//        Toast.makeText(getApplicationContext(),spin.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView arg0)
    {
        // Auto-generated method stub
    }
}