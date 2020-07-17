package com.xz.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioRouting;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xz.base.BaseActivity;
import com.xz.myapp.R;
import com.xz.myapp.entity.ClassEntity;
import com.xz.myapp.entity.MyMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class EventBusActivity extends BaseActivity {


    @BindView(R.id.tvTips)
    TextView tvTips;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_event_bus;
    }

    @Override
    public void initData() {
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销订阅者
        EventBus.getDefault().unregister(this);
    }


    /*
    EventBus 线程事件
    ThreadMode：POSTING  订阅者将在发布事件的同一线程中调用。这是默认值
    ThreadMode：MAIN     订阅者在主线程中执行
    ThreadMode：MAIN_ORDERED   按照发布顺序执行
    ThreadMode: BACKGROUND   如果发布在主线程,则新开一个线程,
    ThreadMode: ASYNC  新的独立线程中执行
    */


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void myEventBusMessageASYNC(MyMessageEvent myMessageEvent) {
        tvTips.setText("事件：" + myMessageEvent.name);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testBusMessage(ClassEntity classEntity) {
        tvTips.setText("另一个类型：" + classEntity.getName());

    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                MyMessageEvent event = new MyMessageEvent("小白");
                EventBus.getDefault().post(event);
                break;
            case R.id.btn2:
                ClassEntity entity = new ClassEntity("ClassEntity", EventBusActivity.class);
                EventBus.getDefault().post(entity);
                break;
            case R.id.btn3:
                startActivity(new Intent(EventBusActivity.this, SecondEventBusActivity.class));
                break;

        }
    }
}
