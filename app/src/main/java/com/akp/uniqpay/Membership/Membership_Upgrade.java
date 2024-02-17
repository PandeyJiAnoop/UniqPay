package com.akp.uniqpay.Membership;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.AppUrls;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.LoginScreen;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.DasRechargeListAdapter;
import com.akp.uniqpay.DashboardActivity;
import com.akp.uniqpay.R;
import com.akp.uniqpay.SlidingMenu.CustomerRegistrtaion;
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
import java.util.Map;

import retrofit2.Call;

public class Membership_Upgrade extends AppCompatActivity {
    RecyclerView rcvList;
    private final ArrayList<HashMap<String, String>> arrFriendsList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private FriendsListAdapter pdfAdapTer;
    String UserId;
    ImageView norecord_tv;
    int i=0;
    ImageView menuImg;
    AlertDialog alertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_upgrade);
        FindId();
        GetUserListAPI();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        Log.v("addadada", UserId);
        menuImg = findViewById(R.id.menuImg);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            } });
        rcvList = findViewById(R.id.rcvList);
        norecord_tv=findViewById(R.id.norecord_tv);
    }

    public void GetUserListAPI() {
        String otp1 = new GlobalAppApis().UpdgrationPackgeList(UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetUpdgrationPackgeList(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("fsasfaf",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
                        norecord_tv.setVisibility(View.GONE);
                        JSONArray Response = object.getJSONArray("PackRes");
                        for (int i = 0; i < Response.length(); i++) {
//                            title_tv.setText("Order Details(ShopIt)("+Response.length()+")");
                            JSONObject jsonObject = Response.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("Id", jsonObject.getString("Id"));
                            hashlist.put("Package_cost", jsonObject.getString("Package_cost"));
                            hashlist.put("Package_name", jsonObject.getString("Package_name"));
                            hashlist.put("PackageType", jsonObject.getString("PackageType"));

                            hashlist.put("Descriptions", jsonObject.getString("Descriptions"));
                            hashlist.put("IsPay", jsonObject.getString("IsPay"));

                            arrFriendsList.add(hashlist);
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(Membership_Upgrade.this, 1);
                        pdfAdapTer = new FriendsListAdapter(getApplicationContext(), arrFriendsList);
                        rcvList.setLayoutManager(layoutManager);
                        rcvList.setAdapter(pdfAdapTer);
                    } else {
                        norecord_tv.setVisibility(View.VISIBLE);
//                        Toast.makeText(Membership_Upgrade.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Membership_Upgrade.this, call1, "", true);
    }

    public class FriendsListAdapter extends RecyclerView.Adapter<Membership_Upgrade.FriendsList> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        public FriendsListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrFriendsList) {
            data = arrFriendsList;
        }
        public Membership_Upgrade.FriendsList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Membership_Upgrade.FriendsList(LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_upgrade, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final Membership_Upgrade.FriendsList holder, final int position) {
            holder.tv.setText(arrFriendsList.get(position).get("Package_name"));
            holder.tv1.setText("\u20B9 "+arrFriendsList.get(position).get("Package_cost"));

            holder.des_tv.setText(Html.fromHtml(arrFriendsList.get(position).get("Descriptions")),TextView.BufferType.SPANNABLE);

            if (arrFriendsList.get(position).get("PackageType").equalsIgnoreCase("Upgrade")){
                holder.img.setImageResource(R.drawable.m2);
            }else {
                holder.img.setImageResource(R.drawable.m1);
            }
            if (arrFriendsList.get(position).get("IsPay").equalsIgnoreCase("0")){
                holder.buy.setClickable(false);
            }
            else {
                holder.buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AccountUpgradtion(arrFriendsList.get(position).get("Id"));
                    }
                });
            }



        }
        public int getItemCount() {
            return data.size();
        }
    }
    public class FriendsList extends RecyclerView.ViewHolder {
        TextView tv,tv1,des_tv;
        AppCompatButton buy;
        ImageView img;

        public FriendsList(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv1=itemView.findViewById(R.id.tv1);
            img = itemView.findViewById(R.id.img);
            buy = itemView.findViewById(R.id.buy);

            des_tv = itemView.findViewById(R.id.des_tv);
        }
    }

    public void AccountUpgradtion(String Packid) {
        String otp1 = new GlobalAppApis().AccountUpgradtion(UserId,Packid,UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetAccountUpgradtion(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("fsasfaf",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
//                        Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                                               showpopupwindow(jsonObject.getString("Message"));
//                        JSONArray jsonArrayr = jsonObject.getJSONArray("ProviderRes");
//                        for (int i = 0; i < jsonArrayr.length(); i++) {
//                            JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
//                            finish();
//                        }
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Membership_Upgrade.this);
                        builder.setTitle("ERROR!")
                                .setMessage(jsonObject.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                        Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Membership_Upgrade.this, call1, "", true);
    }

    private void showpopupwindow(String Set_msg) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(Membership_Upgrade.this).inflate(R.layout.successfullycreated_popup, viewGroup, false);
        Button ok = (Button) dialogView.findViewById(R.id.btnDialog);
        TextView txt_msg=dialogView.findViewById(R.id.txt_msg);
        TextView id_tv= dialogView.findViewById(R.id.id_tv);
        TextView pass_tv= dialogView.findViewById(R.id.pass_tv);
        TextView msg= dialogView.findViewById(R.id.msg);
        msg.setVisibility(View.GONE);
        id_tv.setText(Set_msg);
//        pass_tv.setText("Payid- "+get_payid);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
    }
}
