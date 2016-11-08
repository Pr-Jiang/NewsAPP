package com.example.jiangrui.newsapp.Activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.jiangrui.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangrui on 2016/11/8.
 */

public class AboutMeActivity extends AppCompatActivity {
    @BindView(R.id.about_me_toolbar)
    Toolbar toolbar;
    @BindView(R.id.about_me_imageview)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        toolbar.setTitle("About 鱼先森");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imageView.setImageResource(R.drawable.smartfish);
    }
}
