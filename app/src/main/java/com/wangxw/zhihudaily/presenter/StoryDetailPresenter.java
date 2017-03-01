package com.wangxw.zhihudaily.presenter;

import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.api.ApiManager;
import com.wangxw.zhihudaily.base.BaseIView;
import com.wangxw.zhihudaily.base.BasePresenter;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.Story;
import com.wangxw.zhihudaily.contract.StoryDetailContract;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wangxw on 2017/2/23 0023 13:43.
 * E-mail:wangxw725@163.com
 * function:
 */
public class StoryDetailPresenter extends BasePresenter<StoryDetailContract.IView> implements StoryDetailContract.IPresenter {

    public StoryDetailPresenter(StoryDetailContract.IView mIView) {
        super(mIView);
    }

    @Override
    public void bindView(BaseIView view) {
        mView = (StoryDetailContract.IView) view;
    }

    @Override
    public void initData(Story story) {
        ApiManager.getZhihuApi()
                .getNewsDetail(story.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoadingViewVisible(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setLoadingViewVisible(false);
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        //todo 缓存
                        mView.inlfateData(newsDetail);
                    }
                });
    }

}
