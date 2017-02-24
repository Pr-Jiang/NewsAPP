package com.example.jiangrui.pioneer.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiangrui.pioneer.Model.News;
import com.example.jiangrui.pioneer.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Created by jiangrui on 2016/11/22.
 */

public class RollPagerViewAdapter extends LoopPagerAdapter {
    private List<News.NewslistBean> resources;

    public RollPagerViewAdapter(RollPagerView viewPager, List<News.NewslistBean> resources) {
        super(viewPager);
        this.resources = resources;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        Glide.with(container.getContext())
                .load(resources.get(position).getPicUrl())
                .error(R.mipmap.ic_launcher)
                .into(imageView);
//        imageView.setImageResource(res[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public int getRealCount() {
        return resources.size();
    }
}
