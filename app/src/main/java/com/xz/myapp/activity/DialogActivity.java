package com.xz.myapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xz.base.BaseActivity;
import com.xz.dialog.constant.DialogStyle;
import com.xz.dialog.event.NegativeOnClickListener;
import com.xz.dialog.event.OnItemClickListener;
import com.xz.dialog.event.PositiveOnClickListener;
import com.xz.dialog.imitate.AppleDialog;
import com.xz.dialog.imitate.AppleInputDialog;
import com.xz.dialog.imitate.AppleListDialog;
import com.xz.dialog.imitate.IconDialog;
import com.xz.dialog.imitate.UpdateDialog;
import com.xz.dialog.utils.DownloadTools;
import com.xz.myapp.R;

import butterknife.BindView;

public class DialogActivity extends BaseActivity {


    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_4)
    Button btn4;
    @BindView(R.id.btn_5)
    Button btn5;

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

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppleListDialog dialog = new AppleListDialog.Builder(mContext)
                        .setTitle("打分")
                        .setContent("给个好评呗")
                        .setItem(new String[]{"好评", "特好评", "超级好评"}, new OnItemClickListener<String[]>() {
                            @Override
                            public void OnClick(String[] data, int position) {
                                sToast("点击了：" + data[position]);
                            }
                        })
                        .create();

                dialog.show();

            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mContext, "存储权限未获取", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String url = "https://cn.bing.com/th?id=OHR.SpiritSiblings_EN-CN1295893854_1920x1080.jpg&rf=LaDigue_1920x1080.jpg";
                String path = mContext.getExternalFilesDir("update").getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
                UpdateDialog dialog = new UpdateDialog.Builder(mContext)
                        .setVersionName("v1.0.0")
                        .setContent("1.新的模组加入\n2.自定义控件漏洞修复\n3.腾讯Api接口接入\n4.界面美化设计\n5.修复某个致命漏洞\n6.加入苹果风对话框")
                        .setDownload(url, path, new DownloadTools.DownloadCallback() {
                            @Override
                            public void onInit() {

                            }

                            @Override
                            public void onSuccess(String path) {
                                //下载成功，返回下载地址

                            }

                            @Override
                            public void onError(String err) {

                            }

                            @Override
                            public void onUpdate(int i) {

                            }
                        })
                        .create();
                dialog.show();

            }
        });


    }

}
