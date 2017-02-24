package com.example.jiangrui.pioneer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jiangrui.pioneer.Fragment.EasyNewsFragment;
import com.example.jiangrui.pioneer.Fragment.NewsFragment;
import com.example.jiangrui.pioneer.Fragment.PictureFragment;
import com.example.jiangrui.pioneer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;   //悬浮按钮
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private EasyNewsFragment easyNewsFragment;
    private NewsFragment newsFragment;
    private PictureFragment pictureFragment;
    private Fragment currentFragment;
    private long currentTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        showNewsFragment();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.smartfish_email_des, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.send_me, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.smartfish_email));
                                intent = Intent.createChooser(intent, "请选择发送方式");
                                startActivity(intent);
                            }
                        }).show();
            }
        });

//       fab.setOnClickListener((view) -> {    //Lambda表达式
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                    .setAction("Action", null).show();
//        });


        /*设置toolbar上的触发NavigationView的按钮*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        /*设置点击响应*/
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void showNewsFragment() {
        if (easyNewsFragment == null) {
            easyNewsFragment = new EasyNewsFragment();
        }
//        if (newsFragment == null) {
//            newsFragment = new NewsFragment();
//        }
        addFragment(R.id.fragment_container, easyNewsFragment);
        currentFragment = easyNewsFragment;
    }

    private void showPictureFragment() {
        if (pictureFragment == null) {
            pictureFragment = new PictureFragment();
        }
        replaceFragment(R.id.fragment_container, pictureFragment);
        currentFragment = pictureFragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - currentTime > 2000) {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                currentTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_camera:
                Snackbar.make(getCurrentFocus(), "开发中...", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.nav_gallery:
                showPictureFragment();
                break;
            case R.id.nav_news:
                if (currentFragment != easyNewsFragment) {
                    showNewsFragment();
                }
                break;
            case R.id.nav_about_me:
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
