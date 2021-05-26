package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.SearchActivity;
import com.example.duan1_coffee.adapter.product.HomeAdapter;
import com.example.duan1_coffee.fragment.order.OrderCompleteFragment;
import com.example.duan1_coffee.fragment.wishlist.WishListFragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.ProductType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Home_Fragment extends androidx.fragment.app.Fragment {
    RecyclerView mRecyclerView;
    List<ProductType> types;
    FirebaseStorage firebaseStorage;
    DatabaseReference mDatabaseRef;
    HomeAdapter homeAdapter;
    ValueEventListener mDBListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ANH XA
        mRecyclerView = view.findViewById(R.id.my_rcv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // ANH XA DATA & ADAPTER
        types = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), types);
        mRecyclerView.setAdapter(homeAdapter);

        // ANH XA FIREBASE
        firebaseStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Types");

        // ANH XA CART & SET CLICK
        view.findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new Cart_Fragment(), R.id.home_layout_change, view);
            }
        });

        view.findViewById(R.id.order_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new OrderCompleteFragment(), R.id.home_layout_change, view);
            }
        });

        view.findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Move to Wish List!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new WishListFragment(), R.id.home_layout_change, view);
            }
        });

        view.findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });



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
                homeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
