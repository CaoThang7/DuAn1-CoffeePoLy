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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.edit.EditProduct_Fragment;
import com.example.duan1_coffee.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<Product> products;

    public ProductListAdapter(Context context, List<Product> products) {
        this.mContext = context;
        this.products = products;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item, parent, false);
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
        holder.imageBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Edit click!", Toast.LENGTH_SHORT).show();
                Fragment fragment = EditProduct_Fragment.newInstance(
                        product.getProductId(),
                        product.getProductName(),
                        product.getProductType(),
                        product.getPrice(),
                        product.getImage());
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_product_list_change, fragment)
                        .commit();
            }
        });

        holder.imageBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
                final String selectedKey = product.getProductId();
                StorageReference imageRef = firebaseStorage.getReferenceFromUrl(product.getImage());
                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabaseRef.child(selectedKey).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(mContext, "Delete Item Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(mContext, "Delete Item Fail!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(mContext, "Delete Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Delete Fail!", Toast.LENGTH_SHORT).show();
                    }
                });
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
        ImageButton imageBtnEdit, imageBtnDelete;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            imageBtnEdit = (ImageButton) itemView.findViewById(R.id.imageBtnEdit);
            imageBtnDelete = (ImageButton) itemView.findViewById(R.id.imageBtnDelete);
        }
    }


}
