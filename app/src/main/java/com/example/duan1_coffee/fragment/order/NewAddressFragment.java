package com.example.duan1_coffee.fragment.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.edit.EditProduct_Fragment;
import com.example.duan1_coffee.function.Function;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NewAddressFragment extends Fragment {
    private String uid;

    public NewAddressFragment() {
        // Required empty public constructor
    }

    public NewAddressFragment(String uid) {
        this.uid = uid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_address, container, false);
        // ANH XA
        final EditText edtUsername = view.findViewById(R.id.edtUsername);
        final EditText edtAddress = view.findViewById(R.id.edtAddress);
        final EditText edtPhone = view.findViewById(R.id.edtPhone);
        final EditText edtEmail = view.findViewById(R.id.edtEmail);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtUsername.setText((String) snapshot.child("fullname").getValue());
                edtPhone.setText((String) snapshot.child("phone").getValue());
                edtEmail.setText((String) snapshot.child("email").getValue());
                edtAddress.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        view.findViewById(R.id.tvSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Move To Order!", Toast.LENGTH_SHORT).show();
                String username = edtUsername.getText().toString();
                String address = edtAddress.getText().toString();
                String phone = edtPhone.getText().toString();
                String email = edtEmail.getText().toString();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(email)){
                    Fragment fragment = OrderConfirmFragment.newInstance(username, address, phone, email);
                    ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.new_address_change, fragment)
                            .commit();
                }else{
                    Toast.makeText(getActivity(), "Field not empty!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}