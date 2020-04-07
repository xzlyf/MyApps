package com.xz.myapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xz.base.BaseActivity;
import com.xz.dialog.replica.IconDialog;
import com.xz.myapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends BaseActivity {


    @BindView(R.id.btn_1)
    Button btn1;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initData() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IconDialog dialog = new IconDialog.Builder(mContext)
                        .setIcon(IconDialog.TYPE_ICON_BUG)
                        .setContent("云母屏风烛影深，长河渐落晓星沉。\n" +
                                "嫦娥应悔偷灵药，碧海青天夜夜心。云母屏风烛影深，长河渐落晓星沉。\n" +
                                "嫦娥应悔偷灵药，碧海青天夜夜心。云母屏风烛影深，长河渐落晓星沉。\n" +
                                "嫦娥应悔偷灵药，碧海青天夜夜心。" +
                                "云母屏风烛影深，长河渐落晓星沉。")
                        .create();
                dialog.show();
            }
        });
    }

}
