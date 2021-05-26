package com.example.duan1_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class ManHinhChaoActivity extends AppCompatActivity {
    TextView tvManHinhChao,tv1;
    Animation topAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        tvManHinhChao=findViewById(R.id.tvManHinhChao);
        tv1=findViewById(R.id.tv1);

        //Animation
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        tvManHinhChao.setAnimation(topAnim);
        tv1.setAnimation(bottomAnim);

        //        slashScreen(LoginActivity.class, R.anim.slide_in, R.anim.slide_out, 2000);
        slashScreen(LoginActivity.class, R.anim.left_to_right, R.anim.slide_out, 2000);

    }

    // HÀM SPlASH SCREEN
    public void slashScreen(final Class sender, final int animation_in, final int animation_out, int time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), sender));
                //Animation Intent(hieu ung qua trai dep mat)
                overridePendingTransition(animation_in, animation_out);
                finish();
            }
            //Sau 2s chuyen qua LoginActivity.class
        }, time);
    }

}
