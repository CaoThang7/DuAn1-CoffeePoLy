package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.duan1_coffee.model.User;

import java.util.HashMap;

public class DangKiTKActivity extends AppCompatActivity {
    Button btnDangKi2;
    TextInputLayout tvFullName, tvUserName, tvEmail, tvPhone, tvPassword;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ImageView backinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_t_k);
        // Anh Xa
        tvFullName = findViewById(R.id.tvFullName);
        tvUserName = findViewById(R.id.tvUserName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvPassword = findViewById(R.id.tvPassword);
        btnDangKi2 = findViewById(R.id.btnDangKi2);
        backinfo=findViewById(R.id.backinfo);
        backinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DangKiTKActivity.this,LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.right_to_left,R.anim.slide_out);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        btnDangKi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get all the values
                String username = tvUserName.getEditText().getText().toString();
                String fullName = tvFullName.getEditText().getText().toString();
                String email = tvEmail.getEditText().getText().toString();
                String phone = tvPhone.getEditText().getText().toString();
                String password = tvPassword.getEditText().getText().toString();

                //Dang ki
                setBtnSignUp(username, fullName, email, phone, password);
            }
        });
    }

    //Goi dang ki
    public void setBtnSignUp(final String username, final String fullName, final String email, final String phone, final String password ){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            final String userid=firebaseUser.getUid();
                            reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String>hashMap=new HashMap<>();
                            hashMap.put("userId",userid);
                            hashMap.put("username",username);
                            hashMap.put("fullname",fullName);
                            hashMap.put("image","default");
                            hashMap.put("email",email);
                            hashMap.put("phone",phone);
                            hashMap.put("password",password);
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(DangKiTKActivity.this,MainActivity.class);
                                        intent.putExtra("username",username);
                                        intent.putExtra("email",email);
                                        intent.putExtra("phone",phone);
                                        intent.putExtra("password",password);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(DangKiTKActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}

