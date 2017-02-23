package com.wangxw.zhihudaily.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wangxw.zhihudaily.presenter.StoryDetailPresenter;
import com.wangxw.zhihudaily.utils.ActivityManager;

import butterknife.ButterKnife;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function:
 */
public abstract class BaseActivity<T extends BaseIPresenter> extends AppCompatActivity{

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addWindowFeature();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = (T)bindPresenter();
        initView(savedInstanceState);
        initListener();
        ActivityManager.addActivity(this);
    }

    protected abstract void addWindowFeature();

    /**获取布局资源ID*/
    protected abstract int getLayoutId();

    /**绑定Presenter*/
    protected abstract T bindPresenter();

    /**初始化View
     * @param savedInstanceState*/
    protected abstract void initView(Bundle savedInstanceState);

    /**初始化监听器*/
    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
    }
}
