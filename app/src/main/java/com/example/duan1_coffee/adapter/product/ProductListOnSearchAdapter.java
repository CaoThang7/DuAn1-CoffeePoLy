package com.example.duan1_coffee.adapter.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.edit.EditProduct_Fragment;
import com.example.duan1_coffee.fragment.product.ProductDetailOnMenuFragment;
import com.example.duan1_coffee.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListOnSearchAdapter extends RecyclerView.Adapter<ProductListOnSearchAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Product> products;

    public ProductListOnSearchAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item_on_search, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.tvProductName.setText(product.getProductName());
        holder.tvProductPrice.setText(String.valueOf(product.getPrice()));
        Picasso.get()
                .load(product.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imgProduct);
        holder.product_on_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "" + product.getProductName(), Toast.LENGTH_SHORT).show();
                String productId = products.get(position).getProductId();
                String productName = products.get(position).getProductName();
                String productImg = products.get(position).getImage();
                float productPrice = products.get(position).getPrice();
                int productAmount = products.get(position).getAmount();
                Fragment fragment = ProductDetailOnMenuFragment.newInstance(productId, productName, productImg, productPrice, productAmount);
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice;
        ImageView imgProduct;
        CardView product_on_search;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            product_on_search = (CardView) itemView.findViewById(R.id.product_on_search);
        }
    }


}
