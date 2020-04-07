package com.xz.dialog.replica;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xz.dialog.R;
import com.xz.dialog.base.BaseDialog;
import com.xz.dialog.event.NegativeOnClickListener;
import com.xz.dialog.event.PositiveOnClickListener;

/**
 * @author czr
 * @date 2020/4/7
 * <p>
 * 仿原生对话框
 */
public class IconDialog extends BaseDialog implements View.OnClickListener {
    /**
     * 图表样式开始========================================
     */
    public static int TYPE_ICON_MIC = R.drawable.icon_type_1;
    public static int TYPE_ICON_BUG = R.drawable.icon_type_2;
    public static int TYPE_ICON_BREAK = R.drawable.icon_type_3;
    public static int TYPE_ICON_MSG = R.drawable.icon_type_4;
    public static int TYPE_ICON_ERROR = R.drawable.icon_type_5;
    public static int TYPE_ICON_FILED = R.drawable.icon_type_6;
    public static int TYPE_ICON_TIPS = R.drawable.icon_type_7;
    public static int TYPE_ICON_SUCCESS = R.drawable.icon_type_8;
    public static int TYPE_ICON_LOCATION = R.drawable.icon_type_9;
    public static int TYPE_ICON_CAMERA = R.drawable.icon_type_10;
    public static int TYPE_ICON_LIKE = R.drawable.icon_type_11;
    public static int TYPE_ICON_WIFI = R.drawable.icon_type_12;
    /**
     * 图表样式结束========================================
     */
    private int mainColor;//主色调
    private int type;//图标类型
    private String content;//内容
    private NegativeOnClickListener negativeOnClickListener;//消极的点击
    private PositiveOnClickListener positiveOnClickListener;//积极的点击


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
        //默认配置
        mainColor = mContext.getResources().getColor(R.color.defaultMainColor);
        type = TYPE_ICON_TIPS;
        content = "This is Test content,you can use \"setText()\" to replace!";
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 刷新视图
     */
    private void refreshView() {
        iconType.setColorFilter(mainColor);
        tvNegative.setTextColor(mainColor);
        tvPositive.setTextColor(mainColor);

        iconType.setImageResource(type);
        tvMsg.setText(content);
        tvNegative.setOnClickListener(this);
        tvPositive.setOnClickListener(this);
        tvNegative.setOnTouchListener(onTouchListener);
        tvPositive.setOnTouchListener(onTouchListener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_negative) {
            if (negativeOnClickListener != null)
                negativeOnClickListener.OnClick(v);
        } else if (id == R.id.tv_positive) {
            if (positiveOnClickListener != null)
                positiveOnClickListener.OnClick(v);
        }

    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.9f);
                    v.animate().scaleY(0.9f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f);
                    v.animate().scaleY(1f);
                    break;
            }
            return false;
        }
    };


    public static class Builder {
        private IconDialog dialog;

        public Builder(Context context) {
            dialog = new IconDialog(context);
            dialog.create();
        }

        /**
         * 设置内容
         *
         * @param txt
         * @return
         */
        public Builder setContent(String txt) {
            dialog.content = txt;
            return this;
        }

        /**
         * 设置图标
         *
         * @param type
         * @return
         */
        public Builder setIcon(int type) {
            dialog.type = type;
            return this;
        }

        public IconDialog create() {
            return dialog;
        }
    }
}
