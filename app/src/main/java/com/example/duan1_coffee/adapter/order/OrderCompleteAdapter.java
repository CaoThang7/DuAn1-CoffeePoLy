package com.example.duan1_coffee.adapter.order;

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
import com.example.duan1_coffee.fragment.order.OrderConfirmFragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.OrderState;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderCompleteAdapter extends RecyclerView.Adapter<OrderCompleteAdapter.MyViewHolder> {

    public Context context;
    public List<OrderState> orderStateList;

    public OrderCompleteAdapter(Context context, List<OrderState> orderStateList) {
        this.context = context;
        this.orderStateList = orderStateList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_order_complete, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final OrderState orderState = orderStateList.get(position);
        holder.tvDate.setText(orderState.getDate());
        if(!orderState.isState()){
            holder.tvState.setText("Pending");
            holder.billLabel.setVisibility(View.GONE);
        }else {
            holder.tvState.setText("Done");
            holder.tvState.setTextColor(Color.parseColor("#A5D6A7"));
            holder.billLabel.setVisibility(View.VISIBLE);
            holder.tvBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = BillDetailFragment.newInstance(orderState.getId(), orderState.getDate());
                    ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.order_complete_change, fragment)
                            .commit();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return orderStateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate, tvState, tvBill;
        LinearLayout billLabel;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvState = (TextView) itemView.findViewById(R.id.tvState);
            tvBill = (TextView) itemView.findViewById(R.id.tvBill);
            billLabel = (LinearLayout) itemView.findViewById(R.id.billLabel);

        }
    }
}
