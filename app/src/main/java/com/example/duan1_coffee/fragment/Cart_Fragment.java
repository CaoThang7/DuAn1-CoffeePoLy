package com.example.duan1_coffee.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.CartAdapter;
import com.example.duan1_coffee.fragment.order.NewAddressFragment;
import com.example.duan1_coffee.fragment.wishlist.WishListFragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Cart_Fragment  extends Fragment {
    TextView tvBackToShop;
    TextView tvTotalPrice;
    Button btnCheckOut;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<Cart> carts;
    DatabaseReference reference;
    DatabaseReference referenceCart;
    ValueEventListener mDBListener;
    String uid;
    float sum;
//    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        toolbar =view.findViewById(R.id.toolbarMess);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).setTitle("");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
        tvBackToShop=view.findViewById(R.id.tvBackToShop);
        recyclerView = view.findViewById(R.id.rcvCart);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnCheckOut = view.findViewById(R.id.btnCheckOut);


        tvBackToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Home_Fragment llf = new Home_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        carts = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), carts);
        recyclerView.setAdapter(cartAdapter);

        uid = FirebaseAuth.getInstance().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Cart");
        referenceCart = FirebaseDatabase.getInstance().getReference("Users").child(uid);


        mDBListener = reference.addValueEventListener(new ValueEventListener() {
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
                cartAdapter.notifyDataSetChanged();
                tvTotalPrice.setText("Total Price: " + sum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                referenceCart.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("Cart")) {
                            Toast.makeText(getActivity(), "Move To New Address!", Toast.LENGTH_SHORT).show();
                            new Function().loadFragment(new NewAddressFragment(uid), R.id.cart_layout_change, view);
                        }else {
                            Toast.makeText(getActivity(), "Please Select Products", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reference.removeEventListener(mDBListener);
    }

}
