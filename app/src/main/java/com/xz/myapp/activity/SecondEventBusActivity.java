package com.xz.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.xz.base.BaseActivity;
import com.xz.myapp.R;
import com.xz.myapp.entity.MyMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

public class SecondEventBusActivity extends BaseActivity {


    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_second_event_bus;
    }

    @Override
    public void initData() {
    }


    @OnClick(R.id.btn3)
    public void onViewClick(View view) {
        MyMessageEvent myMessageEvent = new MyMessageEvent("我是新页面小黑");
        EventBus.getDefault().postSticky(myMessageEvent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
