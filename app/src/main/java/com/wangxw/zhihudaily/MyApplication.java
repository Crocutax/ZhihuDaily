package com.wangxw.zhihudaily;

import android.app.Application;
import android.content.Context;
import android.content.MutableContextWrapper;

import com.orhanobut.logger.Logger;

/**
 * Created by wangxw on 2017/1/8.
 * E-mail : wangxw725@163.com
 * function :
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("wxw");
        mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }

}
