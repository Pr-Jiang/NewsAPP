package com.example.jiangrui.pioneer.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jiangrui.pioneer.R;
import com.example.jiangrui.pioneer.Util.MulitPointTouchListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jiangrui on 2016/11/7.
 */

public class BitmapActivity extends AppCompatActivity {
    @BindView(R.id.bitmap_image_view)
    ImageView bitmapImageView;
    @BindView(R.id.my_toolbar)
    Toolbar bitmapToolbar;
    private String bitmapUrl;
    private String bitmapId;

    Matrix matrix = new Matrix();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);
        bitmapToolbar.setTitle("Picture");
        /* 设置Toolbar上的返回按钮 */
        setSupportActionBar(bitmapToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bitmapToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bitmapId = getIntent().getExtras().getString("PicId");
        bitmapUrl = getIntent().getExtras().getString("PicUrl");
//        Glide.with(this)
//                .load(bitmapUrl)
//                .error(R.drawable.smartfish)
//                .fitCenter()
//                .into(bitmapImageView);

//        Log.i("BitmapMatrix_0",matrix.toString());
        Picasso.with(this)
                .load(bitmapUrl)
                .error(R.drawable.smartfish)
                .into(bitmapImageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        /* 使图片居中 */
                        Drawable drawable = bitmapImageView.getDrawable();
                        RectF imageRectF = new RectF(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        Log.i("RectF", "imageRectF = " + imageRectF);
                        RectF viewRectF = new RectF(0, 0, 1080, 1674);
                        Log.i("RectF", "viewRectF = " + viewRectF);
                        matrix.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER);
                        Log.i("BitmapMatrix_1", matrix.toString());
                        bitmapImageView.setImageMatrix(matrix);
                    }
                });

        bitmapImageView.setOnTouchListener(new MulitPointTouchListener());   //设置手势缩放
//        Log.i("RectF", "viewRectF_1 = " + new RectF(0, 0, bitmapImageView.getWidth(), bitmapImageView.getHeight()));
//        Log.i("RectF","------------------------------");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bitmap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                savePicture(this, bitmapId);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePicture(final Context context, final String id) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                try {
                    /* Picasso加载图片质量高于Glide */
                    bitmap = Picasso.with(context).load(bitmapUrl).get();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                if (bitmap == null) {
                    subscriber.onError(new Exception("无法下载图片"));
                }
                subscriber.onNext(bitmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        File appDir = new File(Environment.getExternalStorageDirectory(), "NewsAPP");
                        String pictureName = id + ".jpg";
                        File file = new File(appDir, pictureName);
                        try {
                            FileOutputStream outputStream = new FileOutputStream(file);
                            /* 断言,true则继续执行,false则java.lang.AssertionError */
                            assert (bitmap != null);
                            /**
                             * 压缩图片
                             * @param quality 压缩率  100 表示不压缩
                             */
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /* 通知图库更新 */
                        Uri uri = Uri.fromFile(file);
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        Toast.makeText(context, "图片已保存至" + appDir.getAbsolutePath() + "文件夹", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
