package com.wangxw.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.Story;
import com.wangxw.zhihudaily.contract.StoryDetailContract;
import com.wangxw.zhihudaily.presenter.StoryDetailPresenter;

import java.util.List;

import butterknife.BindView;


public class StoryDetailActivity extends BaseActivity<StoryDetailContract.IPresenter> implements StoryDetailContract.IView {

    private static final String STORY = "STORY";
    @BindView(R.id.iv_detail_topimg)
    ImageView ivDetailTopimg;
    @BindView(R.id.tv_imgBelong)
    TextView tvImgSource;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.webview_story_content)
    WebView webviewStoryContent;
    @BindView(R.id.ll_loading)
    FrameLayout llLoading;

    private List<String> imgUrlList;

    public static Intent getIntent(Context context, Story story) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(STORY, story);
        return intent;
    }

    @Override
    protected void addWindowFeature() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_story_detail;
    }

    @Override
    protected StoryDetailPresenter bindPresenter() {
        return new StoryDetailPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setSupportActionBar(toolbar);
        toolbarLayout.setTitle(" ");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("");
                onBackPressed();
            }
        });

        //从intent中取出数据
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Story story = (Story) intent.getSerializableExtra(STORY);

        if (story != null) {
            mPresenter.initData(story);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void inlfateData(NewsDetail newsDetail) {
        //有可能出现无图的情况
        if(!TextUtils.isEmpty(newsDetail.getImage())) {
            Glide.with(this)
                    .load(newsDetail.getImage())
                    .into(ivDetailTopimg);
            tvImgSource.setText(newsDetail.getImage_source());
        }else {
            ivDetailTopimg.setImageResource(R.drawable.splash_default_img);
        }

        tvDetailTitle.setText(newsDetail.getTitle());

        initWebview(newsDetail.getBody());
    }

    private void initWebview(String htmlContent) {
        WebSettings settings = webviewStoryContent.getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        settings.setAppCacheEnabled(true);  //支持缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //缓存优先模式
        settings.setAppCachePath(getDir("chache",MODE_PRIVATE).getPath());
        settings.setDomStorageEnabled(true);

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + htmlContent + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");

        webviewStoryContent.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_share:

                break;
            case R.id.menu_star:

                break;
            case R.id.menu_comment:

                break;
            case R.id.menu_like:

                break;
            default:
                break;
        };

        return true;
    }

    @Override
    public void setLoadingViewVisible(boolean isVisible) {
        pbLoading.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,R.anim.anim_fade_out);
    }
}