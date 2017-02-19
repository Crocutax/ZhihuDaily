package com.wangxw.zhihudaily.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_viewpager_imageview, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_viewpager_img);
        TextView textView = (TextView) view.findViewById(R.id.tv_item_viewpager_des);

        TopStory topStory = topStories.get(position);
        Glide.with(container.getContext()).load(topStory.getImage()).into(imageView);
        textView.setText(topStory.getTitle());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
