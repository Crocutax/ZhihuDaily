package com.wangxw.zhihudaily.api;


import com.wangxw.zhihudaily.Constants;
import com.wangxw.zhihudaily.MyApplication;
import com.wangxw.zhihudaily.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function:
 */
public class ApiManager {


    private static ZhihuApi mZhihuApi;
    /**默认网络超时时间*/
    private static final int DEFAULT_TIMEOUT = 10;
    /**默认缓存大小:10M*/
    private static final int DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;

    /**缓存控制 拦截器*/
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor(){

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if(NetworkUtil.networkIsConnect(MyApplication.getAppContext())){
                //// 在线缓存时间为1分钟
                int maxAge = 60;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                // 离线时缓存时间为4周
                int maxStale = 60 * 60 * 24 * 28;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public static ZhihuApi getZhihuApi(){
        if(mZhihuApi==null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //配置网络请求超时时间
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
            builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

            //配置缓存
            File okHttpCache = new File(MyApplication.getAppContext().getCacheDir(), "OkHttpCache");
            builder.cache(new Cache(okHttpCache,DEFAULT_CACHE_SIZE));

            //配置拦截器
            builder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);

            OkHttpClient okHttpClient = builder.build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.HTTP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mZhihuApi = retrofit.create(ZhihuApi.class);
        }
        return mZhihuApi;
    }
}





















