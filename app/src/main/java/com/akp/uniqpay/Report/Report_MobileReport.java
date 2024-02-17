package com.akp.uniqpay.Report;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class Report_MobileReport extends AppCompatActivity {
    ImageView back_btn;
    RecyclerView rcvList;
    private final ArrayList<HashMap<String, String>> arrFriendsList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private FriendsListAdapter pdfAdapTer;
    String UserId;
    ImageView norecord_tv;
    TextView title_tv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_mobile_report);
        FindId();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        title_tv= findViewById(R.id.title_tv);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rcvList = findViewById(R.id.rcvList);
        norecord_tv=findViewById(R.id.norecord_tv);
        GetReport();
    }

    private void GetReport() {
        String otp1 = new GlobalAppApis().RechargeTransactionHistroyAPI(UserId);
        Log.d("API_passbook",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.RechargeTransactionHistroy(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_passbook",result);
                try {
                    JSONObject objectlist = new JSONObject(result);
                    if (objectlist.getString("Status").equalsIgnoreCase("false")){
                        String msg       = objectlist.getString("Message");
                        Toast.makeText(getApplicationContext(), objectlist.getString("Message"), Toast.LENGTH_LONG).show();
                        norecord_tv.setVisibility(View.VISIBLE);
                        rcvList.setVisibility(View.GONE);
                    }
                    else {
                        rcvList.setVisibility(View.VISIBLE);
                        JSONArray Response1 = objectlist.getJSONArray("RechargeTran");
                        for (int i = 0; i < Response1.length(); i++) {
                            title_tv.setText("Recharge Report(" + Response1.length() + ")");
                            JSONObject jsonObject = Response1.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("Amount", jsonObject.getString("Amount"));
                            hashlist.put("Date", jsonObject.getString("Date"));
                            hashlist.put("MemberId", jsonObject.getString("MemberId"));
                            hashlist.put("MemberName", jsonObject.getString("MemberName"));
                            hashlist.put("MobileNo", jsonObject.getString("MobileNo"));
                            hashlist.put("OperatorName", jsonObject.getString("OperatorName"));
                            hashlist.put("Remark", jsonObject.getString("Remark"));
                            hashlist.put("TranStatus", jsonObject.getString("TranStatus"));
                            arrFriendsList.add(hashlist);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        pdfAdapTer = new FriendsListAdapter(getApplicationContext(), arrFriendsList);
                        rcvList.setLayoutManager(layoutManager);
                        rcvList.setAdapter(pdfAdapTer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, Report_MobileReport.this, call1, "", true);
    }



    public class FriendsListAdapter extends RecyclerView.Adapter<FriendsList> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        public FriendsListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrFriendsList) {
            data = arrFriendsList;
        }
        public FriendsList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FriendsList(LayoutInflater.from(parent.getContext()).inflate(R.layout.report_mobile, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final FriendsList holder, final int position) {
            holder.tv.setText(String.valueOf(i+1));
            holder.tv1.setText(arrFriendsList.get(position).get("OperatorName"));
            holder.tv2.setText(arrFriendsList.get(position).get("TranStatus"));
            holder.tv3.setText(arrFriendsList.get(position).get("Amount"));
            holder.tv4.setText(arrFriendsList.get(position).get("Date"));
            holder.tv6.setText(arrFriendsList.get(position).get("Remark"));
            holder.tv7.setText(arrFriendsList.get(position).get("MemberId"));
            holder.tv8.setText(arrFriendsList.get(position).get("MemberName"));
            holder.tv5.setText(arrFriendsList.get(position).get("MobileNo"));
            if (arrFriendsList.get(position).get("TranStatus").equalsIgnoreCase("Failed")){
                holder.tv2.setTextColor(Color.RED);
            }
            else {
                holder.tv2.setTextColor(Color.GREEN);
            }
        }

        public int getItemCount() {
            return data.size();
        }
    }
    public class FriendsList extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
        public FriendsList(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            tv3=itemView.findViewById(R.id.tv3);
            tv4=itemView.findViewById(R.id.tv4);
            tv5=itemView.findViewById(R.id.tv5);
            tv6=itemView.findViewById(R.id.tv6);
            tv7=itemView.findViewById(R.id.tv7);
            tv8=itemView.findViewById(R.id.tv8);
        }
    }
}