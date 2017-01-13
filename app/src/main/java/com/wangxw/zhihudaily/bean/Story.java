package com.wangxw.zhihudaily.bean;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function: 新闻子条目
 */
public class Story {

    /**标题*/
    private String title;
    private String ga_prefix;
    private int type;
    private int id;
    private String[] images;

    /**自主添加字段:是否是新闻日期 Item*/
    private boolean isStoryDateItem;
    /**自主添加字段: 是否已读*/
    private boolean isRead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public boolean isStoryDateItem() {
        return isStoryDateItem;
    }

    public void setStoryDateItem(boolean storyDateItem) {
        isStoryDateItem = storyDateItem;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Story{" +
                "title='" + title + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", images=" + Arrays.toString(images) +
                ", isStoryDateItem=" + isStoryDateItem +
                ", isRead=" + isRead +
                '}';
    }
}
