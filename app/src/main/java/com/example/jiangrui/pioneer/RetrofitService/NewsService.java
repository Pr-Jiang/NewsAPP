package com.example.jiangrui.pioneer.RetrofitService;

import com.example.jiangrui.pioneer.Model.News;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiangrui on 2016/11/6.
 */

public interface NewsService {
    @GET("keji/")
    Observable<News> getNewsDatas(
            @Query("key")String key,
            @Query("num")String num,
            @Query("page")int page);
}
