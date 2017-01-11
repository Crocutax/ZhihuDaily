package com.wangxw.zhihudaily.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by wangxw on 2017/1/11 0011.
 * E-mail:wangxw725@163.com
 * function:
 */
public class NetworkUtil {

    /**
     * 判断当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean networkIsConnect(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前连接的网络是否是wifi，并得到连接当前Wifi的信息
     *
     * @param context
     * @return
     */
    public static boolean networkIsWifi(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        if (info != null && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            Toast.makeText(context, "连接的wifi网络的id为：" + wifiInfo.getNetworkId(), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }
}
