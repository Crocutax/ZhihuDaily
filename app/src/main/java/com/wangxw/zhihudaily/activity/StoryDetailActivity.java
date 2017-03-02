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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.StoryExtra;
import com.wangxw.zhihudaily.contract.StoryDetailContract;
import com.wangxw.zhihudaily.presenter.StoryDetailPresenter;

import butterknife.BindString;
import butterknife.BindView;


public class StoryDetailActivity extends BaseActivity<StoryDetailContract.IPresenter> implements StoryDetailContract.IView {

    private static final String STORY_ID = "STORY_ID";
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

    @BindString(R.string.menu_collect)
    String collectStr;
    @BindString(R.string.menu_collected)
    String collectedStr;
    @BindString(R.string.menu_praise)
    String praiseStr;
    @BindString(R.string.menu_praised)
    String praisedStr;


    private TextView tvCommentNum;
    private TextView tvLikeNum;

    public static Intent getIntent(Context context,int storyId) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(STORY_ID, storyId);
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

        //返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //从intent中取出数据
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        int storyId = intent.getIntExtra(STORY_ID, -1);
        if(storyId!=-1) {
            mPresenter.initData(storyId);
            mPresenter.getStoreExtra(storyId);
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

        //更新评论MenuItem布局
        MenuItem commentItem = menu.findItem(R.id.menu_comment);
        commentItem.setActionView(R.layout.action_comment);

        //更新点赞MenuItem布局
        MenuItem praiseItem = menu.findItem(R.id.menu_like);
        praiseItem.setActionView(R.layout.action_praise);

        //获取评论 和 点赞的布局View
        tvCommentNum = (TextView) commentItem.getActionView();
        tvLikeNum = (TextView) praiseItem.getActionView();

        //评论点击事件
        tvCommentNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryDetailActivity.this, "跳往评论页面", Toast.LENGTH_SHORT).show();
            }
        });

        //赞点击事件
        tvLikeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StoryDetailActivity.this, "赞+1", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d(item.getTitle());
        switch (item.getItemId()){
            case R.id.menu_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_collect:
                if(TextUtils.equals(collectStr,item.getTitle())){
                    //+赞
                    item.setIcon(R.drawable.collected);
                    item.setTitle(collectedStr);
                    //todo 数据库添加
                    Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                }else {
                    //-赞
                    item.setIcon(R.drawable.collect);
                    item.setTitle(collectStr);
                    //todo 数据库移除
                    Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        };

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLoadingViewVisible(boolean isVisible) {
        pbLoading.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void initStoryExtraView(StoryExtra storyExtra) {
        //todo 检查是否有数据

        //是否收藏从本地数据库查询
        //todo
        Logger.d(storyExtra.toString());
        tvCommentNum.setText(formatNum(storyExtra.getComments()));
        tvLikeNum.setText(formatNum(storyExtra.getPopularity()));
    }

    private String formatNum(int number) {
        if (number < 1000) {
            return number + "";
        }else {
            return number / 1000 +"k";
        }
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0,R.anim.anim_fade_out);
    }
}
