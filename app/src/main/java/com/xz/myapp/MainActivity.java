package com.xz.myapp;

import android.content.Intent;
import android.widget.Button;

import com.xz.base.BaseActivity;
import com.xz.myapp.activity.BezierActivity;
import com.xz.myapp.activity.ButtonActitvity;
import com.xz.myapp.activity.SystemInfoActivity;
import com.xz.myapp.activity.WebViewActivity;
import com.xz.myapp.activity.api.MoreApiActivity;
import com.xz.myapp.activity.api.TencentOpenActivity;
import com.xz.myapp.activity.WidgetActivity;
import com.xz.ui.XType;
import com.xz.ui.dialog.XOnClickListener;
import com.xz.ui.dialog.XzInputDialog;
import com.xz.ui.dialog.XzTipsDialog;
import com.xz.ui.toast.XToast;
import com.xz.ui.widget.IpEditView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.ipAddress)
    IpEditView ipAddress;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.btn11)
    Button btn11;
    @BindView(R.id.btn12)
    Button btn12;

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
    }

    @OnClick(R.id.btn1)
    public void btn1() {
        showLoading("稍安勿躁");
    }

    @OnClick(R.id.btn2)
    public void btn2() {
        XzTipsDialog dialog = new XzTipsDialog.Builder(this)
                .setContent("Bit definitions for an integer defining the basic content type of text held in an Editable object. Supported classes may be combined with variations and flags to indicate desired behaviors.\n")
                .setCancelOnclickListener("知道了", new XOnClickListener() {
                    @Override
                    public void onClick(int viewId, String s, int position) {

                    }
                })
                .create();
        dialog.show();
    }


    @OnClick(R.id.btn3)
    public void btn3() {
        XzInputDialog dialog = new XzInputDialog.Builder(this)
                .setHint("请输入***")
                .setSubmitOnClickListener("提交", new XOnClickListener() {
                    @Override
                    public void onClick(int viewId, String s, int position) {

                    }
                })
                .create();
        dialog.show();
    }

    @OnClick(R.id.btn4)
    public void btn4() {
        XToast.showToast(this, "测试", XType.XTOAST_TYPE_TIPS);
    }

    @OnClick(R.id.btn5)
    public void btn5() {
        XToast.showToast(this, ipAddress.getIp(), XType.XTOAST_TYPE_SUCCESS);
    }

    @OnClick(R.id.btn6)
    public void btn6() {
        startActivity(new Intent(this, ButtonActitvity.class));
    }

    @OnClick(R.id.btn7)
    public void btn7() {
        startActivity(new Intent(this, SystemInfoActivity.class));
    }

    @OnClick(R.id.btn8)
    public void btn8() {
        startActivity(new Intent(this, WidgetActivity.class));
    }

    @OnClick(R.id.btn9)
    public void btn9() {
        startActivity(new Intent(this, BezierActivity.class));
    }

    @OnClick(R.id.btn10)
    public void btn10() {
        startActivity(new Intent(this, TencentOpenActivity.class));
    }
    @OnClick(R.id.btn11)
    public void btn11() {
        startActivity(new Intent(this, WebViewActivity.class));
    }
    @OnClick(R.id.btn12)
    public void btn12() {
        startActivity(new Intent(this, MoreApiActivity.class));
    }


}
