package com.wangxw.zhihudaily.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function: 新闻额外信息
 */
public class StoryExtra implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.long_comments);
        dest.writeInt(this.popularity);
        dest.writeInt(this.short_comments);
        dest.writeInt(this.comments);
    }

    public StoryExtra() {
    }

    protected StoryExtra(Parcel in) {
        this.long_comments = in.readInt();
        this.popularity = in.readInt();
        this.short_comments = in.readInt();
        this.comments = in.readInt();
    }

    public static final Parcelable.Creator<StoryExtra> CREATOR = new Parcelable.Creator<StoryExtra>() {
        @Override
        public StoryExtra createFromParcel(Parcel source) {
            return new StoryExtra(source);
        }

        @Override
        public StoryExtra[] newArray(int size) {
            return new StoryExtra[size];
        }
    };
}
