package com.xz.myapp;

import android.view.View;
import android.widget.Button;

import com.xz.base.BaseActivity;
import com.xz.ui.widget.BesselView;
import com.xz.ui.widget.SlideHorizontalView;
import com.xz.ui.widget.SportsView;

import java.util.Random;

import butterknife.BindView;

public class WidgetActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.sports_view)
    SportsView sportsView;
    @BindView(R.id.sports_view_2)
    SportsView sportsView2;

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.slide_h)
    SlideHorizontalView slideHorizontalView;
    @BindView(R.id.bessel_view)
    BesselView besselView;

    private boolean isClick = false;
    private int randomNum;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_widget;
    }

    @Override
    public void initData() {
        btn1.setOnClickListener(this);
        Random random = new Random();
        randomNum = random.nextInt(100);
        slideHorizontalView.setOnScrollListener(new SlideHorizontalView.OnScrollListener() {
            @Override
            public void onScroll(int value) {
                sportsView.setProgress(value);
                sportsView2.setProgress(value);
            }
        });
        besselView.startAnim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                if (isClick) {
                    btn1.animate().scaleY(1f).scaleX(1f);
                    isClick = false;
                    slideHorizontalView.startAnim(randomNum, 0);
                    sportsView.startAnim(randomNum, 0);
                    sportsView2.startAnim(randomNum, 0);


                } else {
                    btn1.animate().scaleY(0.7f).scaleX(0.8f);
                    isClick = true;
                    slideHorizontalView.startAnim(0, randomNum);
                    sportsView.startAnim(0, randomNum);
                    sportsView2.startAnim(0, randomNum);
                }
                break;
        }
    }


}
