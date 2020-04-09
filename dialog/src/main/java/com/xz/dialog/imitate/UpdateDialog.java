package com.xz.dialog.imitate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xz.dialog.R;
import com.xz.dialog.base.BaseDialog;
import com.xz.dialog.constant.DialogStyle;
import com.xz.dialog.event.NegativeOnClickListener;
import com.xz.dialog.event.PositiveOnClickListener;

/**
 * @author czr
 * @date 2020/4/7
 * <p>
 * 仿原生对话框
 */
public class UpdateDialog extends BaseDialog implements View.OnClickListener {


    public UpdateDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_update;
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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
    }

    @Override
    public void onClick(View v) {

        dismiss();
    }


    public static class Builder {
        private UpdateDialog dialog;

        public Builder(Context context) {
            dialog = new UpdateDialog(context);
            dialog.create();
        }


        public UpdateDialog create() {
            return dialog;
        }
    }
}
