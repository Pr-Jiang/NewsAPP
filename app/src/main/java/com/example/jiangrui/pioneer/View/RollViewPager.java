package com.example.jiangrui.pioneer.View;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.jiangrui.pioneer.Model.News;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangrui on 2016/11/22.
 */

public class RollViewPager extends ViewPager {

    public static final String TAG = "RollViewPager";

    private Context context;
    private int currentItem;
    private ArrayList<View> dots;
    private List<News.NewslistBean> newslistBeen;
    private int dot_focus_resId;
    private int dot_normal_resId;

    private MyHandler myHandler = new MyHandler(this);
    /* 触摸时按下的点 */
    PointF downPoint = new PointF();
    /* 触摸时当前的点 */
    PointF currentPoint = new PointF();

    public RollViewPager(Context context,ArrayList<View> dots) {
        super(context);
        this.context = context;
        this.dots = dots;
    }
    private long start = 0;
    public class MyOnTouchListener implements OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            currentPoint.x = event.getX();
            currentPoint.y = event.getY();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    start = System.currentTimeMillis();
                    /* 记录按下时的坐标 */
                    downPoint.x = event.getX();
                    downPoint.y = event.getY();

                    myHandler.removeCallbacksAndMessages(null);


                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
            return true;
        }
    }
    private static class MyHandler extends Handler{
        WeakReference<RollViewPager> weakReference;

        public MyHandler(RollViewPager rollViewPager) {
            weakReference = new WeakReference<RollViewPager>(rollViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RollViewPager rollViewPager = weakReference.get();
            Log.e(TAG,"handleMessage:"+rollViewPager.currentItem);
            rollViewPager.setCurrentItem(rollViewPager.currentItem);
        }
    }
}
