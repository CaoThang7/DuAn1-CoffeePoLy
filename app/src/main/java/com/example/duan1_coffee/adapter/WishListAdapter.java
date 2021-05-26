package com.example.duan1_coffee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.Cart;
import com.example.duan1_coffee.model.WishList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    public Context context;
    public List<WishList> wishLists;
    String uid;

    public WishListAdapter(Context context, List<WishList> wishLists) {
        this.context = context;
        this.wishLists = wishLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final WishList wishList = wishLists.get(position);
        holder.tvProductName.setText(wishList.getProductName());
        holder.tvTotal.setText(String.valueOf(wishList.getTotal()));
        Picasso.get()
                .load(wishList.getProductImg())
                .placeholder(R.drawable.ic_product_iamge)
                .into(holder.imgProduct);

        holder.imgHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = FirebaseAuth.getInstance().getUid();
                Toast.makeText(context, "Remove Wishlist", Toast.LENGTH_SHORT).show();
                DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Wishlist");
                String selectedKey = wishList.getId();
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
        return wishLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvTotal;
        ImageView imgProduct;
        ImageButton imgHeart;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvTotal = (TextView) itemView.findViewById(R.id.tvTotal);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            imgHeart = (ImageButton) itemView.findViewById(R.id.imgHeart);
        }
    }
}
