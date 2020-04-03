package com.xz.myapp;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xz.base.BaseActivity;
import com.xz.myapp.activity.BezierActivity;
import com.xz.myapp.activity.ButtonActitvity;
import com.xz.myapp.activity.SystemInfoActivity;
import com.xz.myapp.activity.WebViewActivity;
import com.xz.myapp.activity.WidgetActivity;
import com.xz.myapp.activity.api.MoreApiActivity;
import com.xz.myapp.activity.api.TencentOpenActivity;
import com.xz.myapp.adapter.ClassAdapter;
import com.xz.myapp.entity.ClassEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    public boolean homeAsUpEnabled() {
        return false;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {


        List<ClassEntity> list = new ArrayList<>();
        list.add(new ClassEntity("自定义控件", WidgetActivity.class));
        list.add(new ClassEntity("按钮大全", ButtonActitvity.class));
        list.add(new ClassEntity("WebView开发", WebViewActivity.class));
        list.add(new ClassEntity("贝塞尔图形例子", BezierActivity.class));
        list.add(new ClassEntity("腾讯开发平台", TencentOpenActivity.class));
        list.add(new ClassEntity("优秀开源框架", MoreApiActivity.class));
        list.add(new ClassEntity("系统信息", SystemInfoActivity.class));
        ClassAdapter adapter = new ClassAdapter(mContext);
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.setAdapter(adapter);
        adapter.refresh(list);

    }

}
