package com.example.duan1_coffee.adapter.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.product.ProductDetailOnMenuFragment;
import com.example.duan1_coffee.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRcvAdapter extends RecyclerView.Adapter<HomeRcvAdapter.MyViewHolder>  {

    Context context;
    List<Product> products;

    public HomeRcvAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_item_horizontal, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tvProductName.setText(products.get(position).getProductName());
        holder.tvProductPrice.setText(String.valueOf(products.get(position).getPrice()));
        Picasso.get()
                .load(products.get(position).getImage())
                .placeholder(R.drawable.ic_product_iamge)
                .fit()
                .centerCrop()
                .into(holder.imgProduct);
        holder.item_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = products.get(position).getProductId();
                String productName = products.get(position).getProductName();
                String productImg = products.get(position).getImage();
                float productPrice = products.get(position).getPrice();
                int productAmount = products.get(position).getAmount();
                Fragment fragment = ProductDetailOnMenuFragment.newInstance(productId, productName, productImg, productPrice, productAmount);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_layout_change, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvProductPrice;
        ImageView imgProduct;
        CardView item_select;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            item_select = (CardView) itemView.findViewById(R.id.item_select);
        }
    }

}
