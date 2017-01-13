package com.wangxw.zhihudaily.contract;

import com.wangxw.zhihudaily.base.BaseIView;
import com.wangxw.zhihudaily.base.BaseIPresenter;
import com.wangxw.zhihudaily.bean.LatestNews;
import com.wangxw.zhihudaily.bean.TopStory;

import java.util.List;

/**
 * Created by wangxw on 2017/1/12 0012.
 * E-mail:wangxw725@163.com
 * function:
 */
public interface MainContract {

    interface View extends BaseIView {
        void initViewData(LatestNews latestNews);
    }

    interface Presenter extends BaseIPresenter {

        void getNewsFromServer();
    }

}
