package com.xz.utils.netUtils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

/**
 * @author czr
 * @date 2020/8/11
 * 下载工具类
 * Google 教程：https://developer.android.google.cn/reference/android/app/DownloadManager
 * DownloadManager 下载管理类
 * DownloadManager.Request 请求
 * DownloadManager.Query 查询
 * <p>
 * 当下载完成会发送一个广播：DownloadManager.ACTION_DOWNLOAD_COMPLETE  参数：EXTRA_DOWNLOAD_ID 会返回下载ID
 * <p>
 * 基于 DownloadManager
 * @isuse 目前发现不兼容问题 高版本会出现闪退
 */
public class DownloadUtil {
    private static DownloadUtil instance;

    private String title;
    private String description;

    private DownloadUtil() {
        title = "下载";
        description = "下载管理器";
    }

    public static DownloadUtil getInstance() {
        if (instance == null) {
            synchronized (DownloadUtil.class) {
                if (instance == null) {
                    instance = new DownloadUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 将文件下载至公开目录： /storage/0
     * 下载完成后媒体扫描器会进行扫描，如果是照片会自动添加进图库
     *
     * @param context
     * @param downloadUrl
     * @param fileName
     */
    public void downloadToPublic(Context context, String downloadUrl, String fileName) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager == null) return;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setDestinationInExternalPublicDir("", fileName);
        request.setTitle(title);
        request.setDescription(description);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }

    /**
     * 将文件下载至公开目录： /storage/0
     * 下载完成后不会进行扫描
     *
     * @param context
     * @param downloadUrl
     * @param fileName
     */
    public void downloadToFile(Context context, String downloadUrl, String fileName) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager == null) return;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setDestinationInExternalFilesDir(context, "", fileName);
        request.setTitle(title);
        request.setDescription(description);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        long downloadId = downloadManager.enqueue(request);


    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
