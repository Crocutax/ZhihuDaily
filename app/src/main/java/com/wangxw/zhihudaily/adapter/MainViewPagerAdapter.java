package com.wangxw.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.bean.TopStory;

import java.util.List;

/**
 * Created by wangxw on 2017/1/12 0012.
 * E-mail:wangxw725@163.com
 * function:
 */
public class MainViewPagerAdapter extends PagerAdapter {


    private List<TopStory> topStories;

    public MainViewPagerAdapter(List<TopStory> topStories) {
        this.topStories = topStories;
    }

    @Override
    public int getCount() {
        return topStories ==null ? 0 : topStories.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        ImageView imageView = (ImageView) View.inflate(context, R.layout.item_viewpager_imageview,null);

        Glide.with(context).load(topStories.get(position).getImage()).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
