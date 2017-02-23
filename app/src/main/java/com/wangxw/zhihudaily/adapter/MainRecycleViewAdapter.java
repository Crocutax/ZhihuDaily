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
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.bean.LatestNews;
import com.wangxw.zhihudaily.bean.Story;
import com.wangxw.zhihudaily.bean.TopStory;
import com.wangxw.zhihudaily.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangxw on 2017/1/13 0013.
 * E-mail:wangxw725@163.com
 * function:
 */
public class MainRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**头布局*/
    private static final int TYPE_HEADER = 0;
    /**日期*/
    private static final int TYPE_DATE = 1;
    /**新闻*/
    private static final int TYPE_STORY = 2;

    private List<Story> storyList;
    private List<TopStory> topStoryList;


    public void setData(LatestNews latestNews){
        storyList = getRecycleViewStoryList(latestNews);
        topStoryList = latestNews.getTop_stories();
        notifyDataSetChanged();
    }

    /**
     * 添加新的Stories
     * @param latestNews
     */
    public void addStories(LatestNews latestNews) {
        int lastPosition = storyList.size()+1;
        storyList.addAll(getRecycleViewStoryList(latestNews));
        //布局刷新
        notifyItemInserted(lastPosition);
    }

    /**
     * 给Story添加日期
     * @param latestNews
     * @return
     */
    private List<Story> getRecycleViewStoryList(LatestNews latestNews) {
        List<Story> storyList = latestNews.getStories();

        //日期Item
        Story story = new Story();
        story.setStoryDateItem(true);
        story.setTitle(TimeUtil.formatData(latestNews.getDate()));

        storyList.add(0,story);
        return storyList;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEADER;
        }else if(storyList.get(position-1).isStoryDateItem()){
            return TYPE_DATE;
        }else {
            return TYPE_STORY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_HEADER:
                holder = new HeaderItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_header,parent,false));
                break;
            case TYPE_DATE:
                holder = new DateItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_date_title, parent, false));
                break;
            case TYPE_STORY:
                holder = new StoryItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_story, parent, false));
                break;
            default:
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Story story = null;
        if(position>0) {
            story = storyList.get(position-1);
        }
        int itemViewType = holder.getItemViewType();
        switch (itemViewType)   {
            case TYPE_HEADER:
                ((HeaderItemViewHolder) holder).setData(topStoryList);
                break;
            case TYPE_DATE:
                ((DateItemViewHolder) holder).setData(story.getTitle());
                break;
            case TYPE_STORY:
                ((StoryItemViewHolder)holder).setData(story);
                break;
            default:
                break;
        };
    }

    @Override
    public int getItemCount() {
        return storyList != null ? (storyList.size()+1) : 0;
    }

    /**
     * 头布局
     */
    class HeaderItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.rvp_main_topstories)
        RollPagerView rvpMainTopstories;

        public HeaderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(final List<TopStory> topStoryList) {
            rvpMainTopstories.setAdapter(new MainViewPagerAdapter(topStoryList));

            rvpMainTopstories.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if(topStoryItemClickListener!=null){
                        topStoryItemClickListener.onTopStoryItemClick(topStoryList.get(position));
                    }
                }
            });
        }
    }

    /**
     * 内容条目
     */
     class StoryItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_list_card_view)
        CardView newsListCardView;
        @BindView(R.id.tv_main_recycleview_news_title)
        TextView tvMainRecycleviewNewsTitle;
        @BindView(R.id.iv_main_recycleview_thumbnail_image)
        ImageView ivMainRecycleviewThumbnailImage;
        @BindView(R.id.iv_main_recycleview_multipic)
        ImageView ivMainRecycleviewMultipic;

        StoryItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setData(final Story story) {
            tvMainRecycleviewNewsTitle.setText(story.getTitle());
            //根据是否已读设置标题颜色
            tvMainRecycleviewNewsTitle.setTextColor(
                    story.isRead() ? Color.GRAY : Color.BLACK);

            //根据图片数量.设置不同的UI
            String[] images = story.getImages();
            if(images!=null && images.length>0){
                Glide
                    .with(ivMainRecycleviewThumbnailImage.getContext())
                    .load(story.getImages()[0])
                    .into(ivMainRecycleviewThumbnailImage);
            }else {
                //无图
                ivMainRecycleviewThumbnailImage.setImageResource(R.drawable.img_recycleview_item_placeholder);
            }

            if(story.isMultipic()){
                ivMainRecycleviewMultipic.setVisibility(View.VISIBLE);
                Logger.d("显示多图标志");
            }

            newsListCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(storyItemClickListener!=null){
                        storyItemClickListener.onStoryItemClick(story);
                    }
                }
            });
        }
    }

    /**
     * 日期条目
     */
    class DateItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_recycleView_item_date_title)
        TextView tvRecycleViewItemDateTitle;

        DateItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        public void setData(String title) {
            tvRecycleViewItemDateTitle.setText(title);
        }
    }


    /**
     * 顶部ViewPager Item 点击事件
     */
    private TopStoryItemClickListener topStoryItemClickListener;

    public interface TopStoryItemClickListener {

        void onTopStoryItemClick(TopStory topStory);
    }

    public void setTopStoryItemClickListener(TopStoryItemClickListener topStoryItemClickListener) {
        this.topStoryItemClickListener = topStoryItemClickListener;
    }


    /**
     * RecycleView Story条目点击事件
     */
    private StoryItemClickListener storyItemClickListener;

    public interface StoryItemClickListener{
        void onStoryItemClick(Story story);
    }

    public void setStoryItemClickListener(StoryItemClickListener storyItemClickListener) {
        this.storyItemClickListener = storyItemClickListener;
    }
}
