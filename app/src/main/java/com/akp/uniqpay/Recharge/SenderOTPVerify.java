package com.akp.uniqpay.Recharge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost1;

public class SenderOTPVerify extends AppCompatActivity {
    TextView tvMobileNo, tvResend;
    //EditText
    EditText etCode1, etCode2, etCode3, etCode4, etCode5, etCode6;
    int check = 0;
    TextView tvTimerText,tvOtp;
    //Button
    Button btnSubmit;
    String OTP,Mobile;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_o_t_p_verify);
        FindId();
        OnClick();
    }

    private void OnClick() {
        etCode1.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    etCode2.requestFocus();
            }} );
        etCode2.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    etCode3.requestFocus();
            }
        } );
        etCode3.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    etCode4.requestFocus();
            }
        } );
        etCode4.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    etCode5.requestFocus();
            }
        } );
        etCode5.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    etCode6.requestFocus();
            }
        } );
        etCode6.addTextChangedListener( new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        } );
        etCode1.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace
                    if (etCode1.getText().toString().trim().isEmpty()) {
                        etCode1.requestFocus();
                    }
                }
                return false;
            }
        } );
        etCode2.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace
                    if (etCode2.getText().toString().trim().isEmpty()) {
                        etCode1.requestFocus();
                    }}
                return false;}} );
        etCode3.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace
                    if (etCode3.getText().toString().trim().isEmpty()) {
                        etCode2.requestFocus();
                    }}
                return false;
            }
        } );
        etCode4.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace

                    if (etCode4.getText().toString().trim().isEmpty()) {
                        etCode3.requestFocus();
                    }

                }
                return false;
            }
        } );
        etCode5.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace

                    if (etCode5.getText().toString().trim().isEmpty()) {
                        etCode4.requestFocus();
                    }

                }
                return false;
            }
        } );
        etCode6.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // You can identify which key pressed buy checking keyCode value
                // with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    // this is for backspace

                    if (etCode6.getText().toString().trim().isEmpty()) {
                        etCode5.requestFocus();
                    }

                }
                return false;
            }
        } );
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = etCode1.getText().toString().trim() +
                        etCode2.getText().toString().trim() +
                        etCode3.getText().toString().trim() +
                        etCode4.getText().toString().trim() +
                        etCode5.getText().toString().trim() +
                        etCode6.getText().toString().trim();

                if (otp.length() != 6) {
                    AppUtils.showToastSort( getApplicationContext(),"Enter Valid OTP");
                } else {
                    Otpverify( otp );
                }
            }


        });
    }

    private void FindId() {
        OTP = getIntent().getStringExtra("send_otp");
        Mobile = getIntent().getStringExtra("send_mob");
        ivBack=findViewById(R.id.ivBack);
        //Button
        btnSubmit = findViewById( R.id.btnSubmit );
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //EditText
        etCode1 = findViewById( R.id.etCode1 );
        etCode2 = findViewById( R.id.etCode2 );
        etCode3 = findViewById( R.id.etCode3 );
        etCode4 = findViewById( R.id.etCode4 );
        etCode5 = findViewById( R.id.etCode5 );
        etCode6 = findViewById( R.id.etCode6 );
        tvOtp = findViewById( R.id.tvOtp );
        tvMobileNo = findViewById( R.id.tvMobileNo );
        tvOtp.setText("Your Otp " +OTP );
        tvMobileNo.setText("+91 " +Mobile );
    }

    private void Otpverify(String otp) {
        String otp1 = new GlobalAppApis().Otpverify(Mobile,otp);
        ApiService client1 = getApiClient_ByPost1();
        Call<String> call1 = client1.Otpverify(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Log.v("responseeeee", String.valueOf(jsonObject));
                    if(jsonObject.getString("Status").equalsIgnoreCase("true"))
                    {

//                        Toast.makeText(getApplicationContext(),""+jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("RoleType",role_type);
//                        startActivity(intent);
//                        finish();
                    }
                    else {
                        Toast.makeText(SenderOTPVerify.this, "Something Went Wrong!..\n", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SenderOTPVerify.this, call1, "", true);

    }
}