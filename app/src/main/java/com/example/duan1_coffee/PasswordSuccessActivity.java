package com.example.duan1_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class PasswordSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_success);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                //Animation Intent(hieu ung qua trai dep mat)
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                finish();
            }
            //Sau 2s chuyen qua LoginActivity.class
        }, 2000);
    }
}
