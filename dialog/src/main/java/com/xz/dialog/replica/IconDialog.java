package com.xz.dialog.replica;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.dialog.R;
import com.xz.dialog.base.BaseDialog;

/**
 * @author czr
 * @date 2020/4/7
 * <p>
 * 仿原生对话框
 */
public class IconDialog extends BaseDialog {

    private ImageView iconType;
    private TextView tvMsg;
    private TextView tvNegative;
    private TextView tvPositive;

    public IconDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_icon;
    }

    @Override
    protected void initView() {
        iconType = findViewById(R.id.icon_type);
        tvMsg = findViewById(R.id.tv_msg);
        tvNegative = findViewById(R.id.tv_negative);
        tvPositive = findViewById(R.id.tv_positive);
    }

    @Override
    protected void initData() {
        iconType.setColorFilter(Color.RED);
    }


    public static class Builder {
        private IconDialog dialog;

        public Builder(Context context) {
            dialog = new IconDialog(context);
        }

        public IconDialog create() {
            return dialog;
        }
    }
}
