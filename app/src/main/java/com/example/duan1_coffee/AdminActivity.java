package com.example.duan1_coffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.duan1_coffee.fragment.AnalysisFragment;
import com.example.duan1_coffee.fragment.Product_Fragment;
import com.example.duan1_coffee.fragment.TaiKhoan_Admin_Fragment;
import com.example.duan1_coffee.fragment.User_Fragment;
import com.example.duan1_coffee.fragment.order.OrderSateManagerFragment;
import com.example.duan1_coffee.function.Function;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.duan1_coffee.fragment.Admin_Fragment;
import com.example.duan1_coffee.fragment.Analytic_Fragment;

public class AdminActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Anh xa
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container2);
        bottomNav = findViewById(R.id.bottom_navigation2);
        bottomNav.setItemIconTintList(null);

        // GỌI HÀM Bottom Click
        bottomItemSelected();
        // Fragment Default
        new Function().loadFragment(new Analytic_Fragment(), R.id.fragment_container2, frameLayout);

    }

    // HÀM Bottom Click
    private void bottomItemSelected() {
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.analytic:
                        new Function().loadFragment(new Analytic_Fragment(), R.id.fragment_container2, frameLayout);
                        return true;
                    case R.id.analysis:
                        new Function().loadFragment(new AnalysisFragment(), R.id.fragment_container2, frameLayout);
                        return true;
                    case R.id.product:
                        new Function().loadFragment(new Product_Fragment(), R.id.fragment_container2, frameLayout);
                        return true;
                    case R.id.order_state:
                        new Function().loadFragment(new OrderSateManagerFragment(), R.id.fragment_container2, frameLayout);
                        return true;
                    case R.id.user:
                        new Function().loadFragment(new Admin_Fragment(), R.id.fragment_container2, frameLayout);
                        return true;
                }
                return false;
            }
        });
    }

}