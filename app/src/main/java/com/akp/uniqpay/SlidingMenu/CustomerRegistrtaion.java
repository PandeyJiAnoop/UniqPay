package com.akp.uniqpay.SlidingMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.LoginScreen;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class CustomerRegistrtaion extends AppCompatActivity {
    ImageView menuImg;
    TextInputEditText sponser_et,name_et,email_et,mob_et,c_pass_et,pass_et,username_et,sponser_name_et;
    AppCompatButton signup_btn;
    String UserId;
//    SearchableSpinner country_et;
//    String stateid; ArrayList<String> StateName = new ArrayList<>();
//    ArrayList<String> StateId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registrtaion);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        findId();
        OnClick();
//        CountryList();
    }
    private void OnClick() {
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sponser_et.getText().toString().equalsIgnoreCase("")){
                    sponser_et.setError("Fields can't be blank!");
                    sponser_et.requestFocus();
                }
//                else if (username_et.getText().toString().equalsIgnoreCase("")){
//                    username_et.setError("Fields can't be blank!");
//                    username_et.requestFocus();
//                }
                else if (name_et.getText().toString().equalsIgnoreCase("")){
                    name_et.setError("Fields can't be blank!");
                    name_et.requestFocus();
                }
                else if (email_et.getText().toString().equalsIgnoreCase("")){
                    email_et.setError("Fields can't be blank!");
                    email_et.requestFocus();
                }
                else if (mob_et.getText().toString().equalsIgnoreCase("")){
                    mob_et.setError("Fields can't be blank!");
                    mob_et.requestFocus();
                }
                else if (pass_et.getText().toString().equalsIgnoreCase("")){
                    pass_et.setError("Fields can't be blank!");
                    pass_et.requestFocus();
                }
                else if (c_pass_et.getText().toString().equalsIgnoreCase("")){
                    c_pass_et.setError("Fields can't be blank!");
                    c_pass_et.requestFocus();
                }
                else if (!pass_et.getText().toString().equalsIgnoreCase(c_pass_et.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Password Not Matched!",Toast.LENGTH_LONG).show();
                }
                else {
                    getRegAPI(email_et.getText().toString(),mob_et.getText().toString(),name_et.getText().toString(),pass_et.getText().toString(),sponser_et.getText().toString());
                }
            }
        });

        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sponser_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    private void findId() {
        menuImg=findViewById(R.id.menuImg);
        sponser_name_et=findViewById(R.id.sponser_name_et);
        sponser_et=findViewById(R.id.sponser_et);
        name_et=findViewById(R.id.name_et);
        email_et=findViewById(R.id.email_et);
        mob_et=findViewById(R.id.mob_et);
        pass_et=findViewById(R.id.pass_et);
        c_pass_et=findViewById(R.id.c_pass_et);
        signup_btn=findViewById(R.id.signup_btn);
        username_et=findViewById(R.id.username_et);
//        sponser_et.setText(UserId);
/*
        country_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        if (country_et.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
//                            stateid = StateId.get(j);
                            stateid = StateId.get(j - 1);
                            break;
                        } } } }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

    }

    public void  getRegAPI(String EmailId, String MobileNo, String Name,String Password,String SponsorId){
        String otp1 = new GlobalAppApis().NewAccount(EmailId,MobileNo,Name,Password,SponsorId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getRegister(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resppp",result);
//                {"MemberId":"UP026474","Message":"UP026474 ttest Password is 12345 and Transaction Password is 12345 has been registered successfully.",
//                        "Name":"ttest","Password":"12345","Status":true}
                        try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(getApplicationContext(),RegistrationDetails.class);
                        intent.putExtra("associate_name",object.getString("Name"));
                        intent.putExtra("associate_id",object.getString("MemberId"));
                        intent.putExtra("password",object.getString("Password"));
                        intent.putExtra("sponsor_id",sponser_et.getText().toString());
                        intent.putExtra("sponsor_name",sponser_name_et.getText().toString());
                        startActivity(intent);

                       /* AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                        builder.setTitle("Registration Successfully!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                        dialog.dismiss();
                                    }});
                        builder.create().show();*/
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                        builder.setTitle("ERROR!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                        dialog.dismiss();
                                    } });
                        builder.create().show();
                    }
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                    builder.setTitle("ERROR!")
                            .setMessage("Something Went Wrong!!")
                            .setCancelable(false)
                            .setIcon(R.drawable.logo)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent=new Intent(getApplicationContext(), LoginScreen.class);
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                    e.printStackTrace();
                } }
        }, CustomerRegistrtaion.this, call1, "", true);
    }
   /* public void CountryList() {
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_GetCountryList();
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_GetCountryList","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")==false){
                        String msg       = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONArray jsonArray = object.getJSONArray("MyDirectResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("CountryCode"));
                            String statename = jsonObject1.getString("CountryName");
                            StateName.add(statename);
                        }
                        country_et.setAdapter(new ArrayAdapter<String>(CustomerRegistrtaion.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, CustomerRegistrtaion.this, call1, "", true);
    }*/










    private void getDataFromSponser(String refid)
    {
        String otp1 = new GlobalAppApis().CheckSponserA(refid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getCheckSponser(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equals("false")){
                        sponser_et.setText("");
                        sponser_name_et.setText("");
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerRegistrtaion.this);
                        builder.setTitle("Failed!- Invalid")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }); builder.create().show();
                    } else {
                            String SponserName = object.getString("SponserName");
                            sponser_name_et.setText(SponserName);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } } }, CustomerRegistrtaion.this, call1, "", true);
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getDataFromSponser(sponser_et.getText().toString());
    }
}