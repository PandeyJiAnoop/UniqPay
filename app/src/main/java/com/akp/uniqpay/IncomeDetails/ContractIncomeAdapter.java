package com.akp.uniqpay.IncomeDetails;

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

public class ContractIncomeAdapter extends RecyclerView.Adapter<ContractIncomeAdapter.VH> {
    Context context;
    List<HashMap<String,String>> arrayList;

    public ContractIncomeAdapter(Context context, List<HashMap<String,String>> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public ContractIncomeAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_contactincome, viewGroup, false);
        ContractIncomeAdapter.VH viewHolder = new ContractIncomeAdapter.VH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContractIncomeAdapter.VH vh, int i) {
        vh.date_tv.setText(arrayList.get(i).get("Date"));
        vh.income_tv.setText(arrayList.get(i).get("Income"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView date_tv,income_tv;
        public VH(@NonNull View itemView) {
            super(itemView);
            date_tv = itemView.findViewById(R.id.date_tv);
            income_tv = itemView.findViewById(R.id.income_tv);
        }
    }
}


