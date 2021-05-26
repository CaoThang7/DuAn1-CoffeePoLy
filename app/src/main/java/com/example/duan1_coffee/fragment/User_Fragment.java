package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.R;
import com.example.duan1_coffee.model.User;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Fragment extends androidx.fragment.app.Fragment {
    DrawerLayout dr_ly;
    Toolbar tb;
    NavigationView nv;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //Xuat thong tin nguoi dung tu gmail
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

        //Initialise firebase
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser=mAuth.getCurrentUser();

        //add them icon > ben phai cua navagationView
        NavigationView navigationView=view.findViewById(R.id.nv_view);
        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(4).setActionView(R.layout.menu_image);
        //set icon hinh co mau
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);
        //Anh xa img va textview tren header cua nav_menu
        TextView txtname = (TextView) header.findViewById(R.id.namenav);
        TextView txtgmail = (TextView)header.findViewById(R.id.gmailnav);
        ImageView imgnav = (ImageView) header.findViewById(R.id.imgnav);

        //Xuat thong tin nguoi dung gmail len header cua nav_menu
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
//            Uri personPhoto = acct.getPhotoUrl();
            txtname.setText(personName);
            txtgmail.setText(personEmail);
//            Glide.with(this).load(String.valueOf(personPhoto)).into(imgnav);
            Glide.with(this).load(acct.getPhotoUrl()).into(imgnav);


        }else {
            String id=mAuth.getInstance().getCurrentUser().getUid();
            databaseReference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);

                    if(dataSnapshot.exists()){
                        //Check nhap dung pass cu
                        if (!user.getUsername().equals(txtname)) {
                            txtname.setText(user.getUsername());
                        }
                        if (!user.getEmail().equals(txtgmail)) {
                            txtgmail.setText(user.getEmail());
                        }
                        if (user.getImage().equals("default")){
                            imgnav.setImageResource(R.mipmap.ic_logoapp);
                        } else {
                            Glide.with(getContext()).load(user.getImage()).into(imgnav);
                        }
                    }

                    else {
                        //Initialise firebase
                        mAuth= FirebaseAuth.getInstance();
                        final FirebaseUser mUser=mAuth.getCurrentUser();
                        //xuat thong tin nguoi dung facebook len header cua nav_menu
                        if(mUser != null){
                            String namee= mUser.getDisplayName();
                            String emaill= mUser.getEmail();
                            String photoURL= mUser.getPhotoUrl().toString();
                            Glide.with(getActivity()).load(photoURL).into(imgnav);
                            //            Glide.with(getActivity()).load(acct.getPhotoUrl()).into(imgnav);
                            txtname.setText(namee);
                            txtgmail.setText(emaill);
                        }
                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

//        //xuat thong tin nguoi dung facebook len header cua nav_menu
//        if(mUser != null){
//            String name= mUser.getDisplayName();
//            String email= mUser.getEmail();
////            String photoURL= mUser.getPhotoUrl().toString();
////            Glide.with(getActivity()).load(photoURL).into(imgnav);
////            Glide.with(getActivity()).load(acct.getPhotoUrl()).into(imgnav);
//            txtname.setText(name);
//            txtgmail.setText(email);
//
////            navigationView.getMenu().findItem(R.id.abcd).setVisible(false);
//        }





        //Navigation Drawer
        dr_ly=view.findViewById(R.id.dr_ly);
        tb=view.findViewById(R.id.tg_bar);
        nv=view.findViewById(R.id.nv_view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(tb);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerToggle=setupDrawerToogle();

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        dr_ly.addDrawerListener(drawerToggle);

        if(savedInstanceState == null){
            nv.setCheckedItem(R.id.abc);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new TaiKhoan_Fragment()).commit();
        }

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.abc:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new TaiKhoan_Fragment()).commit();
                        break;
                    case R.id.abcd:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new DoiMatKhau_Fragment()).commit();
                        break;
                    case R.id.thongtin:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new thongtinapp_Fragment()).commit();
                        break;
                    case R.id.hoidap:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new hoidap_Fragment()).commit();
                        break;
                    case R.id.abcde:
                        signOut();
                        Intent i=new Intent(getContext(),LoginActivity.class);
                        startActivity(i);

                        mAuth.signOut();
                        LoginManager.getInstance().logOut();
                        getActivity().overridePendingTransition(R.anim.right_to_left,R.anim.slide_in);
                        getActivity().finish();
                        break;

                    default:
////                        fragmentClass=Fragment_thu.class;
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new Fragment_thu()).commit();
                }

                item.setChecked(true);
                getActivity().setTitle(item.getTitle());
                dr_ly.closeDrawers();
                return true;
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        return view;
    }
    private ActionBarDrawerToggle setupDrawerToogle(){
        return new ActionBarDrawerToggle(getActivity(),dr_ly,tb,R.string.Open,R.string.Close);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"baibai",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });



    }



}
