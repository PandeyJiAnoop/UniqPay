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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.AppUrls;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.DasRechargeListAdapter;
import com.akp.uniqpay.DashboardActivity;
import com.akp.uniqpay.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class BBPS_BillPayment extends AppCompatActivity {
    ImageView ivBack;
    LinearLayout provider_ll;
    TextView provoider_et;
    String UserId;
    AppCompatButton btnSubmit;
    private SharedPreferences sharedPreferences;
    EditText mob_et,amount_et;
    String ClientRefId;
    AlertDialog alertDialog;
    String get_operator_ref;
    TextView title_tv,mob_code_tv;
    TextView txtMarquee,view_plan_tv,plan_dis_tv;
    private Dialog alertDialog1;
    private final ArrayList<HashMap<String, String>> arrLegalList = new ArrayList<>();
    //    private LegalListAdapter pdfAdapTer;
    RecyclerView plan_rv;
    SearchableSpinner sp_state,billerlist_sp;
    String stateid; ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();
    private String CategoryName,DisplayName;
    private String UserName,AcceptPartPay,AcceptPayment;


    ArrayList<String> BillerName = new ArrayList<>();
    ArrayList<String> BillerId = new ArrayList<>();
    private String billername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbps_bill_payment);
        FindId();
        OnClickListner();
    }

    private void OnClickListner() {
        // Now we will call setSelected() method
        // and pass boolean value as true
        txtMarquee.setSelected(true);
        sp_state.setTitle("");
//        Toast.makeText(getApplicationContext(),getonlyservice,Toast.LENGTH_LONG).show();
        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        if (sp_state.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
                            stateid = StateId.get(j);
                            break;
                        } } } }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view_plan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mob_et.getText().toString().equalsIgnoreCase("")){
                    mob_et.setError("Fields can not be blank!");
                    mob_et.requestFocus();
                }
                else {
                    GetAppPlanAPI(mob_et.getText().toString(),stateid,UserId);

                }} });






        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mob_et.getText().toString().equalsIgnoreCase("")){
                    mob_et.setError("Fields can't be blank!");
                    mob_et.requestFocus();
                }
                else if (amount_et.getText().toString().equalsIgnoreCase("")){
                    amount_et.setError("Fields can't be blank!");
                    amount_et.requestFocus();
                }
                else {
                    Payment(AcceptPartPay,AcceptPayment,amount_et.getText().toString(),amount_et.getText().toString(),mob_et.getText().toString(),"0",
                            UserName,amount_et.getText().toString(),CategoryName,"Online",stateid,
                            sp_state.getSelectedItem().toString(),"2323312",UserId,mob_et.getText().toString());
                }}});

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void FindId() {
        title_tv=findViewById(R.id.title_tv);
        mob_code_tv=findViewById(R.id.mob_code_tv);
        plan_rv = (RecyclerView) findViewById(R.id.plan_rv);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        plan_dis_tv=findViewById(R.id.plan_dis_tv);
        btnSubmit=findViewById(R.id.btnSubmit);
        sp_state=findViewById(R.id.stateet);
        billerlist_sp=findViewById(R.id.billerlist_sp);
        // casting of textview
        txtMarquee = (TextView) findViewById(R.id.marqueeText);

        ivBack=findViewById(R.id.ivBack);
        provider_ll=findViewById(R.id.provider_ll);
        provoider_et=findViewById(R.id.provoider_et);
        mob_et=findViewById(R.id.mob_et);
        amount_et=findViewById(R.id.amount_et);
        view_plan_tv=findViewById(R.id.view_plan_tv);



        AllBillerList();



        billerlist_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    for (int i = 0; i <= BillerId.size(); i++) {
                        if (billerlist_sp.getSelectedItem().toString().equalsIgnoreCase(BillerName.get(i))) {
                            billername = BillerName.get(i);
//                            billername = BillerName.get(k);

                            if (billername.equalsIgnoreCase("LANDLINE")){
                                StateName.clear(); GetOperatorList("Landline");
                                mob_et.setHint("Enter Customer Number");
                            }
                            else if (billername.equalsIgnoreCase("ELECTRICITY")){
                                StateName.clear(); GetOperatorList("Electricity");
                                mob_et.setHint("Enter Consumer Number");
                            }
                            else if (billername.equalsIgnoreCase("EMI")){
                                StateName.clear(); GetOperatorList("EMI Payments");
                                mob_et.setHint("Enter Loan Number");
                            }
                            else if (billername.equalsIgnoreCase("FASTAG")){
                                StateName.clear(); GetOperatorList("Fast tag");
                                mob_et.setHint("Enter Vehicle Registration Number");
                            }
                            else if (billername.equalsIgnoreCase("GAS")){
                                StateName.clear(); GetOperatorList("Gas");
                                mob_et.setHint("Enter Customer ID");
                            }
                            else if (billername.equalsIgnoreCase("GAS BOOKING")){
                                StateName.clear(); GetOperatorList("LPG");
                                mob_et.setHint("Enter Registered Mobile Number/Consumer No");
                            }
                            else if (billername.equalsIgnoreCase("INSURANCE")){
                                StateName.clear(); GetOperatorList("Insurance");
                                mob_et.setHint("Enter Policy Number");
                            }
                            else if (billername.equalsIgnoreCase("INTERNET/ISP")){
                                StateName.clear(); GetOperatorList("Broadband");
                                mob_et.setHint("Enter Consumer Number");
                            }
                            else if (billername.equalsIgnoreCase("WATER")){
                                StateName.clear(); GetOperatorList("Water");
                                mob_et.setHint("Enter Account Number");
                            }
                            else if (billername.equalsIgnoreCase("POSTPAID")){
                                StateName.clear();
                                GetOperatorList("Postpaid");
                                mob_et.setHint("Enter Account Number");
                            }
                            else {
                                StateName.clear(); GetOperatorList("Landline");
                                mob_et.setHint("Enter Account Number");
                            }
                            break;
                        }
                    }


//                    GetOperatorList(billername);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
    }


    public void AllBillerList() {
        String otp1 = new GlobalAppApis().GetProviderList("Bill");
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetProviderListService(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("fsasfaf",result);
                BillerName.add("Select Service Category");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArrayr = jsonObject.getJSONArray("ProviderRes");
                    for (int i = 0; i < jsonArrayr.length(); i++) {
                        JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                        BillerId.add(jsonObject1.getString("ProviderId"));
                        String Cityname = jsonObject1.getString("ProviderName");
                        BillerName.add(Cityname);
                    }
                    billerlist_sp.setAdapter(new ArrayAdapter<String>(BBPS_BillPayment.this, android.R.layout.simple_spinner_dropdown_item, BillerName));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, BBPS_BillPayment.this, call1, "", true);
    }



    private void showpopupwindow() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(BBPS_BillPayment.this).inflate(R.layout.successfullycreated_popup, viewGroup, false);
        Button ok = (Button) dialogView.findViewById(R.id.btnDialog);
        TextView txt_msg=dialogView.findViewById(R.id.txt_msg);
        TextView id_tv= dialogView.findViewById(R.id.id_tv);
        TextView pass_tv= dialogView.findViewById(R.id.pass_tv);
        id_tv.setText(get_operator_ref);
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


    private void Payment(String AcceptPartPay,String AcceptPayment,String billamount, String billnetamount, String cellno, String maxbillamount, String username,
                         String Amount, String bbpsservice, String mode,String operatorid,String optrname,
                         String uniqid,String UserId,String AccountId) {
        String otp1 = new GlobalAppApis().Ans_BillPaymentAPI(AcceptPartPay,AcceptPayment,billamount,billnetamount,cellno,maxbillamount,username,Amount,bbpsservice,mode,
                operatorid,optrname,uniqid,UserId,AccountId);
        Log.e("PostRecharge",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.BillPayment_AnshPayAPI(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.e("PostRecharge",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("MessageId").equalsIgnoreCase("0")){
                        String msg       = jsonObject.getString("Message");
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BBPS_BillPayment.this);
                        builder.setTitle("Response!")
                                .setMessage(msg)
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    } });
//                        .setNeutralButton("Middle", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(MyActivity.this, "Middle button clicked!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                        builder.create().show();
                    }
                    else {
                        JSONObject Respon= jsonObject.getJSONObject("Data");
                        get_operator_ref=Respon.getString("message");
                        showpopupwindow();
                        Toast.makeText(getApplicationContext(),Respon.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, BBPS_BillPayment.this, call1, "", true);
    }

    public void  GetAppPlanAPI(String AccountId, String OperatorId, String UserId){
        String otp1 = new GlobalAppApis().Ans_BillFetchAPI(AccountId,OperatorId,UserId);
        Log.d("GetAppPlanAPIparm",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getBillFetch_AnshPay(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("GetAppPlanAPIresponse",result);
                try {
                    JSONObject objectlist = new JSONObject(result);
                    if (objectlist.getString("MessageId").equalsIgnoreCase("0")){
                        Toast.makeText(getApplicationContext(),objectlist.getString("Message"),Toast.LENGTH_LONG).show();
                        plan_dis_tv.setText(objectlist.getString("Message"));
                    }
                    else {
                        JSONObject Respon= objectlist.getJSONObject("Data");
                        JSONObject Respon1= Respon.getJSONObject("BillFetch");
                        amount_et.setText(Respon1.getString("BillNetamount"));
                        AcceptPayment=Respon1.getString("AcceptPayment");
                        AcceptPartPay=Respon1.getString("AcceptPartPay");
                        ClientRefId=Respon1.getString("CellNumber");
                        UserName=Respon1.getString("UserName");
                        plan_dis_tv.setText("Due Amount:-Rs. "+Respon1.getString("BillNetamount")+
                                "\nCustomer Name:- "+Respon1.getString("UserName"));//
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }}, BBPS_BillPayment.this, call1, "", true);
    }


    public class LegalListAdapter extends RecyclerView.Adapter<BBPS_BillPayment.LegalList> {
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public LegalListAdapter(Context applicationContext, ArrayList<HashMap<String, String>> arrLegalList) {
            data = arrLegalList;
        }

        public BBPS_BillPayment.LegalList onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BBPS_BillPayment.LegalList(LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_viewplan, parent, false));
        }

        @SuppressLint("SetTextI18n")
        public void onBindViewHolder(final BBPS_BillPayment.LegalList holder, final int position) {
            holder.head_rb.setText(data.get(position).get("customername"));
            holder.des_tv.setText("Customer Name"+data.get(position).get("customername"));
            holder.tv.setText("Due Amount:- "+data.get(position).get("dueamount"));
            holder.tv1.setText("Due Date:- "+data.get(position).get("duedate"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.head_rb.setChecked(true);
//                    alertDialog1.dismiss();
                    amount_et.setText(data.get(position).get("dueamount"));
                    plan_dis_tv.setText(data.get(position).get("customername")+"\n"+data.get(position).get("duedate"));
                }});
        }
        public int getItemCount() {
            return data.size();
        }
    }

    public class LegalList extends RecyclerView.ViewHolder {
        TextView des_tv,tv1,tv;
        RadioButton head_rb;

        public LegalList(View itemView) {
            super(itemView);
            head_rb = itemView.findViewById(R.id.head_rb);
            des_tv = itemView.findViewById(R.id.des_tv);
            tv = itemView.findViewById(R.id.tv);
            tv1 = itemView.findViewById(R.id.tv1);
        }
    }

    public static String generateMD5Checksum(String input) {
        try {
            // Create an MD5 MessageDigest instance
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Convert the input string to bytes and feed it to the MessageDigest instance
            md.update(input.getBytes());
            // Generate the MD5 checksum
            byte[] digest = md.digest();
            // Convert the byte array to a hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void GetOperatorList(String optype) {
        String otp1 = new GlobalAppApis().BillerAPI(optype);
        Log.d("GetBBPSparam","cxc"+otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetBBPSOperator_AnshPayAPI(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("GetBBPSOperator","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("false")){
                        String msg       = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
//                        StateName.add("Select Service");
                        JSONArray jsonArray = object.getJSONArray("ProviderRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("Id"));
                            String statename = jsonObject1.getString("Name");
                            DisplayName = jsonObject1.getString("DisplayName");
                            CategoryName = jsonObject1.getString("Category");
                            StateName.add(statename);
                        }
                        sp_state.setAdapter(new ArrayAdapter<String>(BBPS_BillPayment.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    } } catch (JSONException e) {
                    e.printStackTrace();
                }} }, BBPS_BillPayment.this, call1, "", true);
    }



}