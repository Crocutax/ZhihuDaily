package com.wangxw.zhihudaily.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.base.BaseIPresenter;
import com.wangxw.zhihudaily.fragment.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void addWindowFeature() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected BaseIPresenter bindPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_settings);

        getFragmentManager().beginTransaction().replace(R.id.fl_contentview,new SettingsFragment()).commitAllowingStateLoss();
    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.anim_fade_out);
    }

}
