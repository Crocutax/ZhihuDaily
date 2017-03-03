package com.wangxw.zhihudaily.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wangxw.zhihudaily.R;
import com.wangxw.zhihudaily.base.BaseActivity;
import com.wangxw.zhihudaily.bean.StoryExtra;
import com.wangxw.zhihudaily.contract.CommentsContract;
import com.wangxw.zhihudaily.presenter.CommentsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsActivity extends BaseActivity<CommentsContract.IPresenter> implements CommentsContract.IView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final String STORY_EXTRA = "StoryExtra";
    private static final String STORY_ID = "storyId";
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;

    private int mStoryId;

    public static Intent getIntent(Context context, int storyId, StoryExtra storyExtra) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(STORY_ID, storyId);
        Bundle bundle = new Bundle();
        bundle.putParcelable(STORY_EXTRA, storyExtra);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void addWindowFeature() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comments;
    }

    @Override
    protected CommentsPresenter bindPresenter() {
        return new CommentsPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            mStoryId = intent.getIntExtra(STORY_ID, -1);
            StoryExtra storyExtra = intent.getParcelableExtra(STORY_EXTRA);
            getSupportActionBar().setTitle(String.format(getString(R.string.comments_count), storyExtra.getComments()));
        }



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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_comment) {
            Toast.makeText(this, "进入评论界面", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLoadingViewVisible(boolean isVisible) {

    }

    @Override
    public void initLongCommentsView() {

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.anim_fade_out);
    }


}
