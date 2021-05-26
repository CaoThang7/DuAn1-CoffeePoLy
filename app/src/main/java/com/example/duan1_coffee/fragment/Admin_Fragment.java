package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.duan1_coffee.LoginActivity;
import com.example.duan1_coffee.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin_Fragment extends androidx.fragment.app.Fragment {
    DrawerLayout dr_ly;
    Toolbar tb;
    NavigationView nv;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);

        //Xuat thong tin nguoi dung tu gmail
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

        //Initialise firebase
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser=mAuth.getCurrentUser();

        //add them icon > ben phai cua navagationView
        NavigationView navigationView=view.findViewById(R.id.nv_view);
        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_image);
        //set icon hinh co mau
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);
        //Anh xa img va textview tren header cua nav_menu
        TextView txtname = (TextView) header.findViewById(R.id.namenav);
        TextView txtgmail = (TextView)header.findViewById(R.id.gmailnav);
        ImageView imgnav = (ImageView) header.findViewById(R.id.imgnav);

        txtname.setText("Admin");
        txtgmail.setText("admin@gmail.com");
        imgnav.setImageResource(R.drawable.logoapp2);


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
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new TaiKhoan_Admin_Fragment()).commit();
        }

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.abc:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new TaiKhoan_Admin_Fragment()).commit();
                        break;
                    case R.id.abcd:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_ly,new DoiMatKhau_Fragment()).commit();
                        break;
                    case R.id.abcde:
                        Intent i=new Intent(getContext(),LoginActivity.class);
                        startActivity(i);
                        mAuth.signOut();
                        LoginManager.getInstance().logOut();
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


        return view;
    }
    private ActionBarDrawerToggle setupDrawerToogle(){
        return new ActionBarDrawerToggle(getActivity(),dr_ly,tb,R.string.Open,R.string.Close);
    }





}
