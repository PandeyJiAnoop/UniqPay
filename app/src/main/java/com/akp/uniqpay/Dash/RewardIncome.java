package com.akp.uniqpay.Dash;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import retrofit2.Call;

public class RewardIncome extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    ImageView norecord_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_income);
        FindId();
        History();
    }
    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        norecord_img=findViewById(R.id.norecord_img);
        menuImg = findViewById(R.id.menuImg);
        history_rec = findViewById(R.id.history_rec);
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
        Call<String> call = client.getRewardIncomeHistory(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resss", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equals("false")) {
                        norecord_img.setVisibility(View.VISIBLE);
                    } else {
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("WalletRes");
                        Log.d("hh",""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh",""+jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Credit", jsonObject1.getString("Credit"));
                            hm.put("CurrentBalance", jsonObject1.getString("CurrentBalance"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Deductions", jsonObject1.getString("Deductions"));
                            hm.put("Description", jsonObject1.getString("Description"));
                            hm.put("Withdrawal", jsonObject1.getString("Withdrawal"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(RewardIncome.this, LinearLayoutManager.VERTICAL, false);
                        RewardIncome.DasAdapter customerListAdapter = new RewardIncome.DasAdapter(RewardIncome.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, RewardIncome.this, call, "", true);
    }
    public class DasAdapter extends RecyclerView.Adapter<RewardIncome.DasAdapter.VH> {
        Context context;
        List<HashMap<String, String>> arrayList;
        public DasAdapter(Context context, List<HashMap<String, String>> arrayList) {
            this.arrayList = arrayList;
            this.context = context;
        }
        @NonNull
        @Override
        public RewardIncome.DasAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_reward, viewGroup, false);
            RewardIncome.DasAdapter.VH viewHolder = new RewardIncome.DasAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull RewardIncome.DasAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText("\u20B9 "+arrayList.get(i).get("Credit"));
            vh.tv2.setText("\u20B9 "+arrayList.get(i).get("CurrentBalance"));
            vh.tv3.setText("\u20B9 "+arrayList.get(i).get("Deductions"));
            vh.tv4.setText("\u20B9 "+arrayList.get(i).get("Withdrawal"));
            vh.tv5.setText(arrayList.get(i).get("Date"));
            vh.tv6.setText(arrayList.get(i).get("Description"));
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5,tv6;
            public VH(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
                tv5 = itemView.findViewById(R.id.tv5);
                tv6 = itemView.findViewById(R.id.tv6);

            }
        }
    }


}