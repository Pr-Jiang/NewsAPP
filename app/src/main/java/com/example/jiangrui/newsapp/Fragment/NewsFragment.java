package com.example.jiangrui.newsapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiangrui.newsapp.Activity.DetailActivity;
import com.example.jiangrui.newsapp.Adapter.MyRecyclerViewAdapter;
import com.example.jiangrui.newsapp.Model.News;
import com.example.jiangrui.newsapp.R;
import com.example.jiangrui.newsapp.RetrofitService.NewsService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jiangrui on 2016/11/5.
 */

public class NewsFragment extends Fragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MyRecyclerViewAdapter adapter;
    private int page = 0;

    public static final String BASE_URL = "http://api.tianapi.com/";
    public static final String APIKEY = "bc880d0a8dd61c0c8af01647c1c97684";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.news_fragment, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getDatas();
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        /* 设置下拉刷新 */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(null);  /* 模拟空白 */
                        page = 0;
                        getDatas();       /* 重新获取数据 */
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });


        return view;
    }

    /* Retrofit+RxJava进行数据请求和转换 */
    public void getDatas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NewsService newsService = retrofit.create(NewsService.class);
        Observable<News> newsObservable = newsService.getNewsDatas(APIKEY, "10",page);
        newsObservable.subscribeOn(Schedulers.io())    /*指定Observable的执行环境*/
                .map(new Func1<News, List<News.NewslistBean>>() {
                    @Override
                    public List<News.NewslistBean> call(News news) {
                        return news.getNewslist();
                    }
                }).observeOn(AndroidSchedulers.mainThread())  /*将后边的线程环境切换为主线程*/
                .subscribe(new Subscriber<List<News.NewslistBean>>() {
                    @Override
                    public void onCompleted() {
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<News.NewslistBean> newslistBeen) {
                        adapter = new MyRecyclerViewAdapter(getActivity(), newslistBeen);
                        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, News.NewslistBean newsItem) {
                                Intent intent = new Intent(getActivity(),DetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("PicUrl",newsItem.getPicUrl());
                                bundle.putString("ContentUrl",newsItem.getUrl());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                    }
                });
        /* 页数增加 */
        page++;
    }
}
