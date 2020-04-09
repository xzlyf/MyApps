package com.xz.myapp.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xz.base.BaseActivity;
import com.xz.dialog.constant.DialogStyle;
import com.xz.dialog.event.NegativeOnClickListener;
import com.xz.dialog.event.PositiveOnClickListener;
import com.xz.dialog.imitate.AppleDialog;
import com.xz.dialog.imitate.AppleInputDialog;
import com.xz.dialog.imitate.IconDialog;
import com.xz.myapp.R;

import butterknife.BindView;

public class DialogActivity extends BaseActivity {


    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;

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
                        .setIcon(DialogStyle.TYPE_ICON_BUG)
                        .setAngle(DialogStyle.BG_ANGLE_8)
                        .setMainColor(getResources().getColor(R.color.defaultMainColor))
                        .setContent("云母屏风烛影深，长河渐落晓星沉。\n" +
                                "嫦娥应悔偷灵药，碧海青天夜夜心。云母屏风烛影深，长河渐落晓星沉。\n" +
                                "嫦娥应悔偷灵药，碧海青天夜夜心。")
                        .setPositiveOnClickListener("同意", new PositiveOnClickListener() {
                            @Override
                            public void OnClick(View v) {
                                Toast.makeText(DialogActivity.this, "同意了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeOnClickListener("不同意", new NegativeOnClickListener() {
                            @Override
                            public void OnClick(View v) {
                                Toast.makeText(DialogActivity.this, "不同意", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                dialog.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppleDialog dialog = new AppleDialog.Builder(mContext)
                        .setTitle("警告")
                        .setNegativeOnClickListener("取消", new NegativeOnClickListener() {
                            @Override
                            public void OnClick(View v) {

                            }
                        })
                        .setPositiveOnClickListener("确定", new PositiveOnClickListener() {
                            @Override
                            public void OnClick(View v) {

                            }
                        })
                        .setContent("你的手机电量过低，请保存数据")
                        .create();

                dialog.show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppleInputDialog dialog = new AppleInputDialog.Builder(mContext)
                        .setTitle("密码输入")
                        .setContent("请输入'666666@qq.com'\nAppleID密码")
                        .setInputLines(1)
                        .setHint("密码")
                        .setPositiveOnClickListener("确定", new PositiveOnClickListener() {
                            @Override
                            public void OnClick(View v) {

                            }
                        })
                        .setNegativeOnClickListener("取消", new NegativeOnClickListener() {
                            @Override
                            public void OnClick(View v) {

                            }
                        })
                        .create();

                dialog.show();

            }
        });

    }

}
