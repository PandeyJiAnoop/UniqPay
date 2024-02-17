package com.akp.uniqpay.Walet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akp.uniqpay.R;

import java.util.HashMap;
import java.util.List;



public class ApprovedAdapter extends RecyclerView.Adapter<ApprovedAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;
    public ApprovedAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public ApprovedAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_wallet_approved, viewGroup, false);
        ApprovedAdapter.VH viewHolder = new ApprovedAdapter.VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedAdapter.VH vh, int i) {
        vh.member_id_tv.setText(arrayList.get(i).get("MemberId"));
        vh.member_name_tv.setText(arrayList.get(i).get("MemberName"));
        vh.current_wallet_tv.setText(arrayList.get(i).get("CurrentWallet")+ " \u20B9");
        vh.date_tv.setText(arrayList.get(i).get("Date"));
        vh.req_wallet_tv.setText(arrayList.get(i).get("ReqWallet"));
        if (arrayList.get(i).get("Remark").equalsIgnoreCase("")){
            vh.remark_tv.setText("N/A");
        }
        else {
            vh.remark_tv.setText(arrayList.get(i).get("Remark"));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class VH extends RecyclerView.ViewHolder {
        TextView member_id_tv,member_name_tv,current_wallet_tv,date_tv,req_wallet_tv,remark_tv;

        public VH(@NonNull View itemView) {
            super(itemView);
            member_id_tv = itemView.findViewById(R.id.member_id_tv);
            member_name_tv = itemView.findViewById(R.id.member_name_tv);

            current_wallet_tv = itemView.findViewById(R.id.current_wallet_tv);
            date_tv = itemView.findViewById(R.id.date_tv);

            req_wallet_tv = itemView.findViewById(R.id.req_wallet_tv);
            remark_tv = itemView.findViewById(R.id.remark_tv);

        }
    }
}


