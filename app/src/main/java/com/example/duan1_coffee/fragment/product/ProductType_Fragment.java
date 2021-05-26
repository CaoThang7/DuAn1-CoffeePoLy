package com.example.duan1_coffee.fragment.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;

import com.example.duan1_coffee.adapter.product.ProductTypeAdapter;
import com.example.duan1_coffee.fragment.add.AddType_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.ProductType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ProductType_Fragment extends Fragment {
    RecyclerView mRecyclerView;
    List<ProductType> types;
    ProductTypeAdapter productTypeAdapter;
    ProgressBar mProgressCircle;
    DatabaseReference mDatabaseRef;
    ValueEventListener mDBListener;
    FirebaseStorage firebaseStorage;
    FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        mProgressCircle = view.findViewById(R.id.progress_circle);
        mRecyclerView = view.findViewById(R.id.rcv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        types = new ArrayList<>();
        productTypeAdapter = new ProductTypeAdapter(getActivity(), types);
        mRecyclerView.setAdapter(productTypeAdapter);


        firebaseStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Types");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                types.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductType type = postSnapshot.getValue(ProductType.class);
                    assert type != null;
                    type.setTypeId(postSnapshot.getKey());
                    types.add(type);
                }
                productTypeAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AddType_Fragment(), R.id.fragment_product_type_change, view);
            }
        });
    }
}
