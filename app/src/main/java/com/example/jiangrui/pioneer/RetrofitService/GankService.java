package com.example.jiangrui.pioneer.RetrofitService;

import com.example.jiangrui.pioneer.Model.Girls;

import retrofit2.http.GET;
import retrofit2.http.Path;
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
