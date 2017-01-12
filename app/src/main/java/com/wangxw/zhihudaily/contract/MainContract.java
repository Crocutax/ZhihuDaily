package com.wangxw.zhihudaily.contract;

import com.wangxw.zhihudaily.base.BaseIView;
import com.wangxw.zhihudaily.base.BaseIPresenter;
import com.wangxw.zhihudaily.bean.NewsItem;

import java.util.List;

/**
 * Created by wangxw on 2017/1/12 0012.
 * E-mail:wangxw725@163.com
 * function:
 */
public interface MainContract {

    interface View extends BaseIView {
        void initViewPager(List<NewsItem> topNews);
    }

    interface Presenter extends BaseIPresenter {

        void getNewsFromServer();
    }

}
