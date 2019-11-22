package com.xz.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.xz.widget.dialog.XOnClickListener;
import com.xz.widget.dialog.XzLoadingDialog;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private Activity mContext;
    private XzLoadingDialog xzLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        mContext = this;
        //设置是否开启返回homeAsUp按钮
        if (homeAsUpEnabled()) {
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setHomeButtonEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);

            }
        }
        initData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从集合中移除
        BaseApplication.getInstance().finishActivity(this);
    }

    public abstract boolean homeAsUpEnabled();

    public abstract int getLayoutResource();

    public abstract void initData();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //id不要写错，前面要加android
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void showLoading(String text) {
        if (xzLoadingDialog == null) {
            xzLoadingDialog = new XzLoadingDialog.Builder(this)
                    .setCancelEnable(true)
                    .setCancelText(text)
                    .setCancelOnClickListener(new XOnClickListener() {
                        @Override
                        public void onClick(int viewId, String s, int position) {

                        }
                    })
                    .create();
        }

        xzLoadingDialog.show();
    }

    @Override
    public void disLoading() {
        if (xzLoadingDialog != null) {
            xzLoadingDialog.dismiss();
        }
    }

    @Override
    public void sToast(String text) {
    }

    @Override
    public void lToast(String text) {
    }

    @Override
    public void sDialog(String title, String msg, int type) {

    }

    @Override
    public void dDialog() {

    }
}
