package com.akp.uniqpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.akp.uniqpay.Adapterclass.ActivationAdapter;
import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class ActivateScreen extends AppCompatActivity {
    ImageView menuImg;
    String UserId;
    SwipeRefreshLayout srl_refresh;
    RecyclerView chat_recyclerView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ImageView norecord_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_screen);
        FindId();
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(ActivateScreen.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(ActivateScreen.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                } }});
        TopupDetails("",UserId);
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        srl_refresh = findViewById(R.id.srl_refresh);
        chat_recyclerView = findViewById(R.id.chat_recyclerView);
        menuImg=findViewById(R.id.menuImg);
        norecord_img=findViewById(R.id.norecord_img);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void  TopupDetails(String action,String userid){
        String otp1 = new GlobalAppApis().TopupDetails(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getTopupDetails(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("getTopupDetails",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equals("false")){
                        norecord_img.setVisibility(View.VISIBLE);
                    }
                    else {
                        norecord_img.setVisibility(View.GONE);
                        JSONArray Jarray = object.getJSONArray("Res");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonObject1 = Jarray.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Amount", jsonObject1.getString("Amount"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Id", jsonObject1.getString("Id"));
                            hm.put("Package", jsonObject1.getString("Package"));
//                        hm.put("show_amount", jsonObject1.getString("show_amount"));
//                        hm.put("updated_at", jsonObject1.getString("updated_at"));
                            arrayList.add(hm);
//                        JSONObject getDetails = jsonArrayr.getJSONObject(i);
//                        String experiance = getDetails.getString("AstroAccountId");
//                        String images = getDetails.getString("ChatId");
//                        String language = getDetails.getString("CustomerAccountId");
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ActivateScreen.this, 1);
                        ActivationAdapter walletHistoryAdapter = new ActivationAdapter(ActivateScreen.this, arrayList);
                        chat_recyclerView.setLayoutManager(gridLayoutManager);
                        chat_recyclerView.setAdapter(walletHistoryAdapter);
                    } } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong!\n",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }}
        }, ActivateScreen.this, call1, "", true); }


}