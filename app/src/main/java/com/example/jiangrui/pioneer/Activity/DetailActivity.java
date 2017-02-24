package com.example.jiangrui.pioneer.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiangrui.pioneer.Model.StoryDetail;
import com.example.jiangrui.pioneer.Model.ZhiHu;
import com.example.jiangrui.pioneer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiangrui on 2016/11/6.
 */

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.detail_image_view)
    ImageView detailImageView;
    @BindView(R.id.detail_web_view)
    WebView detailWebView;
    @BindView(R.id.detail_tool_bar)
    Toolbar detailToolBar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    public static final String PIC_URL = "PicUrl";
    public static final String CONTENT_Id = "ContentId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 隐藏系统原先的导航栏
         * 继承Activity则使用requestWindowFeature(Window.FETURE_NO_TITLE);
         */
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_detail);
        ButterKnife.bind(this);
        toolbarLayout.setTitle(getResources().getString(R.string.news_detail_title));
        setSupportActionBar(detailToolBar);
        /* 显示返回箭头 */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /* 设置返回箭头点击事件 */
        detailToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /* 获取Bundle中的数据 */
        Bundle bundle = getIntent().getExtras();
        Glide.with(this)
                .load(bundle.getString(PIC_URL))
                .error(R.mipmap.ic_launcher)
                .into(detailImageView);
        /* 设置webView在应用内显示内容 */
        detailWebView.getSettings().setJavaScriptEnabled(true);
        detailWebView.setWebViewClient(new WebViewClient());
//        detailWebView.loadUrl(ZhiHu.BASE_URL+"story/"+bundle.getString(CONTENT_Id));
        detailWebView.loadUrl(bundle.getString(CONTENT_Id));
    }
}
