package com.akp.uniqpay.Dash;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.DashboardActivity;
import com.akp.uniqpay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class Das_ActiveWallet extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    ImageView norecord_img;
    TextView wallet_bal_tv,total_credit_tv,total_debit_tv;

    double totalCredit = 0.0;
    double totalDebit = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_das_active_wallet);
        FindId();
        Das();
        History();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        norecord_img=findViewById(R.id.norecord_img);
        menuImg = findViewById(R.id.menuImg);
        history_rec = findViewById(R.id.history_rec);
        wallet_bal_tv = findViewById(R.id.wallet_bal_tv);
        total_credit_tv = findViewById(R.id.total_credit_tv);
        total_debit_tv = findViewById(R.id.total_debit_tv);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void History() {
        String otp = new GlobalAppApis().DasActiveIncome(UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getWalletTransHistory(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("getWalletTransHistory",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    total_credit_tv.setText("\u20B9 " + jsonObject.getString("TotalCredit"));
                    total_debit_tv.setText("\u20B9 " + jsonObject.getString("TotalDebit"));
                    if(jsonObject.getString("Status").equals("false")){
                        norecord_img.setVisibility(View.VISIBLE);
                    }
                    else{
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("Res");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.d("hh",""+jsonObject1);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("Credit", jsonObject1.getString("Credit"));
                        hm.put("Debit", jsonObject1.getString("Debit"));
                        hm.put("Date", jsonObject1.getString("Date"));
                        hm.put("Remark", jsonObject1.getString("Remark"));
                        hm.put("Type", jsonObject1.getString("Type"));

                        // Parse credit and debit values from the HashMap
                        double credit = Double.parseDouble(hm.get("Credit"));
                        double debit = Double.parseDouble(hm.get("Debit"));
                        // Accumulate the values
                        totalCredit += credit;
                        totalDebit += debit;
                        String totalCreditString = String.valueOf(totalCredit);
                        String totalDebitString = String.valueOf(totalDebit);

                        Log.d("TotalCredit", totalCreditString);
                        Log.d("TotalDebit", totalDebitString);
                        arrayList2.add(hm);
                    }
                    LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(Das_ActiveWallet.this, LinearLayoutManager.VERTICAL, false);
                    Das_ActiveWallet.DasAdapter customerListAdapter = new Das_ActiveWallet.DasAdapter(Das_ActiveWallet.this, arrayList2);
                    history_rec.setLayoutManager(HorizontalLayout1);
                    history_rec.setAdapter(customerListAdapter);
                    } } catch (JSONException e) {
                    e.printStackTrace();
                } }}, Das_ActiveWallet.this, call, "", true);
    }


    public class DasAdapter extends RecyclerView.Adapter<Das_ActiveWallet.DasAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;

        public DasAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public Das_ActiveWallet.DasAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_activewallet, viewGroup, false);
            Das_ActiveWallet.DasAdapter.VH viewHolder = new Das_ActiveWallet.DasAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull Das_ActiveWallet.DasAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("Type"));
            if (arrayList.get(i).get("Type").equalsIgnoreCase("Debit")){
                vh.tv1.setBackgroundColor(Color.RED);
            }
            else {
                vh.tv1.setBackgroundColor(Color.GREEN);
            }
            vh.tv2.setText(arrayList.get(i).get("Date"));
            vh.tv3.setText(arrayList.get(i).get("Remark"));
            vh.tv4.setText(arrayList.get(i).get("Credit"));
            vh.tv5.setText(arrayList.get(i).get("Debit"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
                tv5 = itemView.findViewById(R.id.tv5);
            }}
    }
    public void Das() {
        String otp1 = new GlobalAppApis().Dashboard("4", "", "", UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getDashboard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                Log.d("abcresss", "des" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        wallet_bal_tv.setText("\u20B9 " + jsonobject.getString("ActiveWallet"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and Password Not Matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } } }, Das_ActiveWallet.this, call1, "", true);
    }

}