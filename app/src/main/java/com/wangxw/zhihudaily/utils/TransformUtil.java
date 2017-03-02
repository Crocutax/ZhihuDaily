package com.wangxw.zhihudaily.utils;


import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by wangxw on 2017/3/2 0002 14:40.
 * E-mail:wangxw725@163.com
 * function: Observable转换工具类
 */
public class TransformUtil {


    public static <T>Observable.Transformer<T, T> defaultSchedulers(){

        return new Observable.Transformer<T, T>(){

            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
