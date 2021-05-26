package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.facebook.FacebookSdk;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

import com.example.duan1_coffee.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    EditText tvEmail, tvPassword;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;
    CallbackManager mCallbackManager;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser fUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        // Initialise Facebook SDK
        FacebookSdk.sdkInitialize(LoginActivity.this);

        // Initialise firebase
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        fUser=FirebaseAuth.getInstance().getCurrentUser();
        // Anh xa
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        findViewById(R.id.btnGoogle).setOnClickListener(this);
        findViewById(R.id.btnDangNhap).setOnClickListener(this);
        findViewById(R.id.btnDangKi).setOnClickListener(this);
        findViewById(R.id.btnFacebook).setOnClickListener(this);
        findViewById(R.id.tvquenmatkhauu).setOnClickListener(this);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        // Initialize Google Login button
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    // UPDATE UI FIREBASE
    public void updateUI(FirebaseUser user) {
        if(user != null){
            if(user.getEmail().equals("admin@gmail.com")){
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
            }else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }else {
            Toast.makeText(LoginActivity.this, "Please sign in continue.", Toast.LENGTH_SHORT).show();
        }
    }


    private Boolean XacThucUser(){
        String username=tvEmail.getText().toString();
        if(username.isEmpty()){
            tvEmail.setError("Bạn chưa nhập Username");
            return false;
        }else {
            tvEmail.setError(null);
            return true;
        }
    }

    private Boolean XacThucPassword(){
        String password=tvPassword.getText().toString();
        if(password.isEmpty()){
            tvPassword.setError("Bạn chưa nhập Password");
            return false;
        }else {
            tvPassword.setError(null);
            return true;
        }
    }

    // ALL FUNCTION - BUTTON
    // LOGIN NORMAL
    public void loginNormal() {
        final String email = tvEmail.getText().toString().trim();
        final String password = tvPassword.getText().toString().trim();
//        if(email.isEmpty()){
//            tvEmail.setError("loi");
//            tvEmail.requestFocus();
//        }
//        if(password.isEmpty()){
//            tvPassword.setError("loi");
//            tvPassword.requestFocus();
//        }

        if(email.equals("admin@gmail.com") && password.equals("admin1234")){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            overridePendingTransition(R.anim.left_to_right, R.anim.slide_out);
                        }
                    });
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                            User user = postSnapshot.getValue(User.class);
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                Toast.makeText(LoginActivity.this, "Welcome! " + email, Toast.LENGTH_SHORT).show();
                                                finish();
//                                                Intent i=new Intent(LoginActivity.this,MainActivity.class);
//                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                i.putExtra("username",user.getUsername());
//                                                i.putExtra("email",email);
//                                                i.putExtra("phone",user.getPhone());
//                                                i.putExtra("password",password);
//                                                startActivity(i);
//                                                finish();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Sai mật khẩu hoặc password!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }



    // SIGN UP
    public void signUp(){
        startActivity(new Intent(LoginActivity.this,DangKiTKActivity.class));
    }
    // FACEBOOK
    public void loginFb(){
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                        Toast.makeText(LoginActivity.this, dataSnapshot.child("email").getValue().toString(),Toast.LENGTH_SHORT).show();
                                        if(user.getEmail().equalsIgnoreCase(dataSnapshot.child("email").getValue().toString())){
//                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                            startActivity(i);
//                                            finish();
                                        }if(!user.getEmail().equalsIgnoreCase(dataSnapshot.child("email").getValue().toString())){
                                            final String userid=user.getUid();
                                            HashMap<String, String>hashMap=new HashMap<>();
                                            hashMap.put("userId",userid);
                                            hashMap.put("username",user.getDisplayName());
                                            hashMap.put("fullname",user.getEmail());
                                            String photoURL= user.getPhotoUrl().toString();
                                            Glide.with(LoginActivity.this).load(photoURL);
                                            hashMap.put("image",photoURL);
                                            hashMap.put("email",user.getEmail());
                                            hashMap.put("phone","0934135394");
                                            hashMap.put("password","default");
                                            reference.child(userid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                }
                                            });
                                        }
//                                        else {
//                                            final String userid=user.getUid();
//                                            HashMap<String, String>hashMap=new HashMap<>();
//                                            hashMap.put("userId",userid);
//                                            hashMap.put("username",user.getDisplayName());
//                                            hashMap.put("fullname",user.getEmail());
//                                            String photoURL= user.getPhotoUrl().toString();
//                                            Glide.with(LoginActivity.this).load(photoURL);
//                                            hashMap.put("image",photoURL);
//                                            hashMap.put("email",user.getEmail());
//                                            hashMap.put("phone","");
//                                            hashMap.put("password",user.getEmail());
//                                            reference.child(userid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                }
//                                            });
//
//                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // GOOGLE
    public void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);


            // Signed in successfully, show authenticated UI.
            // updateUI(account);
            if(account != null) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            if(account.getEmail().equalsIgnoreCase(dataSnapshot.child("email").getValue().toString())){
//                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                startActivity(i);
//                            }else {
//                                final String userid=account.getId();
//                                HashMap<String, String>hashMap=new HashMap<>();
//                                hashMap.put("userId",userid);
//                                hashMap.put("username",account.getDisplayName());
//                                hashMap.put("fullname",account.getDisplayName());
//                                hashMap.put("image","default");
//                                hashMap.put("email",account.getEmail());
//                                hashMap.put("phone","");
//                                hashMap.put("password",account.getEmail());
//                                reference.child(userid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful()){
//                                            Toast.makeText(LoginActivity.this, "Success!",Toast.LENGTH_SHORT).show();
//                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                                            startActivity(i);
//                                        }
//                                    }
//                                });
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }
            //Animation Intent
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    // FORGOT PASSWORD
    public void forgotPassword(){
        startActivity(new Intent(LoginActivity.this,QuenMatKhauActivity.class));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDangNhap:

                if(!XacThucUser() | !XacThucPassword()){
                    return;
                }else {
                    loginNormal();
                }
//                loginNormal();
                break;
            case R.id.btnDangKi:
                signUp();
                break;
            case R.id.btnFacebook:
                loginFb();
                break;
            case R.id.btnGoogle:
                loginGoogle();
                break;
            case R.id.tvquenmatkhauu:
                forgotPassword();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
