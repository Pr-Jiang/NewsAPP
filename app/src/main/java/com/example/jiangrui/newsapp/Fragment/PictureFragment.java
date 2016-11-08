package com.example.jiangrui.newsapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiangrui.newsapp.Activity.BitmapActivity;
import com.example.jiangrui.newsapp.Adapter.PictureRecyclerAdapter;
import com.example.jiangrui.newsapp.Model.Girls;
import com.example.jiangrui.newsapp.R;
import com.example.jiangrui.newsapp.RetrofitService.GankService;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

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
 * Created by jiangrui on 2016/11/7.
 */

public class PictureFragment extends Fragment {
    public static final String GANK_URL = "http://gank.io/api/";
    private PictureRecyclerAdapter adapter;
    private int page = 1;
    @BindView(R.id.pic_recycler_view)
    EasyRecyclerView pictureRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picture_fragment,container,false);
        ButterKnife.bind(this,view);
        pictureRecyclerView.setRefreshing(true);
        pictureRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        adapter = new PictureRecyclerAdapter(getActivity());
        /* 设置延迟1秒后显示内容 */
        pictureRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getPictureDatas();
            }
        },1000);
        pictureRecyclerView.setAdapter(adapter);
        /* 设置下拉刷新动作 */
        pictureRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pictureRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        page = 1;
                        getPictureDatas();
                    }
                },1000);

            }
        });
        /* 上拉加载更多 */
        adapter.setMore(R.layout.progress_wheel, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                pictureRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPictureDatas();
                    }
                }, 1000);
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), BitmapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PicUrl",adapter.getAllData().get(position).getUrl());
                bundle.putString("PicId",adapter.getAllData().get(position).get_id());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getPictureDatas(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GANK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GankService gankService = retrofit.create(GankService.class);
        gankService.getPictures(20,page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Girls, List<Girls.ResultsBean>>() {
                    @Override
                    public List<Girls.ResultsBean> call(Girls girls) {
                        return girls.getResults();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Girls.ResultsBean>>() {
                    @Override
                    public void onCompleted() {
                        pictureRecyclerView.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        pictureRecyclerView.setRefreshing(false);
                        Snackbar.make(pictureRecyclerView, R.string.network_error,Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Girls.ResultsBean> resultsBeen) {
                        adapter.addAll(resultsBeen);
                    }
                });
        page++;
    }
}
