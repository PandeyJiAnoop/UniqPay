package com.akp.uniqpay.Recharge;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;

public class DTHRecharge extends AppCompatActivity {
    ImageView ivBack;
    LinearLayout provider_ll;
    TextView provoider_et,plan_dis_tv;
    String service_name,UserId,getcode;
    String getProvider_id,roletype;
    AppCompatButton btnSubmit;
    private SharedPreferences sharedPreferences;
    EditText mob_et,amount_et;
    AlertDialog alertDialog;
    String get_operator_ref,get_payid;
    TextView title_tv,numb,view_plan_tv;
    SliderLayout sliderLayout;

    private Dialog alertDialog1;
    private final ArrayList<HashMap<String, String>> arrLegalList = new ArrayList<>();
    private LegalListAdapter pdfAdapTer;
    RecyclerView plan_rv;
    Spinner circle_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthrecharge);
        FindId();
        OnClick();
    }

    private void OnClick() {
        view_plan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mob_et.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter Number",Toast.LENGTH_LONG).show();
                }
                else if (circle_sp.getSelectedItem().toString().equalsIgnoreCase("Select Circle")) {
                    Toast.makeText(getApplicationContext(),"Select Circle First",Toast.LENGTH_LONG).show();
                }
                else {
                    GetAppPlanAPI(circle_sp.getSelectedItem().toString(),mob_et.getText().toString(),service_name);
                }}});

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mob_et.getText().toString().equalsIgnoreCase("")){
                    mob_et.setError("Enter Mobile Number");
                    mob_et.requestFocus();
                }
                else if (circle_sp.getSelectedItem().toString().equalsIgnoreCase("Select Circle")) {
                    Toast.makeText(getApplicationContext(),"Select Circle First",Toast.LENGTH_LONG).show();
                }
                else if (amount_et.getText().toString().equalsIgnoreCase("")){
                    amount_et.setError("Enter Plan Amount");
                    amount_et.requestFocus();
                }
                else {
                    String amount=  amount_et.getText().toString().replaceAll("\\.\\d+$", "");
                    btnSubmit.setEnabled(false);
                    Payment(amount, service_name, mob_et.getText().toString(), UserId, getProvider_id);
                    //      do your work here
                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnSubmit.setEnabled(true);
                                }
                            });
                        }
                    }, 7000); // delay button enable for 0.7 sec
                } }});
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void FindId() {
        title_tv=findViewById(R.id.title_tv);
        sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        service_name=getIntent().getStringExtra("servicename");
        getProvider_id=getIntent().getStringExtra("provider_id");
        getcode=getIntent().getStringExtra("MethodType");
        Log.d("getProvider_id",getProvider_id);
//        Toast.makeText(getApplicationContext(), getonlyservice,Toast.LENGTH_LONG).show();
        numb=findViewById(R.id.numb);
        plan_rv=findViewById(R.id.plan_rv);
        btnSubmit=findViewById(R.id.btnSubmit);
        ivBack=findViewById(R.id.ivBack);
        provider_ll=findViewById(R.id.provider_ll);
        provoider_et=findViewById(R.id.provoider_et);
        mob_et=findViewById(R.id.mob_et);
        amount_et=findViewById(R.id.amount_et);
        title_tv.setText(service_name);
        plan_dis_tv=findViewById(R.id.plan_dis_tv);
        view_plan_tv=findViewById(R.id.view_plan_tv);
        circle_sp=findViewById(R.id.circle_sp);
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        setSliderViews();

        if(service_name ==null){
        }
        else {
            provoider_et.setText(service_name);
        }
    }

    private void showpopupwindow() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(DTHRecharge.this).inflate(R.layout.successfullycreated_popup, viewGroup, false);
        Button ok = (Button) dialogView.findViewById(R.id.btnDialog);
        TextView txt_msg = dialogView.findViewById(R.id.txt_msg);
        TextView id_tv = dialogView.findViewById(R.id.id_tv);
        TextView pass_tv = dialogView.findViewById(R.id.pass_tv);
        id_tv.setText("Operator_ref- " + get_operator_ref + " (" + UserId + ")");
