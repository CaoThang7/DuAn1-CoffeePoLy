package com.example.duan1_coffee.adapter.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.Product;
import com.example.duan1_coffee.model.ProductType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");;
    Context context;
    List<ProductType> types;


    public HomeAdapter(Context context, List<ProductType> types) {
        this.context = context;
        this.types = types;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_group, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.item_title.setText(types.get(position).getTypeName());
        final List<Product> products = new ArrayList<>();
        mDatabaseRef.orderByChild("productType").equalTo(types.get(position).getTypeName()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Product product = postSnapshot.getValue(Product.class);
                        assert product != null;
                        product.productId = postSnapshot.getKey();
                        products.add(product);
                        setItemRecycler(holder.rcv_view_item_list, products);
                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView item_title;
        RecyclerView rcv_view_item_list;
        Button btnMore;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            rcv_view_item_list = (RecyclerView) itemView.findViewById(R.id.rcv_view_item_list);
        }
    }

    private void setItemRecycler(RecyclerView recyclerView, List<Product> products){
        HomeRcvAdapter homeRcvAdapter = new HomeRcvAdapter(context, products);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(homeRcvAdapter);
    }
}
