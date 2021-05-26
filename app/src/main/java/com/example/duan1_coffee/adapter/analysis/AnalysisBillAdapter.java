package com.example.duan1_coffee.adapter.analysis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.bill.BillDetailFragment;
import com.example.duan1_coffee.model.Bill;
import com.example.duan1_coffee.model.OrderState;

import java.text.SimpleDateFormat;
import java.util.List;

public class AnalysisBillAdapter extends RecyclerView.Adapter<AnalysisBillAdapter.MyViewHolder> {

    public Context context;
    public List<Bill> bills;

    public AnalysisBillAdapter(Context context, List<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.analysis_bill_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Bill bill = bills.get(position);
        holder.tvDate.setText(bill.getDate());
        holder.tvEmail.setText(bill.getUsername());
        holder.tvBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = BillDetailFragment.newInstance(bill.getId(), bill.getDate());
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvEmail, tvDate, tvBill;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvBill = (TextView) itemView.findViewById(R.id.tvBill);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);

        }
    }
}
