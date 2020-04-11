package com.xz.dialog.imitate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;

import com.xz.dialog.R;
import com.xz.dialog.base.BaseDialog;
import com.xz.dialog.utils.DownloadTools;

/**
 * @author czr
 * @date 2020/4/7
 * <p>
 * 仿原生对话框
 */
public class UpdateDialog extends BaseDialog {

    private int titleBackgroundRes;//标题背景资源
    private String versionName;//版本号
    private String content;//内容

    private ImageView titleBg;
    private ImageView tvClose;
    private TextView tvVersion;
    private TextView tvContent;
    private TextView tvProgress;
    private TextView tvDownload;
    private Typeface tf;
    private String remoteUrl;
    private String localPath;
    private DownloadTools.DownloadCallback callback;

    public UpdateDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_update;
    }


    @Override
    protected void initView() {

        titleBg = findViewById(R.id.title_bg);
        tvClose = findViewById(R.id.tv_close);
        tvVersion = findViewById(R.id.tv_version);
        tvContent = findViewById(R.id.tv_content);
        tvProgress = findViewById(R.id.tv_progress);
        tvDownload = findViewById(R.id.tv_download);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cancel();
            }
        });
        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadTools downloadTools = new DownloadTools();
                downloadTools.start(remoteUrl, localPath, new DownloadTools.DownloadCallback() {
                    @Override
                    public void onInit() {
                        tvDownload.setVisibility(View.GONE);
                        tvProgress.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(String path) {
                        Toast.makeText(mContext, "成功了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String err) {
                        Toast.makeText(mContext, "失败了" + err, Toast.LENGTH_SHORT).show();
                        tvDownload.setVisibility(View.VISIBLE);
                        tvProgress.setVisibility(View.GONE);

                    }

                    @Override
                    public void onUpdate(int i) {
                        tvProgress.setText(i + "%");
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        tvClose.setColorFilter(Color.WHITE);
        tvProgress.setVisibility(View.GONE);
        tvDownload.setVisibility(View.VISIBLE);
        titleBackgroundRes = R.drawable.bg_update;
        versionName = "";
        content = "";
        tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/bitsybutton_v2.ttf");
        tvProgress.setTypeface(tf, Typeface.BOLD);

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
        titleBg.setBackgroundResource(titleBackgroundRes);
        if (tvVersion.equals("")) {
            tvVersion.setVisibility(View.GONE);
        } else {
            tvVersion.setText(versionName);
        }
        tvContent.setText(content);
    }


    public static class Builder {
        private UpdateDialog dialog;

        public Builder(Context context) {
            dialog = new UpdateDialog(context);
            dialog.create();
        }

        /**
         * 设置标题背景资源，仅标题，非全局背景
         *
         * @param res
         * @return
         */
        public Builder setTitleBackground(int res) {
            dialog.titleBackgroundRes = res;
            return this;
        }

        /**
         * 设置版本号  示例：v1.0.0
         *
         * @param ver
         * @return
         */
        public Builder setVersionName(String ver) {
            dialog.versionName = ver;
            return this;
        }

        /**
         * 设置更新内容
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            dialog.content = content;
            return this;
        }

        /**
         * 设置下载
         *
         * @param remoteUrl 下载地址
         * @param localPath 文件路径，包含文件名和拓展名的路径
         * @param callback  回调
         * @return
         */
        @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        public Builder setDownload(String remoteUrl, String localPath, DownloadTools.DownloadCallback callback) {
            dialog.remoteUrl = remoteUrl;
            dialog.localPath = localPath;
            dialog.callback = callback;
            return this;
        }

        public UpdateDialog create() {
            return dialog;
        }
    }
}
