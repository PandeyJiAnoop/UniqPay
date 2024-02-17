package com.akp.uniqpay.Dash;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class Das_CreditCardLead extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AppCompatButton btn_submit;
    TextView userid_tv;
    TextInputEditText IfscCodeet,addresset,emailet,landMarket,mobile_noet,monthly_incomeet,nameet,pinCodeet,stateet;
    String UserId,UserName;

    Spinner spin;

    String[] courses = {"SALARIED", "BUSINESSMAN"};
    String SelectedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_das_credit_card_lead);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        UserName = sharedPreferences.getString("U_name", "");
        FindId();
        OnClick();
        userid_tv.setText(UserId);
    }

    private void OnClick() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameet.getText().toString().equalsIgnoreCase("")){
                    nameet.setError("Fields can't be blank!");
                    nameet.requestFocus();
                }
                else if (mobile_noet.getText().toString().equalsIgnoreCase("")){
                    mobile_noet.setError("Fields can't be blank!");
                    mobile_noet.requestFocus();
                }
                else if (addresset.getText().toString().equalsIgnoreCase("")){
                    addresset.setError("Fields can't be blank!");
                    addresset.requestFocus();
                }
                else if (emailet.getText().toString().equalsIgnoreCase("")){
                    emailet.setError("Fields can't be blank!");
                    emailet.requestFocus();
                }
                else if (landMarket.getText().toString().equalsIgnoreCase("")){
                    landMarket.setError("Fields can't be blank!");
                    landMarket.requestFocus();
                }
                else if (monthly_incomeet.getText().toString().equalsIgnoreCase("")){
                    monthly_incomeet.setError("Fields can't be blank!");
                    monthly_incomeet.requestFocus();
                }
                else if (pinCodeet.getText().toString().equalsIgnoreCase("")){
                    pinCodeet.setError("Fields can't be blank!");
                    pinCodeet.requestFocus();
                }
                else if (stateet.getText().toString().equalsIgnoreCase("")){
                    stateet.setError("Fields can't be blank!");
                    stateet.requestFocus();
                }
                else{
                    LoangenerationSubmitAPI(UserId,addresset.getText().toString(),SelectedValue.toString(),emailet.getText().toString(),
                            landMarket.getText().toString(),mobile_noet.getText().toString(),
                            monthly_incomeet.getText().toString(),nameet.getText().toString(),pinCodeet.getText().toString(),stateet.getText().toString());
                } }});
    }

    private void FindId() {
        userid_tv=findViewById(R.id.userid_tv);
        IfscCodeet=findViewById(R.id.IfscCodeet);
        addresset=findViewById(R.id.addresset);
        emailet=findViewById(R.id.emailet);
        landMarket=findViewById(R.id.landMarket);
        mobile_noet=findViewById(R.id.mobile_noet);
        monthly_incomeet=findViewById(R.id.monthly_incomeet);
        nameet=findViewById(R.id.nameet);
        pinCodeet=findViewById(R.id.pinCodeet);
        stateet=findViewById(R.id.stateet);
        btn_submit=findViewById(R.id.btn_submit);
        spin = findViewById(R.id.rupee_sp);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);

        RelativeLayout header=findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void LoangenerationSubmitAPI(String UserId, String address,String customer_type,
                                        String email,String landMark,String mobile_no,String monthly_income,String name,
                                        String pinCode,String state) {
        String otp1 = new GlobalAppApis().GenerateCreditCardLeadAPI(UserId,address,customer_type,email,landMark,mobile_no,
                monthly_income,name,pinCode,state);
        Log.d("CreditCardLeadAPIaop",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getGenerateCreditCardLead(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("btn_submit",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
//                        JSONArray jsonArrayr = jsonObject.getJSONArray("Response");
//                        for (int i = 0; i < jsonArrayr.length(); i++) {
//                            JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Das_CreditCardLead.this);
                            builder.setTitle(jsonObject.getString("Message"));
                            builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Exit</font>"), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    dialog.dismiss();
                                } });
                            builder.create();
                            builder.show();
//                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Das_CreditCardLead.this);
                        builder.setTitle(Html.fromHtml(jsonObject.getString("Message")));
                        builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Exit</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.dismiss();
                            } });
                        builder.create();
                        builder.show();
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                    } } catch (JSONException e) {
                    e.printStackTrace();
                } } }, Das_CreditCardLead.this, call1, "", true);
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