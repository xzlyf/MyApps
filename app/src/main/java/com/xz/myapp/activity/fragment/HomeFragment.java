package com.xz.myapp.activity.fragment;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.xz.base.BaseFragment;
import com.xz.myapp.R;
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

/**
 * @author czr
 * @date 2020/4/3
 */
public class HomeFragment extends BaseFragment {
    private RecyclerView recycler;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View rootView) {
        recycler = rootView.findViewById(R.id.recycler);
    }

    @Override
    protected void initDate(Context mContext) {
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
