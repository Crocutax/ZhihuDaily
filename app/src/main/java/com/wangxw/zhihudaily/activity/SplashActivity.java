package com.wangxw.zhihudaily.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wangxw.zhihudaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by wangxw on 2017/1/8.
 * E-mail : wangxw725@163.com
 * function :
 */
public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.iv_splash_img)
    ImageView ivSplashImg;
    @BindView(R.id.rl_splash_slogan)
    RelativeLayout rlSplashSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Animation bottomAnimation = AnimationUtils.makeInChildBottomAnimation(this);
        rlSplashSlogan.setAnimation(bottomAnimation);
        bottomAnimation.setDuration(1000);
        bottomAnimation.start();

        final Animator alphaAnimator = AnimatorInflater.loadAnimator(this, R.animator.anim_splash);
        alphaAnimator.setTarget(ivSplashImg);

        bottomAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                alphaAnimator.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        alphaAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
