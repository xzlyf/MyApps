package com.xz.myapp;

import android.view.View;
import android.widget.Button;

import com.xz.base.BaseActivity;
import com.xz.widget.widget.SlideHorizontalView;
import com.xz.widget.widget.SportsView;

import butterknife.BindView;

public class WidgetActivity extends BaseActivity implements View.OnClickListener {

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
        return R.layout.activity_widget;
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
                    btn1.animate().scaleY(1f).scaleX(1f);
                    isClick = false;
                    slideHorizontalView.startAnim(65, 0);
                    sportsView.startAnim(65, 0);


                } else {
                    btn1.animate().scaleY(0.7f).scaleX(0.8f);
                    isClick = true;
                    slideHorizontalView.startAnim(0, 65);
                    sportsView.startAnim(0, 65);
                }
                break;
        }
    }


}
