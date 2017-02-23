package com.wangxw.zhihudaily.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.adapter.MainRecycleViewAdapter;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.bean.LatestNews;
import com.wangxw.zhihudaily.bean.Story;
import com.wangxw.zhihudaily.bean.TopStory;
import com.wangxw.zhihudaily.contract.MainContract;
import com.wangxw.zhihudaily.presenter.MainIPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainContract.IPresenter> implements MainContract.IView, NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv_main_stories)
    RecyclerView rvMainStories;
    @BindView(R.id.srl_main_refresh)
    SwipeRefreshLayout srlMainRefresh;
    private LinearLayoutManager rvLayoutManager;
    private MainRecycleViewAdapter rvAdapter;

    @Override
    protected void addWindowFeature() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainIPresenter bindPresenter() {
        return new MainIPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        srlMainRefresh.setColorSchemeResources(R.color.colorPrimary);

        //配置RecycleView
        rvLayoutManager = new LinearLayoutManager(this);
        rvMainStories.setLayoutManager(rvLayoutManager);
        rvAdapter = new MainRecycleViewAdapter();
        rvMainStories.setAdapter(rvAdapter);
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

        rvMainStories.addOnScrollListener(MyScrollListener);

        rvAdapter.setTopStoryItemClickListener(new MainRecycleViewAdapter.TopStoryItemClickListener() {
            @Override
            public void onTopStoryItemClick(TopStory topStory) {
                Story story = new Story();
                story.setTitle(topStory.getTitle());
                story.setId(topStory.getId());
                story.setImages(new String[]{topStory.getImage()});
                startActivity(StoryDetailActivity.getIntent(MainActivity.this,story));
            }
        });

        rvAdapter.setStoryItemClickListener(new MainRecycleViewAdapter.StoryItemClickListener() {
            @Override
            public void onStoryItemClick(Story story) {
                //todo 改变已读标记,存储进数据库
                startActivity(StoryDetailActivity.getIntent(MainActivity.this,story));
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
    }

    @Override
    public void onDrawerClosed(View drawerView) {
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
        rvAdapter.setData(latestNews);
    }

    @Override
    public void loadBeforeStories(LatestNews latestNews) {
        rvAdapter.addStories(latestNews);
    }


    private OnScrollListener MyScrollListener  = new  OnScrollListener(){

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState== RecyclerView.SCROLL_STATE_IDLE
                    && (rvLayoutManager.findLastVisibleItemPosition()==rvAdapter.getItemCount()-1)){
                mPresenter.loadBeforStories();
            }
        }
    };


}
