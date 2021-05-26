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

import com.example.duan1_coffee.fragment.edit.EditType_Fragment;
import com.example.duan1_coffee.model.ProductType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<ProductType> types;

    public ProductTypeAdapter(Context context, List<ProductType> types) {
        this.mContext = context;
        this.types = types;
    }

    @NonNull
    @Override
    public ProductTypeAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.type_item, parent, false);
        return new ProductTypeAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductTypeAdapter.RecyclerViewHolder holder, final int position) {
        final ProductType type = types.get(position);
        holder.tvProductTypeName.setText(type.getTypeName());
        Picasso.get()
                .load(type.getImageType())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imgProductType);
        holder.imageBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Edit click!", Toast.LENGTH_SHORT).show();
                Fragment fragment = EditType_Fragment.newInstance(
                        type.getTypeId(),
                        type.getTypeName(),
                        type.getImageType());
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_product_type_change, fragment)
                        .commit();
            }
        });

        holder.imageBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                final DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Types");
                final String selectedKey = type.getTypeId();
                StorageReference imageRef = firebaseStorage.getReferenceFromUrl(type.getImageType());
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
        return types.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductTypeName;
        ImageView imgProductType;

        ImageButton imageBtnEdit, imageBtnDelete;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvProductTypeName = (TextView) itemView.findViewById(R.id.tvProductTypeName);
            imgProductType = (ImageView) itemView.findViewById(R.id.imgProductType);
            imageBtnEdit = (ImageButton) itemView.findViewById(R.id.imageBtnEdit);
            imageBtnDelete = (ImageButton) itemView.findViewById(R.id.imageBtnDelete);
        }
    }
}