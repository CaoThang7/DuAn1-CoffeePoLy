package com.example.duan1_coffee.fragment.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.order.OrderStateManagerAdapter;
import com.example.duan1_coffee.fragment.AnalysisFragment;
import com.example.duan1_coffee.fragment.Analytic_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.OrderDetail;
import com.example.duan1_coffee.model.OrderState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrderSateManagerFragment extends Fragment {
    DatabaseReference referenceOrderState;
    DatabaseReference referenceOrderDetail;
    RecyclerView recyclerView;
    List<OrderState> orderStateList;
    OrderStateManagerAdapter adapter;
    public OrderSateManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_order_sate_manager, container, false);

        referenceOrderState = FirebaseDatabase.getInstance().getReference("OrderState");
        referenceOrderDetail = FirebaseDatabase.getInstance().getReference("OrderDetail");

        recyclerView = view.findViewById(R.id.rcv);

        orderStateList = new ArrayList<>();
        adapter = new OrderStateManagerAdapter(getActivity(), orderStateList);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        referenceOrderState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderStateList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    OrderState orderState = postSnapshot.getValue(OrderState.class);
                    assert orderState != null;
                    orderStateList.add(orderState);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.tvBackToAnalysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AnalysisFragment(), R.id.order_complete_change, view);
            }
        });

        view.findViewById(R.id.tvChatWithCustomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new Analytic_Fragment(), R.id.order_complete_change, view);
            }
        });


        return view;
    }
}