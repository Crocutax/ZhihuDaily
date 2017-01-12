package com.wangxw.zhihudaily.base;

import android.os.Bundle;

/**
 * Created by wangxw on 2017/1/12 0012.
 * E-mail:wangxw725@163.com
 * function:
 */
public interface BaseIPresenter <V extends BaseIView> {

    void bindView(V view);

    /**初始化数据*/
    void initData(Bundle savedInstanceState);
}
