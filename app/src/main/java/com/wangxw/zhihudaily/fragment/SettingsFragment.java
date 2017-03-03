package com.wangxw.zhihudaily.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.utils.PreferencesUtil;

/**
 * Created by wangxw on 2017/3/3 0003 15:13.
 * E-mail:wangxw725@163.com
 * function:
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //通过XML文件添加Preferences布局
        addPreferencesFromResource(R.xml.settings);


        initListener();
    }

    private void initListener() {
        getPreferenceManager().findPreference(PreferencesUtil.KEYS.KEY_CLEAR_CACHE).setOnPreferenceClickListener(this);
        getPreferenceManager().findPreference(PreferencesUtil.KEYS.KEY_CHECK_UPDATE).setOnPreferenceClickListener(this);
        getPreferenceManager().findPreference(PreferencesUtil.KEYS.KEY_ABOUT_ME).setOnPreferenceClickListener(this);
        getPreferenceManager().findPreference(PreferencesUtil.KEYS.KEY_FEEDBACK).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case PreferencesUtil.KEYS.KEY_CLEAR_CACHE:
                Snackbar.make(getActivity().getWindow().getDecorView(), R.string.title_clear_cache, Snackbar.LENGTH_SHORT).show();
                break;
            case PreferencesUtil.KEYS.KEY_CHECK_UPDATE:
                Snackbar.make(getActivity().getWindow().getDecorView(), R.string.title_check_update, Snackbar.LENGTH_SHORT).show();
                break;
            case PreferencesUtil.KEYS.KEY_ABOUT_ME:
                Snackbar.make(getActivity().getWindow().getDecorView(), R.string.title_about_me, Snackbar.LENGTH_SHORT).show();
                break;
            case PreferencesUtil.KEYS.KEY_FEEDBACK:
                Snackbar.make(getActivity().getWindow().getDecorView(), R.string.title_feedback, Snackbar.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
