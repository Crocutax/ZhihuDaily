package com.wangxw.zhihudaily.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.Constants;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.bean.Story;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangxw on 2017/1/13 0013.
 * E-mail:wangxw725@163.com
 * function:
 */
public class MainRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Story> storyList;

    public MainRecycleViewAdapter(List<Story> storyList) {
        this.storyList = storyList;
    }

    public void addNewsData(List<Story> storyList) {
        this.storyList.addAll(storyList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return storyList.get(position).isStoryDateItem() ? Constants.ITEM_DATE : Constants.ITEM_STORY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Constants.ITEM_DATE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_date_title, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_story, parent, false);
        }
        return new DateItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Story story = storyList.get(position);
        //日期标题 Item
        if(story.isStoryDateItem()){
            Logger.i("日期标题");
            DateItemViewHolder dateHolder = (DateItemViewHolder) holder;
            dateHolder.tvRecycleViewItemDateTitle.setText(story.getTitle());
        }else {
            Logger.i("Story");
            //Story Item
            StoryItemViewHolder storyHoler = (StoryItemViewHolder) holder;

            storyHoler.tvMainRecycleviewNewsTitle.setText(story.getTitle());
            //根据是否已读设置标题颜色
            storyHoler.tvMainRecycleviewNewsTitle.setTextColor(
                            story.isRead() ? Color.GRAY : Color.BLACK);

            //根据图片数量.设置不同的UI
            String[] images = story.getImages();
            if(images==null || images.length==0){
                //无图
                storyHoler.ivMainRecycleviewThumbnailImage.setImageResource(R.drawable.img_recycleview_item_placeholder);
            }else {
                if(images.length>1){
                    //多图
                    storyHoler.ivMainRecycleviewMultipic.setVisibility(View.VISIBLE);
                }
                    Glide
                        .with(storyHoler.ivMainRecycleviewMultipic.getContext())
                        .load(story.getImages()[0])
                        .into(storyHoler.ivMainRecycleviewMultipic);
            }

        }
    }

    @Override
    public int getItemCount() {
        return storyList != null ? storyList.size() : 0;
    }


    static class StoryItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_main_recycleview_news_title)
        TextView tvMainRecycleviewNewsTitle;
        @BindView(R.id.iv_main_recycleview_thumbnail_image)
        ImageView ivMainRecycleviewThumbnailImage;
        @BindView(R.id.iv_main_recycleview_multipic)
        ImageView ivMainRecycleviewMultipic;
        @BindView(R.id.news_list_card_view)
        CardView newsListCardView;

        StoryItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class DateItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_recycleView_item_date_title)
        TextView tvRecycleViewItemDateTitle;

        DateItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