//        pass_tv.setText("Payid- "+get_payid);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DTHRecharge.this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void Payment(String Amount, String Circle, String MobileNo, String UserId, String optr) {
        String otp1 = new GlobalAppApis().PostRechargeAPI(Amount, Circle, MobileNo, UserId, optr);
        Log.d("makepayment_req",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.MakeRecharge_AnshPay(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("makepayment_res",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        get_operator_ref = jsonObject.getString("Message");
//                        get_payid=jsonObject.getString("payid");
                        showpopupwindow();
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } }, DTHRecharge.this, call1, "", true);
    }

    private void setSliderViews() {
        for (int i = 0; i <= 5; i++) {
            SliderView sliderView = new SliderView(DTHRecharge.this);
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.ban_1);
                    sliderView.setDescription("Welcome To\n" +
                            "UniqPay Company");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.ban_2);
//                    sliderView.setDescription("सच होगा सपना");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.ban_3);
                    ;
//                    sliderView.setDescription("सोचो  एक  नयी  दुनिया ");
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.banner);
                    ;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.five);
                    ;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 5:
                    sliderView.setImageDrawable(R.drawable.three);
                    ;
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(DTHRecharge.this, "Welcome To UniqPay " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    private void GetAppPlanAPI(String method, String mob, String operator) {
        String otp1 = new GlobalAppApis().PlanAPI(method,mob,operator);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetAllPlan(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("plarws",result);
                try {
                    JSONObject objectlist = new JSONObject(result);
                    if (objectlist.getString("status").equalsIgnoreCase("1")){
                        JSONObject Responobj= objectlist.getJSONObject("records");
//                    JSONObject Respon= Responobj.getJSONObject("data");
                        JSONArray Response1 = Responobj.getJSONArray("COMBO");
                        for (int i = 0; i < Response1.length(); i++) {
//                            title_tv.setText("Passbook History("+Response1.length()+")");
                            JSONObject jsonObject = Response1.getJSONObject(i);
                            HashMap<String, String> hashlist = new HashMap();
                            hashlist.put("rs", jsonObject.getString("rs"));
                            hashlist.put("desc", jsonObject.getString("desc"));
                            hashlist.put("validity", jsonObject.getString("validity"));
                            hashlist.put("isSelected", String.valueOf(false)); // Initially set to false
                            arrLegalList.add(hashlist);
                        }
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(DTHRecharge.this, 1);
                        pdfAdapTer = new DTHRecharge.LegalListAdapter(DTHRecharge.this, arrLegalList);
                        plan_rv.setLayoutManager(layoutManager);
                        plan_rv.setAdapter(pdfAdapTer);
                    }  else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DTHRecharge.this);
                        builder.setTitle("Error Response!").setMessage("Something Went Wrong!\n").setCancelable(false).setIcon(R.drawable.logo)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        alertDialog1.dismiss(); }});
                        builder.create().show(); }}
                catch (JSONException e) {
                    e.printStackTrace(); }         }
        }, DTHRecharge.this, call1, "", true);

    }



    public class LegalListAdapter extends RecyclerView.Adapter<LegalListAdapter.LegalList> {

        private ArrayList<HashMap<String, String>> data;
        private Context context;
        private int selectedPosition = RecyclerView.NO_POSITION;
        public LegalListAdapter(Context context, ArrayList<HashMap<String, String>> arrLegalList) {
            this.context = context;
            this.data = arrLegalList;
        }

        @Override
        public LegalListAdapter.LegalList onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_viewplan, parent, false);
            return new LegalListAdapter.LegalList(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final LegalListAdapter.LegalList holder, final int position) {
            holder.head_rb.setText("Rs. "+data.get(position).get("rs"));
            holder.des_tv.setText(data.get(position).get("desc") + "\nValidity:- " + data.get(position).get("validity"));
            // Set background color based on the selected position
            if (position == selectedPosition) {
                holder.itemView.setBackgroundColor(Color.GRAY); // Set your desired color
            } else {
                holder.itemView.setBackgroundResource(R.drawable.rounded_edittext);
            }

            // Handle item click
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the selected position
                    int previousSelectedPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    amount_et.setText(data.get(position).get("rs"));
                    plan_dis_tv.setText(data.get(position).get("desc") + "\nValidity:- " + data.get(position).get("validity"));
                    // Notify the adapter that the data has changed
                    notifyItemChanged(previousSelectedPosition);
                    notifyItemChanged(selectedPosition);
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class LegalList extends RecyclerView.ViewHolder {
            TextView head_rb;
            TextView des_tv;

            public LegalList(View itemView) {
                super(itemView);
                head_rb = itemView.findViewById(R.id.head_rb);
                des_tv = itemView.findViewById(R.id.des_tv);
            }
        }
    }
}
