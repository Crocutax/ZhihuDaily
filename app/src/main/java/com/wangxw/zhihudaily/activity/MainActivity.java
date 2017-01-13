package com.wangxw.zhihudaily.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.orhanobut.logger.Logger;
import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.adapter.MainRecycleViewAdapter;
import com.wangxw.zhihudaily.adapter.MainViewPagerAdapter;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.bean.LatestNews;
import com.wangxw.zhihudaily.bean.Story;
import com.wangxw.zhihudaily.contract.MainContract;
import com.wangxw.zhihudaily.presenter.MainPresenter;
import com.wangxw.zhihudaily.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vp_main_topstories)
    RollPagerView vpMainTopstories;
    @BindView(R.id.ll_main_topstories_dots)
    LinearLayout llMainTopstoriesDots;
    @BindView(R.id.rv_main_stories)
    RecyclerView rvMainStories;
    @BindView(R.id.srl_main_refresh)
    SwipeRefreshLayout srlMainRefresh;

    @Override
    protected void addWindowFeature() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter bindPresenter() {
        return new MainPresenter(this);
    }


    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        srlMainRefresh.setColorSchemeResources(R.color.colorPrimary);

        vpMainTopstories.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Logger.i("屏幕宽:"+getWindowManager().getDefaultDisplay().getWidth()+"...ViewPager宽:"+vpMainTopstories.getWidth());
                vpMainTopstories.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    protected void initListener() {
        drawerLayout.addDrawerListener(this);
        navView.setNavigationItemSelectedListener(this);
        srlMainRefresh.setOnRefreshListener(this);
        srlMainRefresh.post(new Runnable() {
            @Override
            public void run() {
                srlMainRefresh.setRefreshing(true);
                mPresenter.getNewsFromServer();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_night_mode:
                Snackbar.make(getWindow().getDecorView(), R.string.action_night_mode, Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Snackbar.make(getWindow().getDecorView(), R.string.action_settings, Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        ;
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        Logger.i("Drawer-Open");
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        Logger.i("Drawer-Closed");
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onRefresh() {
        mPresenter.getNewsFromServer();
        Toast.makeText(this, "onRefresh", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initViewData(LatestNews latestNews) {
        srlMainRefresh.setRefreshing(false);
        //todo 如果刷新数据源,需要多次new adapter吗?
        MainViewPagerAdapter pagerAdapter = new MainViewPagerAdapter(latestNews.getTop_stories());
        vpMainTopstories.setAdapter(pagerAdapter);

        rvMainStories.setLayoutManager(new LinearLayoutManager(this));
        MainRecycleViewAdapter recycleViewAdapter = new MainRecycleViewAdapter(getRecycleViewDatas(latestNews));
        rvMainStories.setAdapter(recycleViewAdapter);
        Logger.i("Story数量:"+getRecycleViewDatas(latestNews).size());
    }

    /**
     *
     * @param latestNews
     * @return
     */
    private List<Story> getRecycleViewDatas(LatestNews latestNews) {
        List<Story> storyList = latestNews.getStories();

        //日期Item
        Story story = new Story();
        story.setStoryDateItem(true);
        story.setTitle(TimeUtil.formatData(latestNews.getDate()));

        storyList.add(0,story);
        return storyList;
    }


}
