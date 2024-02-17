package com.akp.uniqpay.Membership;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.AppUrls;
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

public class MembershipTopupReport extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    ImageView norecord_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_topup_report);
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
        String otp = new GlobalAppApis().TopupReport(UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.GetTopupReport(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("GetTopupReport",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("Status").equals("false")){
                        norecord_img.setVisibility(View.VISIBLE);
                    }
                    else{
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("ContractIncomeRes");
                        Log.d("hh",""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh",""+jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Amount", jsonObject1.getString("Amount"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("MemberId", jsonObject1.getString("MemberId"));
                            hm.put("MemberName", jsonObject1.getString("MemberName"));
                            hm.put("Package", jsonObject1.getString("Package"));



                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(MembershipTopupReport.this, LinearLayoutManager.VERTICAL, false);
                        MembershipTopupReport.DasAdapter customerListAdapter = new MembershipTopupReport.DasAdapter(MembershipTopupReport.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, MembershipTopupReport.this, call, "", true);
    }




    public class DasAdapter extends RecyclerView.Adapter<MembershipTopupReport.DasAdapter.VH> {

        Context context;
        List<HashMap<String,String>> arrayList;

        public DasAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public MembershipTopupReport.DasAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_topupreport, viewGroup, false);
            MembershipTopupReport.DasAdapter.VH viewHolder = new MembershipTopupReport.DasAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MembershipTopupReport.DasAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("MemberId"));
            vh.tv2.setText(arrayList.get(i).get("MemberName"));
            vh.tv3.setText(arrayList.get(i).get("Package"));
            vh.tv4.setText("\u20B9 "+arrayList.get(i).get("Amount"));
            vh.tv5.setText(arrayList.get(i).get("Date"));
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
            }
        }
    }


}