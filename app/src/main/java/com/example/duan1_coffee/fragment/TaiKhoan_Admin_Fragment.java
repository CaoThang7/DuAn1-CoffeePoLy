package com.example.duan1_coffee.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duan1_coffee.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class TaiKhoan_Admin_Fragment extends androidx.fragment.app.Fragment {
    TextInputEditText fullName,name,email,phone,pass;
    Button btnUpdateUser;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tk_admin, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        //Anh xa
//        email = view.findViewById(R.id.emailUser);
//        name = view.findViewById(R.id.nameAdmin);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
