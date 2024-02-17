package com.akp.uniqpay.Adapterclass;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akp.uniqpay.R;

import java.util.HashMap;
import java.util.List;

public class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;
    public ReferralAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public ReferralAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_referal_list, viewGroup, false);
        ReferralAdapter.VH viewHolder = new ReferralAdapter.VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReferralAdapter.VH vh, int i) {
        vh.cust_id_tv.setText(arrayList.get(i).get("CustomerId"));
        vh.cust_name_tv.setText(arrayList.get(i).get("Name"));
        vh.reg_date_tv.setText(arrayList.get(i).get("RegDate"));
        vh.mob_tv.setText(arrayList.get(i).get("MobileNo"));
        vh.emailid_tv.setText(arrayList.get(i).get("EmailId"));

        if (arrayList.get(i).get("Status").equalsIgnoreCase("Active")){
            vh.status_tv.setText(arrayList.get(i).get("Status"));
            vh.status_tv.setTextColor(Color.GREEN);
        }
        else {
            vh.status_tv.setText(arrayList.get(i).get("Status"));
            vh.status_tv.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class VH extends RecyclerView.ViewHolder {
        TextView cust_id_tv,cust_name_tv,reg_date_tv, mob_tv,emailid_tv,status_tv;
        //        CircleImageView img_icon;
        public VH(@NonNull View itemView) {
            super(itemView);
            cust_id_tv = itemView.findViewById(R.id.cust_id_tv);
            cust_name_tv = itemView.findViewById(R.id.cust_name_tv);
            reg_date_tv = itemView.findViewById(R.id.reg_date_tv);
            mob_tv = itemView.findViewById(R.id.mob_tv);
            emailid_tv = itemView.findViewById(R.id.emailid_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
        }
    }
}
