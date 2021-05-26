package com.example.duan1_coffee.fragment.wishlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.WishListAdapter;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.chat_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Product;
import com.example.duan1_coffee.model.WishList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class WishListFragment extends Fragment {
    RecyclerView recyclerView;
    WishListAdapter adapter;
    DatabaseReference referenceWishList;
    List<WishList> wishLists;
    String uid;
    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        uid = FirebaseAuth.getInstance().getUid();
        recyclerView = view.findViewById(R.id.rcv);
        wishLists = new ArrayList<>();
        adapter = new WishListAdapter(getActivity(), wishLists);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        referenceWishList = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Wishlist");

        referenceWishList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wishLists.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    WishList wishList = postSnapshot.getValue(WishList.class);
                    wishLists.add(wishList);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.tvBackToShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Move to Shop!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new Home_Fragment(), R.id.order_complete_change, view);


            }
        });

        view.findViewById(R.id.tvChatWithUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Move to Chat!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new chat_Fragment(), R.id.order_complete_change, view);
            }
        });

        return view;
    }
}