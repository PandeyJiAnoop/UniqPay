package com.akp.uniqpay.Dash;
import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;

public class Das_GENERATEURL extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AppCompatButton btn_submit;
    TextView userid_tv;
    TextInputEditText emailet,nameet,middlenameet,lastnameet,genderet;
    String UserId,UserName;
    Spinner spin,title_sp,kyc_sp;
    String[] title = {"Shri", "Smt", "Kumari", "TransGender"};

    String[] kyctype = {"EKYC Mode", "Esign Mode"};

    String[] courses = {"Physical Pan", "Electronic Pan"};
    String SelectedValue,SelectedValue_title;
    String ResultStatus="";
    String Settitle="", SetMode="",SetKYCMode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_das_generateurl);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        UserName = sharedPreferences.getString("U_name", "");
        FindId();
        OnClick();
        userid_tv.setText(UserId);
    }

    private void OnClick() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameet.getText().toString().equalsIgnoreCase("")){
                    nameet.setError("Fields can't be blank!");
                    nameet.requestFocus();
                }
                else if (genderet.getText().toString().equalsIgnoreCase("")){
                    genderet.setError("Fields can't be blank!");
                    genderet.requestFocus();
                }
                else if (emailet.getText().toString().equalsIgnoreCase("")){
                    emailet.setError("Fields can't be blank!");
                    emailet.requestFocus();
                }
                else{
                    LoangenerationSubmitAPI(UserId,emailet.getText().toString(),nameet.getText().toString(),genderet.getText().toString(),
                            SetKYCMode,lastnameet.getText().toString(),middlenameet.getText().toString(),SetMode,
                            Settitle);
                }
            }
        });
    }

    private void FindId() {
        userid_tv=findViewById(R.id.userid_tv);
        emailet=findViewById(R.id.emailet);
        emailet=findViewById(R.id.emailet);
        genderet=findViewById(R.id.genderet);
        nameet=findViewById(R.id.nameet);
        middlenameet=findViewById(R.id.middlenameet);
        lastnameet=findViewById(R.id.lastnameet);
        btn_submit=findViewById(R.id.btn_submit);
        spin = findViewById(R.id.rupee_sp);
        kyc_sp= findViewById(R.id.kyc_sp);
        title_sp= findViewById(R.id.title_sp);

        title_sp.setOnItemSelectedListener(this);
        ArrayAdapter adt = new ArrayAdapter(this, android.R.layout.simple_spinner_item, title);
        adt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title_sp.setAdapter(adt);


        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);

        kyc_sp.setOnItemSelectedListener(this);
        ArrayAdapter kyc = new ArrayAdapter(this, android.R.layout.simple_spinner_item, kyctype);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kyc_sp.setAdapter(kyc);


        RelativeLayout header=findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

   /*     Log.d("Result_UserId",UserId);
        Log.e("Result_UserName",UserName);
        Log.d("Result_Status",SelectedValue);*/

    }


    public void LoangenerationSubmitAPI(String UserId, String email, String firstname,String gender,
                                        String kyctype,String lastname,
                                        String middlename,String mode,String title) {
        String otp1 = new GlobalAppApis().CreatePancardAPI(UserId,email,firstname,
                gender,kyctype,lastname,middlename,mode,title);
        Log.d("lead_res",otp1);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getCreatePancard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("lead_res",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        Intent intent=new Intent(getApplicationContext(),PanCardGenrateWebview.class);
                        intent.putExtra("put_encdata",jsonObject.getString("encdata"));
                        intent.putExtra("put_url",jsonObject.getString("url"));
                        startActivity(intent);
//                        JSONArray jsonArrayr = jsonObject.getJSONArray("Response");
//                        for (int i = 0; i < jsonArrayr.length(); i++) {
//                            JSONObject jsonObject1 = jsonArrayr.getJSONObject(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(Das_GENERATEURL.this);
                        builder.setTitle(Html.fromHtml(jsonObject.getString("Message")));
                        builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Exit</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                finish();
                                dialog.dismiss();
                            }});
                        builder.create();
                        builder.show();
//                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Das_GENERATEURL.this);
                        builder.setTitle(Html.fromHtml(jsonObject.getString("Message")));
                        builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Exit</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.dismiss();
                            }});
                        builder.create();
                        builder.show();
                        Toast.makeText(getApplicationContext(), jsonObject.getString("Message"), Toast.LENGTH_LONG).show();
                    } } catch (JSONException e) {
                    e.printStackTrace();
                } } }, Das_GENERATEURL.this, call1, "", true);
    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method
    @Override
    public void onItemSelected(AdapterView arg0, View arg1, int position, long id)
    {
        SelectedValue=spin.getSelectedItem().toString();
        SelectedValue_title=title_sp.getSelectedItem().toString();
        if (SelectedValue_title.equalsIgnoreCase("Shri")){
            genderet.setText("M");
            genderet.setClickable(false);
            genderet.setFocusable(false);
            Settitle="1";
        }
        else if(SelectedValue_title.equalsIgnoreCase("Smt")){
            genderet.setText("F");
            genderet.setClickable(false);
            genderet.setFocusable(false);
            Settitle="2";
        }
        else if (SelectedValue_title.equalsIgnoreCase("Kumari")){
            genderet.setText("F");
            genderet.setClickable(false);
            genderet.setFocusable(false);
            Settitle="3";
        }
        else if (SelectedValue_title.equalsIgnoreCase("TransGender")){
            genderet.setText("T");
            genderet.setClickable(false);
            genderet.setFocusable(false);
            Settitle="3";
        }
        else {
            genderet.setText("M");
            genderet.setClickable(false);
            genderet.setFocusable(false);
            Settitle="1";
        }
        if (spin.getSelectedItem().toString().equalsIgnoreCase("Physical Pan")){
            SetMode="P";
        }
        else {
            SetMode="E";
        }

        if (kyc_sp.getSelectedItem().toString().equalsIgnoreCase("EKYC Mode")){
            SetKYCMode="K";
        }
        else {
            SetKYCMode="E";
        }
//        Toast.makeText(getApplicationContext(),spin.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),kyc_sp.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView arg0)
    {
        // Auto-generated method stub
    }
}