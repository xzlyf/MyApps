package com.xz.myapp;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.xz.base.BaseActivity;
import com.xz.widget.widget.SlideHorizontalView;
import com.xz.widget.widget.SportsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.sports_view)
    SportsView sportsView;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.slide_h)
    SlideHorizontalView slideHorizontalView;

    private boolean isClick = false;


    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_anim;
    }

    @Override
    public void initData() {
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                if (isClick) {
                    btn1.animate().translationXBy(-200).scaleY(1f).scaleX(1f);
                    isClick = false;
                    showSportViewAnim(65, 0);

                } else {
                    btn1.animate().translationX(200).scaleY(0.8f).scaleX(0.8f);
                    isClick = true;
                    showSportViewAnim(0, 65);
                }
                break;
        }
    }

    private void showSportViewAnim(int startValue, int endValue) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(sportsView, "progress", startValue, endValue);
        animator.setDuration(800);
        animator.setInterpolator(new OvershootInterpolator(1f));//有回弹效果
//        animator.setInterpolator(new AnticipateOvershootInterpolator(1));
        animator.start();
    }


}
