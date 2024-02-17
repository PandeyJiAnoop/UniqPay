package com.akp.uniqpay.Report;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class Report_PersonalLoanReport extends AppCompatActivity {
    ImageView back_btn;
    RecyclerView rcvList;
    private final ArrayList<HashMap<String, String>> arrFriendsList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Report_PersonalLoanReport.FriendsListAdapter pdfAdapTer;
    String UserId;
    ImageView norecord_tv;
    TextView title_tv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_personal_loan_report);
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
        String otp1 = new GlobalAppApis().PersnalLoanTransactionHistroyAPI(UserId);
        Log.d("API_passbook",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.PersnalLoanTransactionHistroy(otp1);
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
                        JSONArray Response1 = objectlist.getJSONArray("PersanalLoanTran");
                        for (int i = 0; i < Response1.length(); i++) {
                            title_tv.setText("Personal Loan Report(" + Response1.length() + ")");
                            JSONObject jsonObject = Response1.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("Address", jsonObject.getString("Address"));
                            hashlist.put("Customer_type", jsonObject.getString("Customer_type"));
                            hashlist.put("EmaildId", jsonObject.getString("EmaildId"));
                            hashlist.put("EntryBy", jsonObject.getString("EntryBy"));
                            hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
                            hashlist.put("Landmark", jsonObject.getString("Landmark"));
                            hashlist.put("Monthly_income", jsonObject.getString("Monthly_income"));
                            hashlist.put("Msg", jsonObject.getString("Msg"));
                            hashlist.put("Name", jsonObject.getString("Name"));
                            hashlist.put("OrderId", jsonObject.getString("OrderId"));
                            hashlist.put("PinCode", jsonObject.getString("PinCode"));
                            hashlist.put("StateName", jsonObject.getString("StateName"));
                            hashlist.put("income_proof", jsonObject.getString("income_proof"));
                            hashlist.put("is_mobile_linked_aadhar", jsonObject.getString("is_mobile_linked_aadhar"));

                            arrFriendsList.add(hashlist);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        pdfAdapTer = new Report_PersonalLoanReport.FriendsListAdapter(getApplicationContext(), arrFriendsList);
                        rcvList.setLayoutManager(layoutManager);
                        rcvList.setAdapter(pdfAdapTer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, Report_PersonalLoanReport.this, call1, "", true);
    }



    public class FriendsListAdapter extends RecyclerView.Adapter<Report_PersonalLoanReport.FriendsList> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        public FriendsListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrFriendsList) {
            data = arrFriendsList;
        }
        public Report_PersonalLoanReport.FriendsList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Report_PersonalLoanReport.FriendsList(LayoutInflater.from(parent.getContext()).inflate(R.layout.report_personalloan, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final Report_PersonalLoanReport.FriendsList holder, final int position) {
            holder.tv1.setText(arrFriendsList.get(position).get("Address"));
            holder.tv2.setText(arrFriendsList.get(position).get("Customer_type"));
            holder.tv3.setText(arrFriendsList.get(position).get("EmaildId"));
            holder.tv4.setText(arrFriendsList.get(position).get("EntryBy"));
            holder.tv5.setText(arrFriendsList.get(position).get("EntryDate"));
            holder.tv6.setText(arrFriendsList.get(position).get("Landmark"));
            holder.tv7.setText(arrFriendsList.get(position).get("Monthly_income"));
            holder.tv8.setText(arrFriendsList.get(position).get("Msg"));
            holder.tv9.setText(arrFriendsList.get(position).get("Name"));
            holder.tv10.setText(arrFriendsList.get(position).get("OrderId"));
            holder.tv11.setText(arrFriendsList.get(position).get("PinCode"));
            holder.tv12.setText(arrFriendsList.get(position).get("StateName"));
            holder.tv13.setText(arrFriendsList.get(position).get("income_proof"));
            holder.tv14.setText(arrFriendsList.get(position).get("is_mobile_linked_aadhar"));


        }

        public int getItemCount() {
            return data.size();
        }
    }
    public class FriendsList extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14;
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
            tv9=itemView.findViewById(R.id.tv9);
            tv10=itemView.findViewById(R.id.tv10);
            tv11=itemView.findViewById(R.id.tv11);
            tv12=itemView.findViewById(R.id.tv12);
            tv13=itemView.findViewById(R.id.tv13);
            tv14=itemView.findViewById(R.id.tv14);
        }
    }
}