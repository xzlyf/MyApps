package com.xz.myapp.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xz.base.BaseActivity;
import com.xz.myapp.R;
import com.xz.utils.hardware.SystemInfoUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemInfoActivity extends BaseActivity {


    @BindView(R.id.tv_tex)
    TextView tvTex;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_system_info;
    }

    @Override
    public void initData() {
    }

}
