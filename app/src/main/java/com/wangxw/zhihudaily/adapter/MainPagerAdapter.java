package com.wangxw.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.bean.NewsItem;

import java.util.List;

/**
 * Created by wangxw on 2017/1/12 0012.
 * E-mail:wangxw725@163.com
 * function:
 */
public class MainPagerAdapter extends PagerAdapter {


    private List<NewsItem> topNews;

    public MainPagerAdapter(List<NewsItem> topNews) {
        this.topNews = topNews;
    }

    @Override
    public int getCount() {
        return topNews==null ? 0 : topNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Logger.i("instantiateItem:"+position+ "..新闻数量:"+topNews.size());
        Context context = container.getContext();
        ImageView imageView = (ImageView) View.inflate(context, R.layout.item_viewpager_imageview,null);

        Glide.with(context).load(topNews.get(position).getImages().get(0)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
