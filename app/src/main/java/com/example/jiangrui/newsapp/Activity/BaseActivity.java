package com.example.jiangrui.newsapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jiangrui on 2016/11/7.
 */

public class BaseActivity extends AppCompatActivity {
    public FragmentManager getMyFragmentManager(){
        return getSupportFragmentManager();
    }
    public FragmentTransaction getFragmentTransaction() {
        return getMyFragmentManager().beginTransaction();
    }

    public void addFragment(int containerViewId,Fragment fragment){
        getFragmentTransaction().add(containerViewId,fragment).commit();
    }
    public void replaceFragment(int containerViewId,Fragment fragment){
        getFragmentTransaction().replace(containerViewId, fragment).commit();
    }
}
