package com.wangxw.zhihudaily.bean;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function:
 */
public class Comment {

    private String author;
    private int id;
    private String content;
    private int likes;
    private int time;
    private String avatar;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
