package com.xz.myapp;

import android.graphics.Color;
import android.os.SystemClock;

import com.xz.base.BaseActivity;
import com.xz.ui.button.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class ButtonActitvity extends BaseActivity {


    @BindView(R.id.load_btn)
    LoadingButton loadBtn;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_button_actitvity;
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.load_btn)
    public void loadBtn() {
        loadBtn.setText("开始下载");
        loadBtn.setOverColor(Color.parseColor("#DEB887"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 101; i++) {
                    loadBtn.updateValue(i);
                    SystemClock.sleep(60);
                }
            }
        }).start();
    }

}
