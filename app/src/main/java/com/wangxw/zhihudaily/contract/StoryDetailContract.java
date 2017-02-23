package com.wangxw.zhihudaily.contract;

import com.wangxw.zhihudaily.base.BaseIPresenter;
import com.wangxw.zhihudaily.base.BaseIView;
import com.wangxw.zhihudaily.bean.LatestNews;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.Story;

/**
 * Created by wangxw on 2017/1/12 0012.
 * E-mail:wangxw725@163.com
 * function:
 */
public interface StoryDetailContract {

    interface IView extends BaseIView {

        void inlfateData(NewsDetail newsDetail);

        void setLoadingViewVisible(boolean isVisible);
    }

    interface IPresenter extends BaseIPresenter {

        void initData(Story story);
    }

}
