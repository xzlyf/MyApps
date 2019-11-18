package com.xz.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xz.myapp.base.BaseActivity;

public class ButtonActitvity extends BaseActivity {


    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_button_actitvity;
    }
}
