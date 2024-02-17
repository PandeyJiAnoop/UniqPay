package com.akp.uniqpay.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;
import com.akp.uniqpay.Walet.WalletHistoy;
import com.akp.uniqpay.Walet.Withdrawal_History_Web;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class Withdraw extends AppCompatActivity {

    TextView history_tv;
    ImageView menuImg;
    String UserId;
    EditText req_wallet_tv;
    AppCompatButton req_submit_btn;
    TextView member_id_tv,name_tv,mobile_tv,transfer_wallet_tv,date_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
      Findid();
        History();

    }

    private void Findid() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        history_tv=findViewById(R.id.history_tv);
        menuImg=findViewById(R.id.menuImg);
        member_id_tv=findViewById(R.id.member_id_tv);
        name_tv=findViewById(R.id.name_tv);
        mobile_tv=findViewById(R.id.mobile_tv);
        transfer_wallet_tv=findViewById(R.id.transfer_wallet_tv);
        date_tv=findViewById(R.id.date_tv);
        req_wallet_tv=findViewById(R.id.req_wallet_tv);

        req_submit_btn=findViewById(R.id.req_submit_btn);

        history_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Withdrawal_History_Web.class);
                startActivity(intent);
            }
        });
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        req_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Withdrawal Not Supported!",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void History(){
        String otp = new GlobalAppApis().ShowWithdrwarAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getwithdrwar(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.v("ddadada",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("OpenWithdrawalResp") && jsonObject.getJSONArray("OpenWithdrawalResp").length() > 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("OpenWithdrawalResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            member_id_tv.setText(jsonObject1.getString("MemberId"));
                            name_tv.setText(jsonObject1.getString("MemberName"));
                            mobile_tv.setText(jsonObject1.getString("Mobile"));
                            transfer_wallet_tv.setText(jsonObject1.getString("Wallet"));
                            date_tv.setText(jsonObject1.getString("Date"));
                        } }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        },Withdraw.this, call, "", true);
    }



}