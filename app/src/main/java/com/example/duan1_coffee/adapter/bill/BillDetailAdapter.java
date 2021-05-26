package com.example.duan1_coffee.adapter.bill;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.BillDetail;
import com.example.duan1_coffee.model.Cart;
import com.example.duan1_coffee.model.OrderState;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.List;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.MyViewHolder> {

    public Context context;
    public List<BillDetail> billDetailList;
    public List<Cart> carts;

    public BillDetailAdapter(Context context, List<BillDetail> billDetailList) {
        this.context = context;
        this.billDetailList = billDetailList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.bill_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BillDetail billDetail = billDetailList.get(position);
        carts = billDetail.getCarts();
        holder.tvProductName.setText(carts.get(position).getProductName());
        holder.tvProductAmount.setText(String.valueOf(carts.get(position).getProductAmount()));
        holder.tvTotal.setText(String.valueOf(carts.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return billDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvProductAmount, tvTotal;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductAmount = (TextView) itemView.findViewById(R.id.tvProductAmount);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
        }
    }
}
