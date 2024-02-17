package com.akp.uniqpay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akp.uniqpay.Basic.AppUrls;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationList extends AppCompatActivity {
    RecyclerView cust_recyclerView;
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();
    String UserId;
    SwipeRefreshLayout srl_refresh;
    ImageView sliding_menu;
    TextView norecord_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        FindId();
        OnClick();
        getHistory();
    }

    private void OnClick() {
        sliding_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(NotificationList.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(NotificationList.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }}});
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        srl_refresh=findViewById(R.id.srl_refresh);
        norecord_tv=findViewById(R.id.norecord_tv);
        sliding_menu=findViewById(R.id.back_img);
        cust_recyclerView=findViewById(R.id.cust_recyclerView);
    }

    public void getHistory() {
        final ProgressDialog progressDialog = new ProgressDialog(NotificationList.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrls.baseUrl+"NewsList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("NewsList",response);
                progressDialog.dismiss();
                String jsonString = response;
//                jsonString = jsonString.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>"," ");
//                jsonString = jsonString.replace("<string xmlns=\"http://examcoach.in/\">"," ");
//                jsonString = jsonString.replace("</string>"," ");
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                        norecord_tv.setVisibility(View.GONE);
                        JSONArray jsonArrayr = jsonObject.getJSONArray("NewsRes");
                        for (int i = 0; i < jsonArrayr.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("CreatedDate", jsonObject1.getString("CreatedDate"));
                            hm.put("NewsText", jsonObject1.getString("NewsText"));
                            hm.put("NewsTitle", jsonObject1.getString("NewsTitle"));
                            arrayList1.add(hm);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(NotificationList .this, 1);
                        NotificationListAdapter customerListAdapter = new NotificationListAdapter(NotificationList.this, arrayList1);
                        cust_recyclerView.setLayoutManager(gridLayoutManager);
                        cust_recyclerView.setAdapter(customerListAdapter);
                    } else {
                        norecord_tv.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("myTag", "message:"+error);
//                Toast.makeText(NotificationList.this, "Something went wrong!\n"+error, Toast.LENGTH_SHORT).show();
            } });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(NotificationList.this);
        requestQueue.add(stringRequest);
    }


    public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public NotificationListAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }
        @NonNull
        @Override
        public NotificationListAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_notification, viewGroup, false);
            NotificationListAdapter.VH viewHolder = new NotificationListAdapter.VH(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(@NonNull NotificationListAdapter.VH vh, int i) {
            vh.msg_tv.setText(arrayList.get(i).get("NewsTitle"));
//            vh.des_tv.setText(arrayList.get(i).get("NewsText"));
            vh.time_tv.setText(arrayList.get(i).get("CreatedDate"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                vh.des_tv.setText(Html.fromHtml(arrayList.get(i).get("NewsText"), Html.FROM_HTML_MODE_COMPACT));
            } else {
                vh.des_tv.setText(Html.fromHtml(arrayList.get(i).get("NewsText")));
            }
        }
        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView msg_tv, des_tv,time_tv;
            public VH(@NonNull View itemView) {
                super(itemView);
                msg_tv = itemView.findViewById(R.id.msg_tv);
                des_tv = itemView.findViewById(R.id.des_tv);
                time_tv = itemView.findViewById(R.id.time_tv);
            }}}

}