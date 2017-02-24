package com.example.jiangrui.pioneer.RetrofitService;

import com.example.jiangrui.pioneer.Model.ZhiHu;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiangrui on 2017/1/16.
 */

public interface ZhiHuService {
    /* 今日新闻 */
    @GET("news/latest")
    Observable<ZhiHu> getZhiHuPictures();

    /* 加载更多 */
    @GET("news/before")
    Observable<ZhiHu> getZhiHuBeforePictures(@Query("date") String date);

    /* 新闻详情 */
    @GET("story")
    Observable<ZhiHu> getZhiHuStories(@Query("id")int id);
}
