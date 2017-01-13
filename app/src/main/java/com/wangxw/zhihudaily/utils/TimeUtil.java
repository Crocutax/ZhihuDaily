package com.wangxw.zhihudaily.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by wangxw on 2017/1/13 0013.
 * E-mail:wangxw725@163.com
 * function: 时间相关 工具类
 */
public class TimeUtil {

    /**
     * 获取用于显示的日期
     *
     * @return 返回格式 yyyy年MM月dd日
     */
    public static String formatData(String date) {
        if (date.length() != 8) {
            return "";
        }
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));
        String weekDay = new SimpleDateFormat("EEEE", Locale.CHINA).format(calendar.getTime());
        return new StringBuilder()
                .append(year).append("年")
                .append(month).append("月")
                .append(day).append("日")
                .append(weekDay).toString();
    }


}
