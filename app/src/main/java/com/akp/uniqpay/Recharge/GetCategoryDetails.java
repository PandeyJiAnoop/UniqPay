package com.akp.uniqpay.Recharge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class GetCategoryDetails extends AppCompatActivity {
    ImageView ivBack;
    RecyclerView cust_recyclerView;
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
    String getServicename,GetmethodType;
    TextView title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_category_details);
        FindId();
    }

    private void FindId() {
        getServicename=getIntent().getStringExtra("service_name");
        GetmethodType=getIntent().getStringExtra("MethodType");
        title_tv=findViewById(R.id.title_tv);
        title_tv.setText(getServicename+" Services");
        ivBack=findViewById(R.id.ivBack);
        cust_recyclerView = findViewById(R.id.cust_recyclerView);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });
        getHistory(getServicename);
    }

    public void  getHistory(String providerid){
        String otp1 = new GlobalAppApis().GetOpratorList_AnshPay(providerid);
        Log.d("ressss",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.get_GetOpratorList_AnshPay(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("ressss",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
//                jsonString = jsonString.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>"," ");
//                jsonString = jsonString.replace("<string xmlns=\"http://tempuri.org/\">"," ");
//                jsonString = jsonString.replace("</string>"," ");
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayr = jsonObject.getJSONArray("ProviderRes");
                    for (int i = 0; i < jsonArrayr.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("OperatorName", jsonObject1.getString("OperatorName"));
                        hm.put("Image", jsonObject1.getString("Image"));
                        hm.put("OperatorId", jsonObject1.getString("OperatorId"));
                        hm.put("ProviderType", jsonObject1.getString("ProviderType"));
                        hm.put("FullOperatorName", jsonObject1.getString("FullOperatorName"));
                        hm.put("MethodType",GetmethodType);
                        arrayList1.add(hm);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(GetCategoryDetails.this, 3);
                    CategoryServiceListAdapter customerListAdapter = new CategoryServiceListAdapter(GetCategoryDetails.this, arrayList1);
                    cust_recyclerView.setLayoutManager(gridLayoutManager);
                    cust_recyclerView.setAdapter(customerListAdapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, GetCategoryDetails.this, call1, "", true);
    }

   /* public void  getHistory(String service,String providerid){
        String otp1 = new GlobalAppApis().Operator(service,providerid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.OperatorService(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
//                jsonString = jsonString.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>"," ");
//                jsonString = jsonString.replace("<string xmlns=\"http://tempuri.org/\">"," ");
//                jsonString = jsonString.replace("</string>"," ");
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayr = jsonObject.getJSONArray("ProviderRes");
                    for (int i = 0; i < jsonArrayr.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("OperatorName", jsonObject1.getString("OperatorName"));
                        hm.put("Image", jsonObject1.getString("Image"));
                        hm.put("OperatorId", jsonObject1.getString("OperatorId"));
                        hm.put("ProviderId", jsonObject1.getString("ProviderId"));
                        hm.put("Code", jsonObject1.getString("Code"));
                        hm.put("method", GetmethodType);
                        arrayList1.add(hm);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(GetCategoryDetails.this, 3);
                    CategoryServiceListAdapter customerListAdapter = new CategoryServiceListAdapter(GetCategoryDetails.this, arrayList1);
                    cust_recyclerView.setLayoutManager(gridLayoutManager);
                    cust_recyclerView.setAdapter(customerListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, GetCategoryDetails.this, call1, "", true);
    }*/

}