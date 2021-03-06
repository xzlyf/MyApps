package com.xz.myapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xz.base.BaseActivity;
import com.xz.myapp.R;
import com.xz.utils.fileUtils.StorageUtil;
import com.xz.utils.netUtils.DownloadV2Util;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadActivity extends BaseActivity {

    @BindView(R.id.etDownloadLink)
    EditText etDownloadLink;
    @BindView(R.id.etSavePath)
    EditText etSavePath;
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.btnPause)
    Button btnPause;
    @BindView(R.id.btnDelete)
    Button btnDelete;


    private DownloadV2Util downloadV2Util;

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_download;
    }

    @Override
    public void initData() {
        downloadV2Util = DownloadV2Util.getInstance();

        etDownloadLink.setText("http://wap.apk.anzhi.com/data5/apk/202009/30/1b6ae8af14452f9320a69fa1fb788ef4_16064400.apk");
        etSavePath.setText(StorageUtil.getExternalDir().getAbsolutePath() + File.separator + "apkDownload");


    }

    @OnClick({R.id.btnStart, R.id.btnPause, R.id.btnDelete})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                download();
                break;
            case R.id.btnPause:
                break;
            case R.id.btnDelete:
                break;
        }
    }

    private void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadV2Util.download("http://wap.apk.anzhi.com/data5/apk/202009/30/1b6ae8af14452f9320a69fa1fb788ef4_16064400.apk"
                        , StorageUtil.getExternalDir().getAbsolutePath() + "/apkDownload/");
            }
        }).start();
    }

}
