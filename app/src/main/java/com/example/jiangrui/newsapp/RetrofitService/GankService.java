package com.example.jiangrui.newsapp.RetrofitService;

import com.example.jiangrui.newsapp.Model.Girls;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jiangrui on 2016/11/7.
 */

public interface GankService {
    @GET("data/福利/{num}/{page}")
    Observable<Girls> getPictures(
            @Path("num")int num,
            @Path("page")int page);
}
