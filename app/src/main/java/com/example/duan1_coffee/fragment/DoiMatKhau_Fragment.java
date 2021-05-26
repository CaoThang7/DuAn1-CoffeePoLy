package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.MainActivity;
import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class DoiMatKhau_Fragment extends androidx.fragment.app.Fragment {
    TextInputEditText oldpass,newpass,xacnhannewpass;
    Button btnUpdatePass;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doimk, container, false);


//        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        //Anh xa
        oldpass=view.findViewById(R.id.oldpass);
        newpass=view.findViewById(R.id.newpass);
        xacnhannewpass=view.findViewById(R.id.xacnhannewpass);
        btnUpdatePass=view.findViewById(R.id.btnUpdatePass);

        btnUpdatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Oldpass = oldpass.getText().toString();
                final String Newpass = newpass.getText().toString();
                final String XacNhanPass = xacnhannewpass.getText().toString();


                String id=firebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);

                        //Check nhap dung pass cu
                        if(!user.getPassword().equals(Oldpass)){
                            oldpass.setError("Sai mật khẩu cũ");
                            oldpass.requestFocus();
                        }
                        if(Oldpass.isEmpty()){
                            oldpass.setError("Bạn chưa nhập mật khẩu cũ ");
                            oldpass.requestFocus();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                //neu mat khau<6 ki tu thi bao loi
                if(Newpass.length()<6){
                    newpass.setError("Mật khẩu >6 ký tự");
                    newpass.requestFocus();
                }

                //Check nguoi dung chua nhap gi ca vao TextInput EditText
                if(Newpass.isEmpty()){
                    newpass.setError("Bạn chưa nhập mật khẩu mới");
                    newpass.requestFocus();
                }
                //Check xac nhan pass phai trung voi newpass
                if(XacNhanPass.length() != Newpass.length()|| XacNhanPass.length()<6 || Newpass.length()<6){
                    xacnhannewpass.setError("Xác nhận phải trùng mới mật khẩu mới");
                    xacnhannewpass.requestFocus();
                }else {
                    firebaseUser.updatePassword(XacNhanPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "The password updated.", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }else {
                                        Toast.makeText(getContext(), "The password Fail.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                    User user = new User(XacNhanPass);
                    Update(user);
                }
                if(XacNhanPass.isEmpty()){
                    xacnhannewpass.setError("Nhập lại mật khẩu mới");
                    xacnhannewpass.requestFocus();
                }


            }
        });



        return view;
    }

    private void Update (User user){

        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(id).updateChildren(user.userUpdate2());
    }


}
