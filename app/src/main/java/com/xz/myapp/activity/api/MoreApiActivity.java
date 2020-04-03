package com.xz.myapp.activity.api;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatDelegate;

import com.xz.base.BaseActivity;
import com.xz.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.alterac.blurkit.BlurLayout;

public class MoreApiActivity extends BaseActivity {


    @BindView(R.id.blurLayout)
    BlurLayout blurLayout;
    @BindView(R.id.image_android)
    ImageView imageAndroid;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_more_api;
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

}
