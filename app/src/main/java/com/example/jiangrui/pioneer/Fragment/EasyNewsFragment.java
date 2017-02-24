package com.example.jiangrui.pioneer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiangrui.pioneer.Activity.DetailActivity;
import com.example.jiangrui.pioneer.Adapter.EasyRecyclerAdapter;
import com.example.jiangrui.pioneer.Model.News;
import com.example.jiangrui.pioneer.Model.ZhiHu;
import com.example.jiangrui.pioneer.R;
import com.example.jiangrui.pioneer.RetrofitService.NewsService;
import com.example.jiangrui.pioneer.RetrofitService.ZhiHuService;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jiangrui on 2016/11/6.
 */

public class EasyNewsFragment extends Fragment {
    @BindView(R.id.easy_recycler_view)
    EasyRecyclerView easyRecyclerView;
    //    @BindView(R.id.news_fragment_rollviewpager)
//    RollPagerView rollPagerView;
    private int page = 0;
    private EasyRecyclerAdapter adapter;
//    private RollPagerViewAdapter rollPagerViewAdapter;
//    private List<News.NewslistBean> rollPagerResources;
    private int count = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_easy_fragment, container, false);
        ButterKnife.bind(this, view);

        easyRecyclerView.setRefreshing(true);
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EasyRecyclerAdapter(getActivity());

        easyRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                easyGetDatas();
            }
        }, 1000);
        easyRecyclerView.setAdapter(adapter);
        easyRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                easyRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        adapter.clear();
                        easyGetDatas();
                    }
                }, 1000);
            }
        });
        adapter.setMore(R.layout.progress_wheel, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                easyRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        easyGetDatas();
                    }
                }, 1000);
            }

            @Override
            public void onMoreClick() {

            }
        });
        /* 设置item点击事件 */
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(DetailActivity.PIC_URL, adapter.getAllData().get(position).getPicUrl().toString());
                bundle.putString(DetailActivity.CONTENT_Id, adapter.getAllData().get(position).getUrl()+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private void easyGetDatas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsFragment.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())    /* 添加Gson转换器 */
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  /* 添加RxJava适配器 */
                .build();
//        ZhiHuService zhiHuService = retrofit.create(ZhiHuService.class);
//        zhiHuService.getZhiHuPictures()
                NewsService newsService = retrofit.create(NewsService.class);
                newsService.getNewsDatas(NewsFragment.APIKEY,"10",page)
                .subscribeOn(Schedulers.io())         /* 指定Observable执行所在的线程 */
                .map(new Func1<News, List<News.NewslistBean>>() {
                    @Override
                    public List<News.NewslistBean> call(News news) {
                        return news.getNewslist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  /* 将后边的线程切换到主线程 */
                .subscribe(new Subscriber<List<News.NewslistBean>>() {
                    @Override
                    public void onCompleted() {
                        easyRecyclerView.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(easyRecyclerView,R.string.network_error,Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<News.NewslistBean> newslistBeen) {
                        adapter.addAll(newslistBeen);
                    }
                });
        page++;

    }
}
