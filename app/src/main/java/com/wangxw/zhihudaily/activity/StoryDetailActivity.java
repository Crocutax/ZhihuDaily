package com.wangxw.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.bean.NewsDetail;
import com.wangxw.zhihudaily.bean.Story;
import com.wangxw.zhihudaily.contract.StoryDetailContract;
import com.wangxw.zhihudaily.presenter.StoryDetailPresenter;
import com.wangxw.zhihudaily.utils.NetworkUtil;
import com.wangxw.zhihudaily.utils.PreferencesUtil;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
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
        webviewStoryContent.addJavascriptInterface(this,"wangxw");

       /* String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + htmlContent + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");*/

        //拼接html内容
        StringBuilder sbHtml = new StringBuilder("<!doctype html>\n<html><head>\n<meta charset=\"utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width,user-scalable=no\">");

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">\n";
        String img_replace = "<script src=\"file:///android_asset/img_replace.js\"></script>\n";
        String video = "<script src=\"file:///android_asset/video.js\"></script>\n";
        String zepto = "<script src=\"file:///android_asset/zepto.min.js\"></script>\n";
        String autoLoadImage = "onload=\"onLoaded()\"";

        //根据是否WIfi + 自动加载图片设置 两个条件判断
        boolean autoLoad = NetworkUtil.networkIsWifi(this);
        boolean nightMode = PreferencesUtil.getBoolean(this,PreferencesUtil.KEYS.KEY_NIGHT_MODE,false);
        boolean largeFont = PreferencesUtil.getBoolean(this,PreferencesUtil.KEYS.KEY_FONT_SIZE,false);

        sbHtml.append(css)
                .append(zepto)
                .append(img_replace)
                .append(video)
                .append("</head><body className=\"\"")
                .append(autoLoad ? autoLoadImage : "")
                .append(" >")
                .append(htmlContent);
        if (nightMode) {
            String night = "<script src=\"file:///android_asset/night.js\"></script>\n";
            sbHtml.append(night);
        }
        if (largeFont) {
            String bigFont = "<script src=\"file:///android_asset/large-font.js\"></script>\n";
            sbHtml.append(bigFont);
        }
        sbHtml.append("</body></html>");
        String html = sbHtml.toString();

        html = html.replace("<div class=\"img-place-holder\">", "");
        html = replaceImgTagFromHTML(html, autoLoad, nightMode);
        Logger.d(html);

        webviewStoryContent.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    /**
     * 替换html中的<img class="content-image">标签的属性
     *
     * @param html
     * @return
     */
    protected String replaceImgTagFromHTML(String html, boolean autoLoad, boolean nightMode) {
        imgUrlList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements es = doc.getElementsByTag("img");
        for (Element e : es) {
            if (!TextUtils.equals("avatar",e.attr("class"))) {
                String imgUrl = e.attr("src");
                imgUrlList.add(imgUrl);
                String src = String.format("file:///android_asset/default_pic_content_image_%s_%s.png",
                        autoLoad ? "loading" : "download",
                        nightMode ? "dark" : "light");
                e.attr("src", src);
                e.attr("zhimg-src", imgUrl);
                e.attr("onclick", "onImageClick(this)");
            }
        }
        return doc.html();
    }

    @JavascriptInterface
    public void loadImage(final String imgPath) {
        if (TextUtils.isEmpty(imgPath)) {
            return;
        }
        Logger.d("加载图片");
        webviewStoryContent.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(StoryDetailActivity.this).load(imgPath)
                        .downloadOnly(new SimpleTarget<File>() {
                            @Override
                            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                                String str = "file://" + resource.getAbsolutePath();//加载完成的图片地址
                                try {
                                    String[] arrayOfString = new String[2];
                                    arrayOfString[0] = URLEncoder.encode(imgPath,"UTF-8");//旧url
                                    arrayOfString[1] = str;
                                    onImageLoadingComplete("onImageLoadingComplete", arrayOfString);
                                } catch (Exception e) {
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void setLoadingViewVisible(boolean isVisible) {
        pbLoading.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
    }


    @JavascriptInterface
    public void clickHtmlImg(String imgUrl){
        Toast.makeText(this, "imgUrl:"+imgUrl, Toast.LENGTH_SHORT).show();
    }

    public final void onImageLoadingComplete(String funName, String[] paramArray) {
        String str = "'" + TextUtils.join("','", paramArray) + "'";
        webviewStoryContent.loadUrl("javascript:" + funName + "(" + str + ");");
    }

}
