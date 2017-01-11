package com.wangxw.zhihudaily.utils;

import com.wangxw.zhihudaily.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    public static List<BaseActivity> activities = new ArrayList<>();

    public static void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (BaseActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}