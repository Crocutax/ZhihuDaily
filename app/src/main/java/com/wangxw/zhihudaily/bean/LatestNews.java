package com.wangxw.zhihudaily.bean;

import java.util.List;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function: 当日新闻
 */
public class LatestNews {

    /**日期*/
    private String date;
    /**当日新闻*/
    private List<NewsItem> stories;
    /**ViewPager顶部滚动新闻*/
    private List<NewsItem> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewsItem> getStories() {
        return stories;
    }

    public void setStories(List<NewsItem> stories) {
        this.stories = stories;
    }

    public List<NewsItem> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<NewsItem> top_stories) {
        this.top_stories = top_stories;
    }

    @Override
    public String toString() {
        return "LatestNews{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
