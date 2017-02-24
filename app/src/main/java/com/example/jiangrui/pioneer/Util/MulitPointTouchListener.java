package com.example.jiangrui.pioneer.Util;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jiangrui on 2017/1/13.
 */

public class MulitPointTouchListener implements View.OnTouchListener {
    private static final String TAG = "MulitPpintTouchListener";
    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states

    enum States {NONE, DRAG, ZOOM}

    States mode = States.NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
//    float originalDist;
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;
//        originalDist = (float) Math.sqrt((double) v.getWidth()*v.getWidth()+v.getHeight()+v.getHeight());
        dumpEvent(event);

        /**
         * MotionEvent.ACTION_MASK 用于多点触摸操作
         * ACTION_POINTER_DOWN
         * ACTION_POINTER_UP   多点触摸动作
         */
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:           //单点触摸
                matrix.set(view.getImageMatrix());
                savedMatrix.set(matrix);
                Log.i("Matrix", matrix.toString());
                start.set(event.getX(), event.getY());
                mode = States.DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:   //多点触摸
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
//                    Log.i("Matrix", savedMatrix.toString());
                    midPoint(mid, event);
                    mode = States.ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = States.NONE;
                break;

            case MotionEvent.ACTION_MOVE:
//                if (mode == States.DRAG) {    /* 拖拽 */
//                    matrix.set(savedMatrix);
//                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
//                }
                if (mode == States.ZOOM) {      /* 缩放 */
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        Log.i("Matrix", "scale = " + scale);
                        matrix.postScale(scale, scale, mid.x, mid.y);

                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true; // indicate event was handled
    }

    private void dumpEvent(MotionEvent event) {
        String actionMode[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "HOVER_MOVE", "SCROLL", "HOVER_ENTER"};
        StringBuilder sb = new StringBuilder();
        /*  0x0100   01表示触控点索引  00表示动作类型   */
        int actionCode = event.getActionMasked();  //获得动作类型  event.getAction() & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(actionMode[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {  //如果为多点触控
            //获得触摸点索引(判断几点触控)
            sb.append("(pid ").append(event.getActionIndex());
            //(event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK >> MotionEvent.ACTION_POINTER_INDEX_SHIFT) == event.getActionIndex()
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }


    /**
     * 计算两触摸点距离
     *
     * @param event
     * @return
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}

