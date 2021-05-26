package com.example.duan1_coffee.adapter.analysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.Cart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnalysisTimeAdapter extends RecyclerView.Adapter<AnalysisTimeAdapter.MyViewHolder> {

    public Context context;
    public List<Cart> carts;
    String uid;

    public AnalysisTimeAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Cart cart = carts.get(position);
        holder.tvProductName.setText(cart.getProductName());
        holder.tvProductAmount.setText(String.valueOf(cart.getProductAmount()));
        holder.tvTotal.setText(String.valueOf(cart.getTotal()));
        Picasso.get()
                .load(cart.getImgProduct())
                .placeholder(R.drawable.ic_product_iamge)
                .into(holder.imgProduct);

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = FirebaseAuth.getInstance().getUid();
                DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Cart");
                String selectedKey = cart.getCartId();
                mDatabaseRef.child(selectedKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvProductAmount, tvTotal;
        ImageView imgProduct;
        ImageButton imgDelete;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductAmount = (TextView) itemView.findViewById(R.id.tvProductAmount);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            imgDelete = (ImageButton) itemView.findViewById(R.id.imgDelete);
        }
    }
}
