package com.akp.uniqpay.IncomeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class MatchingIncome extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_income);
        FindId();
        History();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        menuImg=findViewById(R.id.menuImg);
        history_rec = findViewById(R.id.history_rec);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void History(){
        String otp = new GlobalAppApis().DirectIncomeAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getDirectincome(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("DirectIncomeRes") && jsonObject.getJSONArray("DirectIncomeRes").length() > 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("DirectIncomeRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Income", jsonObject1.getString("Income"));
                            hm.put("MemberId", jsonObject1.getString("MemberId"));
                            hm.put("MemberName", jsonObject1.getString("MemberName"));
                            hm.put("Package", jsonObject1.getString("Package"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(MatchingIncome.this,
                                LinearLayoutManager.VERTICAL, false);
                        DirectIncomeAdapter customerListAdapter = new DirectIncomeAdapter(MatchingIncome.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },MatchingIncome.this, call, "", true);
    }


}