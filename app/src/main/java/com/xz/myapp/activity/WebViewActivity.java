package com.xz.myapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.xz.myapp.R;

import io.alterac.blurkit.BlurLayout;

public class WebViewActivity extends AppCompatActivity {
    private WebView myWebView;
    private BlurLayout blurLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        myWebView = findViewById(R.id.my_webView);
        myWebView.loadUrl("https://www.bilibili.com/");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        WebSettings settings = myWebView.getSettings();

        //设置自适应大小
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小

        //缩放操作
        settings.setSupportZoom(true);//支持缩放
        settings.setBuiltInZoomControls(true);//缩放控件 需要打开，不然缩放无效
        settings.setDisplayZoomControls(false);//隐藏缩放控件


        blurLayout = findViewById(R.id.blurLayout);
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            //如果网页能返回就返回，不能返回就退出App
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //清除网页访问留下的缓存

        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.

        myWebView.clearCache(true);

        //清除当前webview访问的历史记录

        //只会webview访问历史记录里的所有记录除了当前访问记录

        myWebView.clearHistory();

        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据

        myWebView.clearFormData();

        //释放webview
        myWebView.destroy();
    }
}
