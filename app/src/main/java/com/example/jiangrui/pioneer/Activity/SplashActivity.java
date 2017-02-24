package com.example.jiangrui.pioneer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.jiangrui.pioneer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangrui on 2016/11/9.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.splash_image)
    ImageView splashImage;

    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashImage.setImageResource(R.drawable.splash);

        scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true); /*动画执行完是否停留在执行完的状态*/
        scaleAnimation.setDuration(3000);

        splashImage.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
