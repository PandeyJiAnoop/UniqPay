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

public class Report_PanGenerationReport extends AppCompatActivity {
    ImageView back_btn;
    RecyclerView rcvList;
    private final ArrayList<HashMap<String, String>> arrFriendsList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private Report_PanGenerationReport.FriendsListAdapter pdfAdapTer;
    String UserId;
    ImageView norecord_tv;
    TextView title_tv;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pan_generation_report);
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
        String otp1 = new GlobalAppApis().PanCardTransactionHistroyAPI(UserId);
        Log.d("API_passbook",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.PanCardTransactionHistroy(otp1);
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
                        JSONArray Response1 = objectlist.getJSONArray("PancardTran");
                        for (int i = 0; i < Response1.length(); i++) {
                            title_tv.setText("Pan Card  Report(" + Response1.length() + ")");
                            JSONObject jsonObject = Response1.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("EmaildId", jsonObject.getString("EmaildId"));
                            hashlist.put("EntryDate", jsonObject.getString("EntryDate"));
                            hashlist.put("Msg", jsonObject.getString("Msg"));
                            hashlist.put("OrderId", jsonObject.getString("OrderId"));
                            hashlist.put("TranStatus", jsonObject.getString("TranStatus"));
                            hashlist.put("firstname", jsonObject.getString("firstname"));
                            hashlist.put("kyctype", jsonObject.getString("kyctype"));
                            hashlist.put("lastname", jsonObject.getString("lastname"));
                            hashlist.put("middlename", jsonObject.getString("middlename"));


                            arrFriendsList.add(hashlist);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        pdfAdapTer = new Report_PanGenerationReport.FriendsListAdapter(getApplicationContext(), arrFriendsList);
                        rcvList.setLayoutManager(layoutManager);
                        rcvList.setAdapter(pdfAdapTer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, Report_PanGenerationReport.this, call1, "", true);
    }



    public class FriendsListAdapter extends RecyclerView.Adapter<Report_PanGenerationReport.FriendsList> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        public FriendsListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrFriendsList) {
            data = arrFriendsList;
        }
        public Report_PanGenerationReport.FriendsList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Report_PanGenerationReport.FriendsList(LayoutInflater.from(parent.getContext()).inflate(R.layout.report_pangenration, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final Report_PanGenerationReport.FriendsList holder, final int position) {
            holder.tv1.setText(arrFriendsList.get(position).get("EmaildId"));
            holder.tv2.setText(arrFriendsList.get(position).get("EntryDate"));
            holder.tv3.setText(arrFriendsList.get(position).get("Msg"));
            holder.tv4.setText(arrFriendsList.get(position).get("OrderId"));
            holder.tv5.setText(arrFriendsList.get(position).get("TranStatus"));
            holder.tv6.setText(arrFriendsList.get(position).get("kyctype"));
            holder.tv.setText(arrFriendsList.get(position).get("firstname")+arrFriendsList.get(position).get("middlename")+arrFriendsList.get(position).get("lastname"));



        }

        public int getItemCount() {
            return data.size();
        }
    }
    public class FriendsList extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2,tv3,tv4,tv5,tv6;
        public FriendsList(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            tv3=itemView.findViewById(R.id.tv3);
            tv4=itemView.findViewById(R.id.tv4);
            tv5=itemView.findViewById(R.id.tv5);
            tv6=itemView.findViewById(R.id.tv6);
        }
    }
}