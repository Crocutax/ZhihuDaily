package com.wangxw.zhihudaily.base;


/**
 * Created by wangxw on 2017/1/11.
 * E-mail : wangxw725@163.com
 * function :
 */
public abstract class BasePresenter<V extends BaseIView> {

    protected V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

}
