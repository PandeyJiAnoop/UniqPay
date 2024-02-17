package com.akp.uniqpay.Recharge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class MobileRecharge extends AppCompatActivity {
    ImageView ivBack;
    LinearLayout provider_ll;
    TextView provoider_et, plan_dis_tv;
    String service_name, UserId, getcode;
    String getProvider_id, roletype;
    AppCompatButton btnSubmit;
    private SharedPreferences sharedPreferences;
    EditText mob_et, amount_et;
    AlertDialog alertDialog;
    String get_operator_ref, get_payid;
    TextView title_tv, numb, view_plan_tv;
    SliderLayout sliderLayout;

    private Dialog alertDialog1;
    private final ArrayList<HashMap<String, String>> arrLegalList = new ArrayList<>();
    private LegalListAdapter pdfAdapTer;
    RecyclerView plan_rv;
    Spinner circle_sp;
    LinearLayout buttonLayout;
    private Button selectedButton;  // Add this member variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
        //hide keyboard :
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        FindId();
        OnClick();
    }

    private void OnClick() {
        view_plan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                if (mob_et.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Enter Number", Toast.LENGTH_LONG).show();
                } else if (circle_sp.getSelectedItem().toString().equalsIgnoreCase("Select Circle")) {
                    Toast.makeText(getApplicationContext(), "Select Circle First", Toast.LENGTH_LONG).show();
                } else {
                    GetAppPlanAPI(circle_sp.getSelectedItem().toString(), mob_et.getText().toString(), service_name);
                }
            } });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mob_et.getText().toString().equalsIgnoreCase("")) {
                    mob_et.setError("Enter Mobile Number");
                    mob_et.requestFocus();
                } else if (circle_sp.getSelectedItem().toString().equalsIgnoreCase("Select Circle")) {
                    Toast.makeText(getApplicationContext(), "Select Circle First", Toast.LENGTH_LONG).show();
                } else if (amount_et.getText().toString().equalsIgnoreCase("")) {
                    amount_et.setError("Enter Plan Amount");
                    amount_et.requestFocus();
                } else {
                    String amount = amount_et.getText().toString().replaceAll("\\.\\d+$", "");
                    btnSubmit.setEnabled(false);
                    btnSubmit.setAlpha(0.5F);
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
                                    btnSubmit.setAlpha(1.0F);
                                }
                            });
                        }
                    }, 7000); // delay button enable for 0.7 sec
                } } });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void FindId() {
        title_tv = findViewById(R.id.title_tv);
        sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        service_name = getIntent().getStringExtra("servicename");
        getProvider_id = getIntent().getStringExtra("provider_id");
//        getonlyservice=getIntent().getStringExtra("onlyservice");
        getcode = getIntent().getStringExtra("MethodType");
        Log.d("getProvider_id", getProvider_id);
//        Toast.makeText(getApplicationContext(), getonlyservice,Toast.LENGTH_LONG).show();
        numb = findViewById(R.id.numb);
        plan_rv = findViewById(R.id.plan_rv);
        circle_sp = findViewById(R.id.circle_sp);
        buttonLayout = findViewById(R.id.buttonLayout);
        btnSubmit = findViewById(R.id.btnSubmit);
        ivBack = findViewById(R.id.ivBack);
        provider_ll = findViewById(R.id.provider_ll);
        provoider_et = findViewById(R.id.provoider_et);
        mob_et = findViewById(R.id.mob_et);
        amount_et = findViewById(R.id.amount_et);
        title_tv.setText(service_name);
        plan_dis_tv = findViewById(R.id.plan_dis_tv);
        view_plan_tv = findViewById(R.id.view_plan_tv);
        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :
        setSliderViews();

        if (service_name == null) {
        } else {
            provoider_et.setText(service_name);
        }


        mob_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    hideKeyboard();
                } }});

    }

    private void showpopupwindow() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(MobileRecharge.this).inflate(R.layout.successfullycreated_popup, viewGroup, false);
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
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MobileRecharge.this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void Payment(String Amount, String Circle, String MobileNo, String UserId, String optr) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String otp1 = new GlobalAppApis().PostRechargeAPI(Amount, Circle, MobileNo, UserId, optr);
        Log.d("makepayment_req", otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.MakeRecharge_AnshPay(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                progressDialog.dismiss();
                Log.d("makepayment_res", result);
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
                    progressDialog.dismiss();
                    e.printStackTrace();
                } }}, MobileRecharge.this, call1, "", true);
    }

    private void setSliderViews() {
        for (int i = 0; i <= 4; i++) {
            SliderView sliderView = new SliderView(MobileRecharge.this);
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.ban_1);
                    sliderView.setDescription("Welcome To\n" + "UniqPay Company");
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
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MobileRecharge.this, "Welcome To UniqPay " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }


    private void GetAppPlanAPI(String method, String mob, String operator) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String otp1 = new GlobalAppApis().PlanAPI(method, mob, operator);
        Log.d("plarws", otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetAllPlan(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("plarws", result);
                progressDialog.dismiss();
                try {
                    JSONObject objectlist = new JSONObject(result);
                    if (objectlist.getString("status").equalsIgnoreCase("1")) {
                        JSONObject responobj = objectlist.getJSONObject("records");
                        // Get all array names
                        JSONArray arrayNames = responobj.names();
                        // Create buttons dynamically
                        for (int i = 0; i < arrayNames.length(); i++) {
                            final String arrayName = arrayNames.getString(i);
                            showArrayData(responobj.optJSONArray("COMBO"));
                            Button arrayButton = new Button(MobileRecharge.this);
                            arrayButton.setText(arrayName);
                            arrayButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (selectedButton != null) {
                                        // Deselect the previously selected button
                                        selectedButton.setTextColor(Color.WHITE);  // Set the desired color
                                    }
                                    // Show the corresponding array data in RecyclerView
                                    showArrayData(responobj.optJSONArray(arrayName));
                                    // Change the text color of the clicked button
                                    arrayButton.setTextColor(Color.RED);  // Set the desired color

                                    // Update the reference to the currently selected button
                                    selectedButton = arrayButton;
                                }});
                            // Add the button to your layout
                            // For example, if you have a LinearLayout named buttonLayout
                            buttonLayout.addView(arrayButton);
                        }
                        // Set the text color of the first button to red initially
                        if (buttonLayout.getChildCount() > 0) {
                            Button firstButton = (Button) buttonLayout.getChildAt(0);
                            firstButton.setTextColor(Color.RED);  // Set the desired color
                            selectedButton = firstButton;  // Update the reference to the currently selected button
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MobileRecharge.this, "Error ! ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }} }, MobileRecharge.this, call1, "", true);
    }

    private void showArrayData(JSONArray dataArray) {
        ArrayList<HashMap<String, String>> arrayDataList = new ArrayList<>();

        if (dataArray != null) {
            // Process the array data and display it in RecyclerView
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = dataArray.getJSONObject(i);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                HashMap<String, String> hashlist = new HashMap<>();
                hashlist.put("rs", jsonObject.optString("rs"));
                hashlist.put("desc", jsonObject.optString("desc"));
                hashlist.put("validity", jsonObject.optString("validity"));
                hashlist.put("isSelected", String.valueOf(false)); // Initially set to false
                arrayDataList.add(hashlist);
            }
        }

        // Set up RecyclerView
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MobileRecharge.this, 1);
        LegalListAdapter adapter = new LegalListAdapter(MobileRecharge.this, arrayDataList);
        plan_rv.setLayoutManager(layoutManager);
        plan_rv.setAdapter(adapter);
    }


