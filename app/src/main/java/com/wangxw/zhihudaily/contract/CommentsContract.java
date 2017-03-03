package com.wangxw.zhihudaily.contract;

import com.wangxw.zhihudaily.base.BaseIPresenter;
import com.wangxw.zhihudaily.base.BaseIView;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.StoryExtra;

/**
 * Created by wangxw on 2017/1/13 0011.
 * E-mail:wangxw725@163.com
 * function:
 */
public interface CommentsContract {

    interface IView extends BaseIView {

        void setLoadingViewVisible(boolean isVisible);

        void initLongCommentsView();
    }

    interface IPresenter extends BaseIPresenter {

        void getLongCommentsData(int storyId);
    }

}
