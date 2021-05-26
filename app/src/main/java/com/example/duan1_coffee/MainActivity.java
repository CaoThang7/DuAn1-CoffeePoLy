package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.duan1_coffee.fragment.TaiKhoan_Fragment;
import com.example.duan1_coffee.function.Function;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.duan1_coffee.fragment.Home_Fragment;
import com.example.duan1_coffee.fragment.Info_Fragment;
import com.example.duan1_coffee.fragment.New_Fragment;
import com.example.duan1_coffee.fragment.User_Fragment;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Anh xa
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setItemIconTintList(null);
        // GỌI HÀM Bottom Click
        bottomItemSelected();
        // Fragment Default
        new Function().loadFragment(new New_Fragment(), R.id.fragment_container, frameLayout);

    }

    // HÀM Bottom Click
    private void bottomItemSelected() {
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.news:
                        new Function().loadFragment(new New_Fragment(), R.id.fragment_container, frameLayout);
                        return true;
                    case R.id.home:
                        new Function().loadFragment(new Home_Fragment(), R.id.fragment_container, frameLayout);
                        return true;
                    case R.id.info:
                        new Function().loadFragment(new Info_Fragment(), R.id.fragment_container, frameLayout);
                        return true;
                    case R.id.user:
                        new Function().loadFragment(new User_Fragment(), R.id.fragment_container, frameLayout);
                        return true;
                }
                return false;
            }
        });
    }
}
