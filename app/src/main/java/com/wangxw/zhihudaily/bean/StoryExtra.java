package com.wangxw.zhihudaily.bean;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function: 新闻额外信息
 */
public class StoryExtra {

    private int long_comments;
    private int popularity;     //点赞总数
    private int short_comments;
    private int comments;   //评论总数

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "StoryExtra{" +
                "long_comments=" + long_comments +
                ", popularity=" + popularity +
                ", short_comments=" + short_comments +
                ", comments=" + comments +
                '}';
    }
}
