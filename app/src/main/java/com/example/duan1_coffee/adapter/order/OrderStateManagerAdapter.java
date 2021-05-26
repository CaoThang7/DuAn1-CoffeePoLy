package com.example.duan1_coffee.adapter.order;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.Bill;
import com.example.duan1_coffee.model.BillDetail;
import com.example.duan1_coffee.model.OrderDetail;
import com.example.duan1_coffee.model.OrderState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderStateManagerAdapter extends RecyclerView.Adapter<OrderStateManagerAdapter.MyViewHolder> {

    public Context context;
    public List<OrderState> orderStateList;
    public List<OrderDetail> orderDetailList;
    DatabaseReference referenceOrderState;
    DatabaseReference referenceOrderDetail;
    DatabaseReference referenceBill;
    DatabaseReference referenceBillDetail;

    public OrderStateManagerAdapter(Context context, List<OrderState> orderStateList) {
        this.context = context;
        this.orderStateList = orderStateList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_order_state_manager, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        orderDetailList = new ArrayList<>();
        referenceOrderState = FirebaseDatabase.getInstance().getReference("OrderState");
        referenceOrderDetail = FirebaseDatabase.getInstance().getReference("OrderDetail");
        referenceBill = FirebaseDatabase.getInstance().getReference("Bill");
        referenceBillDetail = FirebaseDatabase.getInstance().getReference("BillDetail");
        final OrderState orderState = orderStateList.get(position);

        holder.tvDate.setText(orderState.getDate());
        holder.tvUsername.setText(orderState.getEmail());
        if(!orderState.isState()){
            holder.tvState.setText("Pending");
        }else {
            holder.tvState.setText("Done");
            holder.tvState.setTextColor(Color.parseColor("#A5D6A7"));
        }
        holder.tvState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceOrderState.child(orderState.getId()).child("state").setValue(true)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            orderState.setState(true);
                            // Set Click change state to Done
                            referenceOrderDetail.child(orderState.getId()).child("state").setValue(true);

                            // Set Bill Detail
                            referenceOrderDetail.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                        OrderDetail orderDetail = postSnapshot.getValue(OrderDetail.class);
                                        assert orderDetail != null;

                                        BillDetail billDetail = new BillDetail(
                                                orderDetail.getId(), orderDetail.getDate(), orderDetail.isState(), orderDetail.getUsername(),
                                                orderDetail.getAddress(), orderDetail.getPhone(), orderDetail.getEmail(), orderDetail.getTotal(), orderDetail.getCarts());

                                        referenceBillDetail.child(orderDetail.getId()).setValue(billDetail);
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            // Set Bill
                            Bill bill = new Bill(orderState.getId(), orderState.getEmail(), orderState.getDate(), orderState.isState());
                            referenceBill.child(orderState.getId()).setValue(bill);

                            Toast.makeText(context, "Update state success!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update state fail!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderStateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate, tvState, tvUsername;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvState = (TextView) itemView.findViewById(R.id.tvState);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        }
    }
}
