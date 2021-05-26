package com.example.duan1_coffee.fragment.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.order.OrderCompleteAdapter;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.chat_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.OrderState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderCompleteFragment extends Fragment {
    Toolbar toolbar;


    DatabaseReference referenceOrderState;
    DatabaseReference referenceOrderDetail;
    RecyclerView recyclerView;
    List<OrderState> orderStateList;
    OrderCompleteAdapter adapter;
    public OrderCompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_complete, container, false);
//
//        toolbar =view.findViewById(R.id.toolbarMess);
//
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });


        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        referenceOrderState = FirebaseDatabase.getInstance().getReference("OrderState");
        referenceOrderDetail = FirebaseDatabase.getInstance().getReference("OrderDetail");

        recyclerView = view.findViewById(R.id.rcv);

        orderStateList = new ArrayList<>();
        adapter = new OrderCompleteAdapter(getActivity(), orderStateList);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        referenceOrderState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderStateList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if(postSnapshot.child("email").getValue().equals(email)){
                        OrderState orderState = postSnapshot.getValue(OrderState.class);
                        assert orderState != null;
                        orderStateList.add(orderState);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.tvBackToShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Move to Shop!", Toast.LENGTH_SHORT).show();
                new Function().loadFragment(new Home_Fragment(), R.id.order_complete_change, view);


            }
        });
//
//        view.findViewById(R.id.tvChatWithUs).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Move to Chat!", Toast.LENGTH_SHORT).show();
//                new Function().loadFragment(new chat_Fragment(), R.id.order_complete_change, view);
//            }
//        });

        return view;
    }
}