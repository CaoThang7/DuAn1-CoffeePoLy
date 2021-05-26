package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.duan1_coffee.DangKiTKActivity;
import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.MainActivity;
import com.example.duan1_coffee.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.duan1_coffee.model.User;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaiKhoan_Fragment extends androidx.fragment.app.Fragment {
    TextInputEditText fullName,name,email,phone,pass;
    Button btnUpdateUser;
    CircleImageView circleImageView;
    //    DatabaseReference reference;
//    FirebaseUser firebaseUser;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    //    String userId;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tk, container, false);

        //Xuat thong tin nguoi dung tu gmail
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);





        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



//        if(firebaseUser != null){
//            userId = firebaseUser.getUid();
//            Log.d("ACCOUTN", "" + userId);
//        }

        //Anh xa
//        fullName = view.findViewById(R.id.fullname);
        name = view.findViewById(R.id.tkUser);
        email = view.findViewById(R.id.emailUser);
        phone =view.findViewById(R.id.sdtUser);
        pass = view.findViewById(R.id.passUser);
        circleImageView=view.findViewById(R.id.profile_image);
        btnUpdateUser=view.findViewById(R.id.btnUpdateUser);
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                String fullNameChange = fullName.getText().toString().trim();
//                String usernameChange = name.getText().toString().trim();
//                String phoneChange = phone.getText().toString().trim();
//                updateUserInfo(usernameChange, phoneChange);



                final String usernameChange = name.getText().toString();
                final String phoneChange = phone.getText().toString();

                //update
                User user = new User(usernameChange,phoneChange);
                Update(user);



            }
        });




        //Xuat thong tin nguoi dung gmail len header cua nav_menu
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
//            Uri personPhoto = acct.getPhotoUrl();
            name.setText(personName);
            email.setText(personEmail);
//            Glide.with(this).load(String.valueOf(personPhoto)).into(imgnav);
            Glide.with(this).load(acct.getPhotoUrl()).into(circleImageView);
            btnUpdateUser.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            pass.setVisibility(View.INVISIBLE);
        }
        else {

            showAllUserData();
        }



        return view;
    }


    private void showAllUserData() {
//        Intent intent = getActivity().getIntent();
////        String user_username = intent.getStringExtra("email");
//        String user_name = intent.getStringExtra("username");
//        String user_email = intent.getStringExtra("email");
//        String user_phoneNo = intent.getStringExtra("phone");
//        String user_password = intent.getStringExtra("password");
//
//        name.setText(user_name);
////        fullname.setText(user_username);
//        email.setText(user_email);
//        phone.setText(user_phoneNo);
//        pass.setText(user_password);
        //Xuat thong tin nguoi dung gmail len header cua nav_menu



            String id=firebaseAuth.getInstance().getCurrentUser().getUid();
            databaseReference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    if(dataSnapshot.exists()){
                        //Check nhap dung pass cu
                        if (!user.getUsername().equals(name)) {
                            name.setText(user.getUsername());
                        }
                        if (!user.getEmail().equals(email)) {
                            email.setText(user.getEmail());
                        }
                        if (!user.getPhone().equals(phone)) {
                            phone.setText(user.getPhone());
                        }
                        if (!user.getPassword().equals(pass)) {
                            pass.setText(user.getPassword());
                        }
                        if (user.getImage().equals("default")){
                            circleImageView.setImageResource(R.mipmap.ic_logoapp);
                        } else {
                            Glide.with(getContext()).load(user.getImage()).into(circleImageView);
                        }
//                        String photoURL=user.getImage();
//                        if (user.getImage().equals("default")){
//                            circleImageView.setImageResource(R.mipmap.ic_launcher);
//                        } else {
//                            Glide.with(getContext()).load(photoURL).into(circleImageView);
//                        }


//                        if (!user.getImage().equals(circleImageView)) {
////                        gmailGG.setText(user.getEmail());
//                            String photoURL=user.getImage();
//                            Glide.with(getContext()).load(photoURL).into(circleImageView);
//
//                        }
                    }
//                    else {
//                        //Initialise firebase
//                        firebaseAuth= FirebaseAuth.getInstance();
//                        final FirebaseUser mUser=firebaseAuth.getCurrentUser();
//                        //xuat thong tin nguoi dung facebook len header cua nav_menu
//                        if(mUser != null){
//                            String namee= mUser.getDisplayName();
//                            String emaill= mUser.getEmail();
//                            String photoURL= mUser.getPhotoUrl().toString();
//                            Glide.with(getActivity()).load(photoURL).into(circleImageView);
//              //            Glide.with(getActivity()).load(acct.getPhotoUrl()).into(imgnav);
//                            name.setText(namee);
//                            email.setText(emaill);
//                            btnUpdateUser.setVisibility(View.INVISIBLE);
////                            phone.setVisibility(View.INVISIBLE);
//                            pass.setVisibility(View.INVISIBLE);
//                        }
//                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });



    }




    //Update
    private void Update (User user){

        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(id).updateChildren(user.userUpdate());
    }
}
