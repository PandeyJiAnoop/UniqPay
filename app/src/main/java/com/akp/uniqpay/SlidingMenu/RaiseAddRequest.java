package com.akp.uniqpay.SlidingMenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.akp.uniqpay.Basic.ApiService;
import com.akp.uniqpay.Basic.ConnectToRetrofit;
import com.akp.uniqpay.Basic.GlobalAppApis;
import com.akp.uniqpay.Basic.RetrofitCallBackListenar;
import com.akp.uniqpay.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import retrofit2.Call;

import static com.akp.uniqpay.Basic.API_Config.getApiClient_ByPost;

public class RaiseAddRequest extends AppCompatActivity {
ImageView menuImg;
RadioButton usdt,eth,btc,bnb;
String UserId;
ImageView image_qr;
TextView url_tv,select_tv;
    String str="";
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String temp;
    ImageView img_showProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_add_request);
        FindId();
        OnClick();
        WalletAPI("",UserId);
    }

    private void OnClick() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        menuImg=findViewById(R.id.menuImg);
        image_qr=findViewById(R.id.image_qr);
        url_tv=findViewById(R.id.url_tv);
        usdt = (RadioButton)findViewById(R.id.usdt);
        eth = (RadioButton)findViewById(R.id.eth);
        btc = (RadioButton)findViewById(R.id.btc);
        bnb = (RadioButton)findViewById(R.id.bnb);
        select_tv=findViewById(R.id.select_tv);
        img_showProfile=findViewById(R.id.img_showProfile);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.btc:
                if(checked)
                    str = "BTC";
                WalletAPI("",UserId);
                break;
            case R.id.bnb:
                if(checked)
                    str = "BNB";
                WalletAPI("",UserId);
                break;
            case R.id.usdt:
                if(checked)
                    str = "USDT";
                WalletAPI("",UserId);
                break;
            case R.id.eth:
                if(checked)
                    str = "ETH";
                WalletAPI("",UserId);
                break;
        }
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void  WalletAPI(String action,String userid){
        String otp1 = new GlobalAppApis().Wallet(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getWallet(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String credit       = jsonobject.getString("Credit");
                        String debit    = jsonobject.getString("Debit");
                        String walletBalance      = jsonobject.getString("WalletBalance");


                        String bnbAddress      = jsonobject.getString("BNBAddress");
                        String bnburl      = jsonobject.getString("BNBURL");
                        String btcAddress      = jsonobject.getString("BTCAddress");
                        String btcurl      = jsonobject.getString("BTCURL");
                        String ethAddress      = jsonobject.getString("ETHAddress");
                        String ethurl      = jsonobject.getString("ETHURL");
                        String usdtAddress      = jsonobject.getString("USDTAddress");
                        String usdturl      = jsonobject.getString("USDTURL");

                        if (btcurl.equalsIgnoreCase("")){
                        }
                        else {
                            Picasso.with(getApplicationContext()).load(btcurl).error(R.drawable.logo).into(image_qr);
                        }
                        url_tv.setText(btcAddress);
                        if(str.equalsIgnoreCase("BTC")){
                            if (btcurl.equalsIgnoreCase("")){
                            }
                            else {
                                Picasso.with(getApplicationContext()).load(btcurl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(btcAddress);

                        }
                        else if(str.equalsIgnoreCase("BNB")){

                            if (bnburl.equalsIgnoreCase("")){
                            }
                            else {
                                Picasso.with(getApplicationContext()).load(bnburl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(bnbAddress);

                        }
                        else if(str.equalsIgnoreCase("USDT")){
                            if (usdturl.equalsIgnoreCase("")){
                            }
                            else {
                                Picasso.with(getApplicationContext()).load(usdturl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(usdtAddress);
                        }
                        else if(str.equalsIgnoreCase("ETH")){

                            if (ethurl.equalsIgnoreCase("")){
                            }
                            else {
                                Picasso.with(getApplicationContext()).load(ethurl).error(R.drawable.logo).into(image_qr);
                            }
                            url_tv.setText(ethAddress);
                        }


//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, RaiseAddRequest.this, call1, "", true);
    }





    private void selectImage() {
        final CharSequence[] items = {"Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RaiseAddRequest.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(RaiseAddRequest.this);
                if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Toast.makeText(getApplicationContext(),""+bm,Toast.LENGTH_LONG).show();
        img_showProfile.setImageBitmap(bm);
        Bitmap immagex=bm;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] b = baos.toByteArray();
        temp = Base64.encodeToString(b,Base64.DEFAULT);
    }

}

