package com.akp.uniqpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPlanList extends AppCompatActivity {
    String Getprovider;
TextView title_tv;
ImageView ivBack;
RecyclerView cust_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan_list);
        Getprovider=getIntent().getStringExtra("a_provider");
        FindId();
    }

    private void FindId() {
        cust_recyclerView=findViewById(R.id.cust_recyclerView);
        title_tv=findViewById(R.id.title_tv);
        ivBack=findViewById(R.id.ivBack);
        title_tv.setText(Getprovider);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}