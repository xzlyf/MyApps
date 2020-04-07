package com.xz.dialog.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.xz.dialog.R;

/**
 * @author czr
 * @date 2020/4/7
 */
public abstract class BaseDialog extends Dialog {
    private Context mContext;

    public BaseDialog(Context context) {
        this(context, R.style.customDialog);
    }

    private BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        lp.width = (int) (dm.widthPixels * 0.8);//宽度始终是屏幕的80%
        lp.dimAmount = 0.2f;//背景暗度
        window.setAttributes(lp);
        initView();
        initData();

    }


    /**
     * 布局资源
     *
     * @return
     */
    protected abstract int getLayoutResource();

    /**
     * 控件初始化
     */
    protected abstract void initView();

    /**
     * 数据初始化
     */
    protected abstract void initData();


}
