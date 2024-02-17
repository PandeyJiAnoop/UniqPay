package com.akp.uniqpay.MyAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class MyProfile extends AppCompatActivity {
 ImageView menuImg;
 String UserId;
 EditText sponser_id_et,name_et,mobile_et,email_et,gender_et,btc_address_et,bnb_address_et,eth_address_et,usdt_adress_et,acc_no_et;
 AppCompatButton btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        FindId();
        OnClick();
        ProfileAPI("",UserId);
    }

    private void OnClick() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfileAPI(usdt_adress_et.getText().toString(),acc_no_et.getText().toString(),eth_address_et.getText().toString(),btc_address_et.getText().toString(),
                        email_et.getText().toString(),gender_et.getText().toString(),bnb_address_et.getText().toString(),
                        name_et.getText().toString(),UserId);
            }
        });
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        menuImg=findViewById(R.id.menuImg);
        sponser_id_et=findViewById(R.id.sponser_id_et);
        name_et=findViewById(R.id.name_et);
        mobile_et=findViewById(R.id.mobile_et);
        email_et=findViewById(R.id.email_et);
        gender_et=findViewById(R.id.gender_et);
        btc_address_et=findViewById(R.id.btc_address_et);
        bnb_address_et=findViewById(R.id.bnb_address_et);
        eth_address_et=findViewById(R.id.eth_address_et);
        usdt_adress_et=findViewById(R.id.usdt_adress_et);
        acc_no_et=findViewById(R.id.acc_no_et);
        btn_submit=findViewById(R.id.btn_submit);
    }

    private void UpdateProfileAPI(String aacnname,String accno,String bankbranch,String bankname,String emailid,String gender,String ifsccode,String name,String userid) {
            String otp1 = new GlobalAppApis().UpdateProfile(aacnname,accno,bankbranch,bankname,emailid,gender,ifsccode,name,userid);
            ApiService client1 = getApiClient_ByPost();
            Call<String> call1 = client1.getUpdateProfile(otp1);
            new ConnectToRetrofit(new RetrofitCallBackListenar() {
                @Override
                public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                    Log.d("ressss",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject object = new JSONObject(result);
                        if(object.getString("Status").equalsIgnoreCase("true")) {
                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, MyProfile.this, call1, "", true);
    }

    public void  ProfileAPI(String action,String userid){
        String otp1 = new GlobalAppApis().Profile(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getProfile(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("ressss",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if(object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("LoginRes");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String bnbAddress = jsonobject.getString("BNBAddress");
                            String btcAddress = jsonobject.getString("BTCAddress");
                            String customerId = jsonobject.getString("CustomerId");
                            String ethAddress = jsonobject.getString("ETHAddress");

                            String emailId = jsonobject.getString("EmailId");
                            String gender = jsonobject.getString("Gender");
                            String mobileNo = jsonobject.getString("MobileNo");
                            String name = jsonobject.getString("Name");
                            String sponsorId = jsonobject.getString("SponsorId");
                            String title = jsonobject.getString("Title");
                            String usdtAddress = jsonobject.getString("USDTAddress");
                            String BranchName = jsonobject.getString("BranchName");
                            sponser_id_et.setText(sponsorId);
                            name_et.setText(name);
                            mobile_et.setText(mobileNo);
                            email_et.setText(emailId);

                            if (gender_et.getText().toString().equalsIgnoreCase("")){
                                gender_et.setEnabled(true);
                                gender_et.setClickable(true);
                            }
                            else {
                                gender_et.setEnabled(false);
                                gender_et.setClickable(false);
                            }
                            gender_et.setText(gender);
                            btc_address_et.setText(usdtAddress);
                            bnb_address_et.setText(ethAddress);
                            eth_address_et.setText(BranchName);
                            acc_no_et.setText(bnbAddress);
                            usdt_adress_et.setText(btcAddress);
//                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, MyProfile.this, call1, "", true);
    }

}