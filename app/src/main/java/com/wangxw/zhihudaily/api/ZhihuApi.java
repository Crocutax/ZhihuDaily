package com.wangxw.zhihudaily.api;

import com.wangxw.zhihudaily.bean.Comment;
import com.wangxw.zhihudaily.bean.LatestNews;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.SplashImage;
import com.wangxw.zhihudaily.bean.StoryExtra;
import com.wangxw.zhihudaily.bean.Theme;
import com.wangxw.zhihudaily.bean.Themes;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function:
 */
public interface ZhihuApi {

    // Splash页图片
    @GET("start-image/1080*1776")
    Observable<SplashImage> getSplashImage();

    // 今日热闻
    @GET("news/latest")
    Observable<LatestNews> getLatestNews();

    // 新闻详情
    @GET("story/{id}")
    Observable<NewsDetail> getNewsDetail(@Path("id") int id);

    // 加载更多(前一天新闻)
    @GET("news/before/{date}")
    Observable<LatestNews> getBeforeNews(@Path("date") String date);

    // 新闻的附加数据(点赞数,评论数等)
    @GET("story-extra/{id}")
    Observable<StoryExtra> getStoreExtra(@Path("id") int id);

    // 获取新闻的长评论
    @GET("story/{id}/long-comments")
    Observable<List<Comment>> getLongComments(@Path("id") int id);

    // 获取新闻的短评论
    @GET("story/{id}/short-comments")
    Observable<List<Comment>> getShortComments(@Path("id") int id);

    // 短评论加载更多
    @GET("story/{story_id}/short-comments/before/{comment_id}")
    Observable<List<Comment>> getBeforeShortComment(@Path("story_id") int storyId, @Path("comment_id") int lastCommentId);

    // 长评论加载更多
    @GET("story/{story_id}/long-comments/before/{comment_id}")
    Observable<List<Comment>> getBeforeLongComment(@Path("story_id") int storyId, @Path("comment_id") int lastCommentId);

    // 获取主题列表
    @GET("themes")
    Observable<Themes> getThemes();

    // 获取某个主题日报的列表
    @GET("theme/{id}")
    Observable<Theme> getTheme(@Path("id") int id);

    // 某个主题日报加载更多
    @GET("theme/{theme_id}/before/{story_id}")
    Observable<Theme> getBeforeTheme(@Path("theme_id") int theme_id, @Path("story_id") int story_id);
}
