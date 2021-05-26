package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan1_coffee.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QuenMatKhauActivity extends AppCompatActivity {
    ImageView forget_password_back_btn;
    Button btnNext;
    TextInputLayout btnEmail;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        btnEmail=findViewById(R.id.forget_password_phone_number);
        btnNext=findViewById(R.id.forget_password_next_btn);
        forget_password_back_btn=findViewById(R.id.forget_password_back_btn);

        forget_password_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(QuenMatKhauActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = btnEmail.getEditText().getText().toString();

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull final Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(QuenMatKhauActivity.this, "Check email", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(QuenMatKhauActivity.this,PasswordSuccessActivity.class);
                        startActivity(i);
                    }
                    }
                });
            }
        });

    }

    private void Update (User user){

        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(id).updateChildren(user.userUpdate2());
    }

}
