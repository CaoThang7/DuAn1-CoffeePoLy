package com.example.duan1_coffee.fragment.bill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.CartAdapter;
import com.example.duan1_coffee.adapter.bill.BillDetailAdapter;
import com.example.duan1_coffee.adapter.order.OrderCompleteAdapter;
import com.example.duan1_coffee.adapter.order.OrderConfirmAdapter;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.chat_Fragment;
import com.example.duan1_coffee.fragment.order.OrderConfirmFragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.BillDetail;
import com.example.duan1_coffee.model.Cart;
import com.example.duan1_coffee.model.OrderState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BillDetailFragment extends Fragment {
    DatabaseReference referenceBill;
    DatabaseReference referenceBillDetail;
    DatabaseReference referenceBillDetailCart;
    RecyclerView recyclerView;
    List<Cart> carts;
    OrderConfirmAdapter adapter;
    // KHAI BAO
    private static final String ARGUMENT_BILL_DETAIL_ID = "BILL_DETAIL_ID";
    private static final String ARGUMENT_BILL_DETAIL_DATE = "BILL_DETAIL_DATE";

    public BillDetailFragment() {
        // Required empty public constructor
    }


    public static BillDetailFragment newInstance(String id, String date) {
        Bundle args = new Bundle();
        // Save data here
        args.putString(ARGUMENT_BILL_DETAIL_ID, id);
        args.putString(ARGUMENT_BILL_DETAIL_DATE, String.valueOf(date));
        BillDetailFragment fragment = new BillDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bill, container, false);
        final String id = getArguments().getString(ARGUMENT_BILL_DETAIL_ID);
        final TextView tvUsername = view.findViewById(R.id.tvUsername);
        final TextView tvAddress = view.findViewById(R.id.tvAddress);
        final TextView tvPhone = view.findViewById(R.id.tvPhone);
        final TextView tvEmail = view.findViewById(R.id.tvEmail);
        final TextView tvDate = view.findViewById(R.id.tvDate);
        final TextView tvTotal = view.findViewById(R.id.tvTotal);

        referenceBill = FirebaseDatabase.getInstance().getReference("Bill");
        referenceBillDetail = FirebaseDatabase.getInstance().getReference("BillDetail");
        referenceBillDetailCart = FirebaseDatabase.getInstance().getReference("BillDetail").child(id).child("carts");


        recyclerView = view.findViewById(R.id.rcv);

        carts = new ArrayList<>();
        adapter = new OrderConfirmAdapter(getActivity(), carts);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        referenceBillDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BillDetail billDetail = postSnapshot.getValue(BillDetail.class);
                    String id = billDetail.getId();
                    tvUsername.setText(billDetail.getUsername());
                    tvAddress.setText(billDetail.getAddress());
                    tvEmail.setText(billDetail.getEmail());
                    tvPhone.setText(billDetail.getPhone());
                    tvTotal.setText(String.valueOf(billDetail.getTotal()));
                    tvDate.setText(billDetail.getDate());
                    assert billDetail != null;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        referenceBillDetailCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carts.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cart cart = postSnapshot.getValue(Cart.class);
                    carts.add(cart);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}