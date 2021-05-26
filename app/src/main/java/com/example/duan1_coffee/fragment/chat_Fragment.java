package com.example.duan1_coffee.fragment;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;
import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.ViewPagerFragment.ChatsFragment;
import com.example.duan1_coffee.fragment.ViewPagerFragment.UsersFragment;
import com.example.duan1_coffee.fragment.product.ProductList_Fragment;
import com.example.duan1_coffee.fragment.product.ProductType_Fragment;
import com.example.duan1_coffee.model.TokenChat;
import com.example.duan1_coffee.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_Fragment extends androidx.fragment.app.Fragment {
    ImageView backchat;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    CircleImageView profile_image;
    TextView username;
    ProductTabAdapter productTabAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        //Anh Xa
        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);
        profile_image=view.findViewById(R.id.profile_image);
        username=view.findViewById(R.id.username);
        Toolbar toolbar=view.findViewById(R.id.toolbarMess);
        backchat=view.findViewById(R.id.backchat);

        backchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Info_Fragment llf = new Info_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });


//        //Nut back ve fragment info
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Info_Fragment llf = new Info_Fragment();
//                ft.replace(R.id.fragment_container, llf);
//                ft.commit();
//            }
//        });


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

//        //lấy tên,lấy ảnh từ firebase
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user=dataSnapshot.getValue(User.class);
//                username.setText(user.getUsername());
//                 if(user.getImage().equals("default")){
//                     profile_image.setImageResource(R.mipmap.ic_launcher);
//                 }else {
//                     Glide.with(getContext()).load(user.getImage()).into(profile_image);
//                 }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//
//        //ViewPager and tab layout
//        ViewpagerAdapter viewpagerAdapter=new ViewpagerAdapter(getActivity().getSupportFragmentManager());
//
//        //2 fragment cua tablayout
//        viewpagerAdapter.addFragment(new ChatsFragment(),"Chats");
//        viewpagerAdapter.addFragment(new UsersFragment(),"Users");
//
//        viewPager.setAdapter(viewpagerAdapter);
//
//        tabLayout.setupWithViewPager(viewPager);
//




        return view;
    }

//    //Goi viewpager
//   class ViewpagerAdapter extends FragmentPagerAdapter{
//
//        private ArrayList<Fragment>fragments;
//        private ArrayList<String> titles;
//
//        ViewpagerAdapter(FragmentManager fm){
//            super(fm);
//            this.fragments=new ArrayList<>();
//            this.titles=new ArrayList<>();
//        }
//
//       @NonNull
//       @Override
//       public Fragment getItem(int position) {
//           return fragments.get(position);
//       }
//
//       @Override
//       public int getCount() {
//
//           return fragments.size();
//       }
//
//       public void addFragment(Fragment fragment,String title){
//            fragments.add(fragment);
//            titles.add(title);
//       }
//
//       @NonNull
//       @Override
//       public CharSequence getPageTitle(int position){
//            return titles.get(position);
//       }
//
//   }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        productTabAdapter = new ProductTabAdapter(this);
        viewPager.setAdapter(productTabAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.addTab(tabLayout.newTab().setText("ListFriend"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    public class ProductTabAdapter extends FragmentStateAdapter {
        public ProductTabAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ChatsFragment();
                case 1:
                    return new UsersFragment();
            }
            return null;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }






}
