package com.xz.dialog.imitate;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xz.dialog.R;
import com.xz.dialog.base.BaseDialog;
import com.xz.dialog.event.NegativeOnClickListener;
import com.xz.dialog.event.PositiveOnClickListener;

/**
 * @author czr
 * @date 2020/4/8
 * 仿IOS对话框
 */
public class AppleListDialog extends BaseDialog {

    private String title, content;//标题和类容
    private String negativeName, positiveName;//标题和类容

    private TextView tvTitle;
    private TextView tvContent;

    public AppleListDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_ios_list;
    }

    @Override
    protected void initView() {

        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
    }

    @Override
    protected void initData() {
        //使用苹果字体
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/pingfangziti.ttf");
        tvTitle.setTypeface(typeface, Typeface.BOLD);
        tvContent.setTypeface(typeface);

        title = "示例";
        content = "Hello world!";
        negativeName = "取消";
        positiveName = "确定";
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
        tvTitle.setText(title);
        tvContent.setText(content);


    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            dismiss();

        }
    };

    public static class Builder {
        AppleListDialog dialog;

        public Builder(Context context) {
            dialog = new AppleListDialog(context);
            dialog.create();
        }

        /**
         * 设置标题
         *
         * @param title
         * @return
         */
        public Builder setTitle(CharSequence title) {
            dialog.title = title.toString();
            return this;
        }

        /**
         * 设置内容
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            dialog.content = content;
            return this;
        }


        public AppleListDialog create() {
            return dialog;
        }


    }

}
