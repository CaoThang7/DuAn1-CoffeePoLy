package com.example.duan1_coffee.adapter.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderConfirmAdapter extends RecyclerView.Adapter<OrderConfirmAdapter.MyViewHolder> {

    public Context context;
    public List<Cart> carts;

    public OrderConfirmAdapter(Context context, List<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_order_confirm, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvProductAmount, tvTotal;
        ImageView imgProduct;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductAmount = (TextView) itemView.findViewById(R.id.tvProductAmount);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
        }
    }
}
