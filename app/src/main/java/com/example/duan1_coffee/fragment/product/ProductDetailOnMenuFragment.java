package com.example.duan1_coffee.fragment.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.Cart_Fragment;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.review.WriteReviewFragment;
import com.example.duan1_coffee.fragment.wishlist.WishListFragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Cart;
import com.example.duan1_coffee.model.WishList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductDetailOnMenuFragment extends Fragment {
    DatabaseReference mDatabaseRef;
    ImageView imgProduct, wishlist_heart;
    TextView tvProductName, tvProductPrice, tvProductAmount, tvTotal;
    int count = 1;
    String uid;
    String email;
    public int status = 0;
    DatabaseReference referenceWishList;

    private static final String PRODUCT_ID = "PRODUCT_ID";
    private static final String PRODUCT_NAME = "PRODUCT_NAME";
    private static final String PRODUCT_IMG = "PRODUCT_IMG";
    private static final String PRODUCT_PRICE = "PRODUCT_PRICE";
    private static final String PRODUCT_AMOUNT = "PRODUCT_AMOUNT";


    public ProductDetailOnMenuFragment() {
        // Required empty public constructor
    }

    public static ProductDetailOnMenuFragment newInstance(String productId, String productName, String productImg, float productPrice, int amount) {
        Bundle args = new Bundle();
        // Save data here
        args.putString(PRODUCT_ID, productId);
        args.putString(PRODUCT_NAME, productName);
        args.putString(PRODUCT_IMG, productImg);
        args.putFloat(PRODUCT_PRICE, productPrice);
        args.putInt(PRODUCT_AMOUNT, amount);
        ProductDetailOnMenuFragment fragment = new ProductDetailOnMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail_on_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String productId = getArguments().getString(PRODUCT_ID);
        final String productName = getArguments().getString(PRODUCT_NAME);
        final String productImg = getArguments().getString(PRODUCT_IMG);
        final float productPrice = getArguments().getFloat(PRODUCT_PRICE);
        final int amount = getArguments().getInt(PRODUCT_AMOUNT);

        // ANH XA
        imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
        tvProductAmount = (TextView) view.findViewById(R.id.tvProductAmount);
        wishlist_heart = (ImageView) view.findViewById(R.id.wishlist_heart);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);

        uid = FirebaseAuth.getInstance().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Cart");
        referenceWishList = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Wishlist");

        // GET CONTENT
        Picasso.get()
                .load(productImg)
                .placeholder(R.drawable.ic_product_iamge)
                .fit()
                .centerCrop()
                .into(imgProduct);

        tvProductName.setText(productName);
        tvProductPrice.setText("" + productPrice);
        tvProductAmount.setText("" + amount);
        tvTotal.setText("" + productPrice);

        //  SET CLICK
        view.findViewById(R.id.btnDecrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sum = 0;
                if(count == 1){
                    count = 1;
                }else {
                    count--;
                    sum += productPrice*count;
                    tvProductAmount.setText("" + count);
                    tvTotal.setText("" + sum);
                }
            }
        });
        view.findViewById(R.id.btnIncrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float sum = 0;
                count++;
                sum += productPrice*count;
                tvProductAmount.setText("" + count);
                tvTotal.setText("" + sum);
            }
        });
        view.findViewById(R.id.wishlist_heart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diffId = referenceWishList.push().getKey();
                if(status == 0)
                {
                    status = 1;
                    if(status == 1){
                        wishlist_heart.setImageResource(R.drawable.ic_wishlist_heart_red);
                        WishList wishList = new WishList(diffId, email, productId, productName, productImg, productPrice, true);
                        referenceWishList.child(diffId).setValue(wishList);
                        new Function().loadFragment(new WishListFragment(), R.id.product_on_menu_change, view);

                    }
                }
                else if(status == 1)
                {
                    //logic here
                    status = 0;
                    if(status == 0){
                        wishlist_heart.setImageResource(R.drawable.ic_heart_no_wish);
                        referenceWishList.child(diffId).removeValue();
                    }

                }
            }
        });
        view.findViewById(R.id.tvAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getProductName = productName;
                String getProductAmount = tvProductAmount.getText().toString();
                String getTotal = tvTotal.getText().toString();

                Cart cart = new Cart(getProductName, Integer.parseInt(getProductAmount), productImg, Float.parseFloat(getTotal));
                String diffId = mDatabaseRef.push().getKey();
                cart.setCartId(diffId);
                mDatabaseRef.child(diffId)
                        .setValue(cart)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Add " + getProductName + "To Cart!", Toast.LENGTH_SHORT).show();
                                new Function().loadFragment(new Cart_Fragment(), R.id.product_on_menu_change, view);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Add " + getProductName + "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new Home_Fragment(), R.id.product_on_menu_change, view);
            }
        });

        view.findViewById(R.id.write_review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = WriteReviewFragment.newInstance(productId, productName, productImg);
                ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.product_on_menu_change, fragment)
                        .commit();
            }
        });

    }


}