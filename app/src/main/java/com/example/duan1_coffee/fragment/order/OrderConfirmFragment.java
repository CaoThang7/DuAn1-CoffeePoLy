package com.example.duan1_coffee.fragment.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.order.OrderConfirmAdapter;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Cart;
import com.example.duan1_coffee.model.OrderDetail;
import com.example.duan1_coffee.model.OrderState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class OrderConfirmFragment extends Fragment {
    RecyclerView recyclerView;
    OrderConfirmAdapter adapter;
    List<Cart> carts;
    DatabaseReference referenceCart;
    DatabaseReference referenceOrderState;
    DatabaseReference referenceOrderDetail;
    String uid;
    float sum;
    // KHAI BAO
    private static final String ARGUMENT_USERNAME = "USERNAME";
    private static final String ARGUMENT_ADDRESS = "ADDRESS";
    private static final String ARGUMENT_PHONE = "PHONE";
    private static final String ARGUMENT_EMAIL = "EMAIL";

    public OrderConfirmFragment() {
        // Required empty public constructor
    }

    public static OrderConfirmFragment newInstance(String username, String address, String phone, String email) {
        Bundle args = new Bundle();
        // Save data here
        args.putString(ARGUMENT_USERNAME, username);
        args.putString(ARGUMENT_ADDRESS, address);
        args.putString(ARGUMENT_PHONE, phone);
        args.putString(ARGUMENT_EMAIL, email);
        OrderConfirmFragment fragment = new OrderConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order_comfirm, container, false);

        // ANH XA
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        final TextView tvTotal = view.findViewById(R.id.tvTotal);
        recyclerView = view.findViewById(R.id.rcv);

        carts = new ArrayList<>();
        adapter = new OrderConfirmAdapter(getActivity(), carts);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        uid = FirebaseAuth.getInstance().getUid();
        referenceCart = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Cart");
        referenceOrderState = FirebaseDatabase.getInstance().getReference("OrderState");
        referenceOrderDetail = FirebaseDatabase.getInstance().getReference("OrderDetail");


        final String username = getArguments().getString(ARGUMENT_USERNAME);
        final String address = getArguments().getString(ARGUMENT_ADDRESS);
        final String phone = getArguments().getString(ARGUMENT_PHONE);
        final String email = getArguments().getString(ARGUMENT_EMAIL);

        // Set Text
        tvUsername.setText(username);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvEmail.setText(email);

        referenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sum = 0;
                carts.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cart cart = postSnapshot.getValue(Cart.class);
                    assert cart != null;
                    sum += cart.getTotal();
                    cart.setCartId(postSnapshot.getKey());
                    carts.add(cart);
                }
                adapter.notifyDataSetChanged();
                tvTotal.setText("" + sum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.tvConfirmOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = referenceOrderState.push().getKey();
                final Date dateNow = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                final String date = format.format(dateNow);
                OrderState orderState = new OrderState(id, date, false, email);
                referenceOrderState.child(id).setValue(orderState)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            OrderDetail orderDetail = new OrderDetail(id, date, false, username, address, phone, email, sum, carts);
                            referenceOrderDetail.child(id).setValue(orderDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Order Success!", Toast.LENGTH_SHORT).show();
                                    referenceCart.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Delete Cart Success!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(), "Delete Cart Fail!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    new Function().loadFragment(new OrderCompleteFragment(), R.id.order_confirm_change, view);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
        });

        return view;
    }
}