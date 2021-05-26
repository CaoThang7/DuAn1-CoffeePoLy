package com.example.duan1_coffee.fragment.bottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Cart;
import com.example.duan1_coffee.model.WishList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    DatabaseReference mDatabaseRef;
    String productId, productName, productImg;
    float productPrice;
    int amount;
    ImageView imgProduct, wishlist_heart;
    TextView tvProductName, tvProductPrice, tvProductAmount, tvTotal;
    int count = 1;
    String uid;
    String email;
    public int status = 0;
    DatabaseReference referenceWishList;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public BottomSheetFragment(String productId, String productName, String productImg, float productPrice, int amount) {
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.productPrice = productPrice;
        this.amount = amount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ANH XA
        imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        tvProductName = (TextView) view.findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) view.findViewById(R.id.tvProductPrice);
        tvProductAmount = (TextView) view.findViewById(R.id.tvProductAmount);
        wishlist_heart = (ImageView) view.findViewById(R.id.wishlist_heart);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        // ANH XA BUTTON VA SET CLICK
        view.findViewById(R.id.btnDecrease).setOnClickListener(this);
        view.findViewById(R.id.btnIncrease).setOnClickListener(this);
        view.findViewById(R.id.wishlist_heart).setOnClickListener(this);
        view.findViewById(R.id.btnAddToCart).setOnClickListener(this);

        // ANH XA FIREBASE
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

    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.btnDecrease:
                setBtnDecrease();
                break;
            case R.id.btnIncrease:
                setBtnIncrease();
                break;
            case R.id.wishlist_heart:
                String diffId = referenceWishList.push().getKey();
                //on button clicked
                if(status == 0)
                {
                    status = 1;
                    wishlist_heart.setImageResource(R.drawable.ic_wishlist_heart_red);
                    WishList wishList = new WishList(diffId, email, productId, productName, productImg, productPrice, true);
                    referenceWishList.child(diffId).setValue(wishList);
                }
                //off button clicked
                else if(status == 1)
                {
                    //logic here
                    status = 0;
                    wishlist_heart.setImageResource(R.drawable.ic_heart_no_wish);
                    referenceWishList.child(diffId).removeValue();
                }
                break;
            case R.id.btnAddToCart:
                setBtnAddToCart();
                break;
        }
    }

    public void setBtnDecrease(){
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
    public void setBtnIncrease(){
        float sum = 0;
        count++;
        sum += productPrice*count;
        tvProductAmount.setText("" + count);
        tvTotal.setText("" + sum);
    }
    public void setBtnWishlist(){

    }
    public void setBtnAddToCart(){
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
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Add " + getProductName + "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}