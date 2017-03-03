package com.wangxw.zhihudaily.presenter;

import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.api.ApiManager;
import com.wangxw.zhihudaily.base.BaseIView;
import com.wangxw.zhihudaily.base.BasePresenter;
import com.wangxw.zhihudaily.bean.Comment;
import com.wangxw.zhihudaily.contract.CommentsContract;
import com.wangxw.zhihudaily.utils.TransformUtil;

import java.util.List;

import rx.Subscriber;

/**
 * Created by wangxw on 2017/3/3 0003 11:55.
 * E-mail:wangxw725@163.com
 * function:
 */
public class CommentsPresenter extends BasePresenter<CommentsContract.IView> implements CommentsContract.IPresenter {

    public CommentsPresenter(CommentsContract.IView mView) {
        super(mView);
    }

    @Override
    public void bindView(BaseIView view) {
        mView = (CommentsContract.IView) view;
    }

    @Override
    public void getLongCommentsData(int storyId) {
        ApiManager.getZhihuApi().getLongComments(storyId)
                .compose(TransformUtil.<List<Comment>>defaultSchedulers())
                .subscribe(new Subscriber<List<Comment>>() {
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
                    public void onNext(List<Comment> comments) {
                        Logger.d(comments.toString());
                    }
                });
    }
}
