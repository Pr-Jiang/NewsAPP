package com.example.jiangrui.pioneer.Util;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jiangrui on 2016/11/5.
 */

public class URLConnectionUtil {
    public static final String TAG = "URLConnectionUtil";
    public static final String HttpUrl = "http://api.tianapi.com/wxnew/?key=bc880d0a8dd61c0c8af01647c1c97684&num&rand";

    public static void getDatas(final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "getDatas()");
                HttpURLConnection connection = null;
                StringBuilder result = new StringBuilder();
                try {
                    URL url = new URL(HttpUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                        result.append("\r\n");
                    }
                    reader.close();
                    if (listener != null) {
                        listener.onFinish(result.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }
}
