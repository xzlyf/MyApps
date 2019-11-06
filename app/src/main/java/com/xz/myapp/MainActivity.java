package com.xz.myapp;

import android.os.Bundle;
import android.widget.Button;

import com.xz.myapp.base.BaseActivity;
import com.xz.xzwidget.XType;
import com.xz.xzwidget.dialog.XOnClickListener;
import com.xz.xzwidget.dialog.XzInputDialog;
import com.xz.xzwidget.dialog.XzTipsDialog;
import com.xz.xzwidget.toast.XToast;
import com.xz.xzwidget.widget.IpEditView;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    public void btn5(){
        XToast.showToast(this,ipAddress.getIp(),XType.XTOAST_TYPE_SUCCESS);
    }
}
