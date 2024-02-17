package com.akp.uniqpay.Basic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.akp.uniqpay.Dash.Das_CreditCardLead;
import com.akp.uniqpay.DashboardActivity;
import com.akp.uniqpay.R;
import com.akp.uniqpay.SlidingMenu.CustomerRegistrtaion;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class LoginScreen extends AppCompatActivity {
    TextView forget_tv;
    TextInputEditText user_name_et,pass_et;
    RelativeLayout btn_login,btn_forget;
    private PopupWindow popupWindow;
    RelativeLayout mail_rl;
    private SharedPreferences login_preference;
    private SharedPreferences.Editor login_editor;
    TextView help_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        GetId();
        OnClick();
    }

    private void OnClick() {
        help_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CustomerRegistrtaion.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_name_et.getText().toString().equalsIgnoreCase("")){
                    user_name_et.setError("Fields can't be blank!");
                    user_name_et.requestFocus();
                }
                else if (pass_et.getText().toString().equalsIgnoreCase("")){
                    pass_et.setError("Fields can't be blank!");
                    pass_et.requestFocus();
                }
                else {
//                    Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
//                    startActivity(intent);
                    getLoginAPI("1","",pass_et.getText().toString(),user_name_et.getText().toString());
                } } });
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                forgetpasswordpopup();
                String url = "https://uniqpay.in/index.aspx";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void GetId() {
        user_name_et=findViewById(R.id.user_name_et);
        pass_et=findViewById(R.id.pass_et);
        btn_forget=findViewById(R.id.btn_forget);
        btn_login=findViewById(R.id.btn_login);
        mail_rl=findViewById(R.id.mail_rl);
        help_tv=findViewById(R.id.help_tv);
        TextView textv = (TextView) findViewById(R.id.tv1);
//        textv.getPaint().setStrokeWidth(1);
//        textv.getPaint().setStyle(Paint.Style.STROKE);
        TextView textv1 = (TextView) findViewById(R.id.tv2);
    }

    private void forgetpasswordpopup() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.forget_password,null);
        ImageView Goback = (ImageView) customView.findViewById(R.id.Goback);
        EditText email_et = (EditText) customView.findViewById(R.id.email_et);
        AppCompatButton continue_btn = (AppCompatButton) customView.findViewById(R.id.continue_btn);
        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //display the popup window
        popupWindow.showAtLocation(mail_rl, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        // Settings disappear when you click somewhere else
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.update();
        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    public void  getLoginAPI(String action, String pakid, String pass,String userid){
        String otp1 = new GlobalAppApis().Login(action,pakid,pass,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getLogin(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("response_login",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String activeStatus       = jsonobject.getString("ActiveStatus");
                        String customerId    = jsonobject.getString("CustomerId");
                        String groupName       = jsonobject.getString("GroupName");
                        String name       = jsonobject.getString("Name");
                        String role       = jsonobject.getString("Role");
                        login_preference = getSharedPreferences("login_preference", MODE_PRIVATE);
                        login_editor = login_preference.edit();
                        login_editor.putString("U_id",customerId);
                        login_editor.putString("U_name",name);
                        login_editor.putString("U_role",role);
                        login_editor.commit();
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(intent);
                    } }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
                        builder.setTitle(Html.fromHtml(object.getString("Message")));
                        builder.setNegativeButton(Html.fromHtml("<font color='#D32F2F'>Exit</font>"), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                dialog.dismiss();
                            } });
                        builder.create();
                        builder.show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }}, LoginScreen.this, call1, "", true);
    }
}