//    private void GetAppPlanAPI(String method, String mob, String operator) {
//        String otp1 = new GlobalAppApis().PlanAPI(method,mob,operator);
//        ApiService client1 = getApiClient_ByPost();
//        Call<String> call1 = client1.GetAllPlan(otp1);
//        new ConnectToRetrofit(new RetrofitCallBackListenar() {
//            @Override
//            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Log.d("plarws",result);
//                try {
//                    JSONObject objectlist = new JSONObject(result);
//                    if (objectlist.getString("status").equalsIgnoreCase("1")){
//                    JSONObject Responobj= objectlist.getJSONObject("records");
////                    JSONObject Respon= Responobj.getJSONObject("data");
//                    JSONArray Response1 = Responobj.getJSONArray("COMBO");
//                    for (int i = 0; i < Response1.length(); i++) {
////                            title_tv.setText("Passbook History("+Response1.length()+")");
//                        JSONObject jsonObject = Response1.getJSONObject(i);
//                        HashMap<String, String> hashlist = new HashMap();
//                        hashlist.put("rs", jsonObject.getString("rs"));
//                        hashlist.put("desc", jsonObject.getString("desc"));
//                        hashlist.put("validity", jsonObject.getString("validity"));
//                        arrLegalList.add(hashlist);
//                    }
//                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MobileRecharge.this, 1);
//                    pdfAdapTer = new LegalListAdapter(MobileRecharge.this, arrLegalList);
//                    plan_rv.setLayoutManager(layoutManager);
//                    plan_rv.setAdapter(pdfAdapTer);
//                }  else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MobileRecharge.this);
//                        builder.setTitle("Error Response!").setMessage("Something Went Wrong!").setCancelable(false).setIcon(R.drawable.logo)
//                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.dismiss();
//                                        alertDialog1.dismiss();
//                                    }});
//                        builder.create().show();
//
//                    }
//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//                }            }
//        }, MobileRecharge.this, call1, "", true);
//
//    }


    public class LegalListAdapter extends RecyclerView.Adapter<LegalListAdapter.LegalList> {

        private ArrayList<HashMap<String, String>> data;
        private Context context;
        private int selectedPosition = RecyclerView.NO_POSITION;
        public LegalListAdapter(Context context, ArrayList<HashMap<String, String>> arrLegalList) {
            this.context = context;
            this.data = arrLegalList;
        }

        @Override
        public LegalList onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_viewplan, parent, false);
            return new LegalList(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final LegalList holder, final int position) {
            holder.head_rb.setText("Rs. "+data.get(position).get("rs"));
            holder.des_tv.setText(data.get(position).get("desc") + "\nValidity:- " + data.get(position).get("validity"));
//            // Set the checked state without triggering the listener
//            holder.head_rb.setChecked(Boolean.parseBoolean(data.get(position).get("isSelected")));
//
//            holder.head_rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    // Update the data model to reflect the new selection state
//                    data.get(position).put("isSelected", String.valueOf(isChecked));
//                    notifyDataSetChanged();
//
//                    // Debugging output
//                    Log.d("RadioButtonState", "Position: " + position + ", isSelected: " + data.get(position).get("isSelected"));
//                    amount_et.setText(data.get(position).get("rs"));
//                    plan_dis_tv.setText(data.get(position).get("desc") + "\nValidity:- " + data.get(position).get("validity"));
//                    // Notify the adapter that the data has changed
//                }
//            });

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



//            holder.head_rb.setChecked(Boolean.parseBoolean(data.get(position).get("isSelected")));
//
//            holder.head_rb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Update the data model to reflect the new selection state
//                    boolean currentSelection = Boolean.parseBoolean(data.get(position).get("isSelected"));
//                    for (int i = 0; i < data.size(); i++) {
//                        data.get(i).put("isSelected", String.valueOf(i == position && !currentSelection));
//                    }
//                    // Notify the adapter that the data has changed
//                    notifyDataSetChanged();
//                    // Update other UI elements as needed
//                    // Assuming amount_et and plan_dis_tv are properly declared and initialized
//                    amount_et.setText(data.get(position).get("rs"));
//                    plan_dis_tv.setText(data.get(position).get("desc") + "\nValidity:- " + data.get(position).get("validity"));
//                    // Debugging output
//                    Log.d("RadioButtonState", "Position: " + position + ", isSelected: " + data.get(position).get("isSelected"));
//                }
//            });

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
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mob_et.getWindowToken(), 0);
    }

}
/*

public void GetAppPlanAPI(String op_code) {
    final ProgressDialog progressDialog = new ProgressDialog(MobileRecharge.this);
    progressDialog.setMessage("Loading...");
    progressDialog.show();
    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://amazpay.in.net/api/Amazepay/OPERATOR_CODE?operatorcode="+op_code, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            progressDialog.dismiss();
            Log.d("res_p",response);
            try {
                JSONObject objectlist = new JSONObject(response);
                JSONObject Responobj= objectlist.getJSONObject("Response");
                JSONObject Respon= Responobj.getJSONObject("data");
                JSONArray Response1 = Respon.getJSONArray("plans");
                for (int i = 0; i < Response1.length(); i++) {
//                            title_tv.setText("Passbook History("+Response1.length()+")");
                    JSONObject jsonObject = Response1.getJSONObject(i);
                    HashMap<String, String> hashlist = new HashMap();
                    hashlist.put("planType", jsonObject.getString("planType"));
                    hashlist.put("planCode", jsonObject.getString("planCode"));
                    hashlist.put("amount", jsonObject.getString("amount"));
                    hashlist.put("validity", jsonObject.getString("validity"));
                    hashlist.put("planName", jsonObject.getString("planName"));
                    hashlist.put("planDescription", jsonObject.getString("planDescription"));
                    arrLegalList.add(hashlist);
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MobileRecharge.this, 1);
                pdfAdapTer = new LegalListAdapter(MobileRecharge.this, arrLegalList);
                plan_rv.setLayoutManager(layoutManager);
                plan_rv.setAdapter(pdfAdapTer);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            Toast.makeText(MobileRecharge.this, "Internet connection is slow Or no internet connection", Toast.LENGTH_SHORT).show();
        }
    });
    stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    RequestQueue requestQueue = Volley.newRequestQueue(MobileRecharge.this);
    requestQueue.add(stringRequest);

}
            public class LegalListAdapter extends RecyclerView.Adapter<LegalList> {
                ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
                public LegalListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrLegalList) {
                    data = arrLegalList;
                }
                public LegalList onCreateViewHolder(ViewGroup parent, int viewType) {
                    return new LegalList(LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_viewplan, parent, false));
                }
                @SuppressLint("SetTextI18n")
                public void onBindViewHolder(final LegalList holder, final int position) {
                    holder.head_rb.setText(data.get(position).get("planName"));
                    holder.des_tv.setText(data.get(position).get("planDescription"));
                    holder.tv.setText("Plan Amount:- " + data.get(position).get("amount"));
                    holder.tv1.setText("Validity:- " + data.get(position).get("validity"));
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder.head_rb.setChecked(true);
//                    alertDialog1.dismiss();
                            amount_et.setText(data.get(position).get("amount"));
                            plan_dis_tv.setText(data.get(position).get("planDescription"));
                        }});
                }
                public int getItemCount() {
                    return data.size();
                }
            }
            public class LegalList extends RecyclerView.ViewHolder {
                TextView des_tv, tv1, tv;
                RadioButton head_rb;
                public LegalList(View itemView) {
                    super(itemView);
                    head_rb = itemView.findViewById(R.id.head_rb);
                    des_tv = itemView.findViewById(R.id.des_tv);
                    tv = itemView.findViewById(R.id.tv);
                    tv1 = itemView.findViewById(R.id.tv1);
                }
            }

        }*/
