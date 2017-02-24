package com.example.jiangrui.pioneer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiangrui.pioneer.Activity.DetailActivity;
import com.example.jiangrui.pioneer.Model.News;
import com.example.jiangrui.pioneer.Model.ZhiHu;
import com.example.jiangrui.pioneer.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;


/**
 * Created by jiangrui on 2016/11/6.
 */

public class EasyRecyclerAdapter extends RecyclerArrayAdapter<News.NewslistBean> {
    public EasyRecyclerAdapter(Context context) {
        super(context);
    }

    public static enum ITEM_TYPE {
        COMMON_ITEM, IMAGE_ITEM;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.IMAGE_ITEM.ordinal()) {
            return new RollPagerViewHolder(parent);
        } else if (viewType == ITEM_TYPE.COMMON_ITEM.ordinal()) {
            return new EasyViewHolder(parent);
        } else
            return null;
    }

    @Override
    public int getViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.IMAGE_ITEM.ordinal();
        } else
            return ITEM_TYPE.COMMON_ITEM.ordinal();
    }

    class EasyViewHolder extends BaseViewHolder<News.NewslistBean> {
        private ImageView easyImageView;
        private TextView easyTitleText;
        private TextView easyContentText;

        public EasyViewHolder(ViewGroup parent) {
            super(parent, R.layout.adapter_item);
            /* itemView.findViewById()的封装 */
            easyImageView = $(R.id.adapter_item_image);
            easyTitleText = $(R.id.adapter_item_title);
            easyContentText = $(R.id.adapter_item_content);
        }

        @Override
        public void setData(News.NewslistBean data) {
            easyTitleText.setText(data.getTitle());
            easyContentText.setText(data.getDescription());
            Glide.with(getContext())
                    .load(data.getPicUrl())
                    .error(R.mipmap.ic_launcher)
                    .into(easyImageView);
        }
    }

    class RollPagerViewHolder extends BaseViewHolder<News.NewslistBean> {
        private RollPagerView rollPagerView;
        private TextView rollPagerText;

        public RollPagerViewHolder(ViewGroup parent) {
            super(parent, R.layout.adapter_item_rollpager);
            rollPagerView = $(R.id.roll_pager);
            rollPagerText = $(R.id.roll_pager_text);
        }

        @Override
        public void setData(final News.NewslistBean data) {
            rollPagerView.setPlayDelay(3000);
            rollPagerView.setAdapter(new LoopPagerAdapter(rollPagerView) {
                @Override
                public View getView(ViewGroup container, int position) {
                    ImageView imageView = new ImageView(container.getContext());
                    Glide.with(getContext())
                            .load(data.getPicUrl())
                            .error(R.mipmap.ic_launcher)
                            .into(imageView);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    rollPagerText.setText(data.getTitle());
                    return imageView;
                }

                @Override
                public int getRealCount() {
                    return 5;
                }
            });
            rollPagerView.setOnItemClickListener(new com.jude.rollviewpager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.PIC_URL,data.getPicUrl());
                    intent.putExtra(DetailActivity.CONTENT_Id,data.getUrl());
                    getContext().startActivity(intent);
                }
            });
        }

    }
}
