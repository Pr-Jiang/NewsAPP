package com.example.jiangrui.newsapp.Util;

/**
 * Created by jiangrui on 2016/11/5.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
