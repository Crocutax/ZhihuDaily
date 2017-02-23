package com.wangxw.zhihudaily.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangxw on 2017/2/23 0023 17:51.
 * E-mail:wangxw725@163.com
 * function:
 */
public class PreferencesUtil {

    private static SharedPreferences sp;
    private static final String SP_FILE_NAME = "config";


    public interface KEYS{
        String KEY_NIGHT_MODE = "KEY_NIGHT_MODE";
        String KEY_FONT_SIZE = "KEY_FONT_LARGE";
    }


    //putString
    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    //getString
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    //putBoolean
    public static void putBoolean(Context context, String key, Boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    //getBoolean
    public static boolean getBoolean(Context context, String key, Boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    //putInt
    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).apply();
    }

    //getInt
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    //清除SharedPreferences数据
    public static void remove(Context context, String key) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().remove(key);
    }
}
