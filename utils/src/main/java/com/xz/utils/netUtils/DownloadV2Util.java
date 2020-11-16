package com.xz.utils.netUtils;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.xz.utils.encodUtils.MD5Util;
import com.xz.utils.fileUtils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2020/10/22
 * <p>
 * 基于Okhttp下载方式的下载器
 */
public class DownloadV2Util {
    public static final String TAG = DownloadV2Util.class.getName();
    private static DownloadV2Util mInstance;
    private OkHttpClient client;

    private DownloadV2Util() {
        if (client == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(15, TimeUnit.SECONDS);
            okHttpBuilder.writeTimeout(15, TimeUnit.SECONDS);
            okHttpBuilder.readTimeout(15, TimeUnit.SECONDS);
            client = okHttpBuilder.build();

        }
    }

    public static DownloadV2Util getInstance() {
        if (mInstance == null) {
            synchronized (DownloadV2Util.class) {
                if (mInstance == null) {
                    mInstance = new DownloadV2Util();
                }
            }
        }
        return mInstance;
    }


    /**
     * 下载器
     *
     * @param url      目标url
     * @param savePath 保存目录 例如：/storage/emulated/0/
     * @param fileName 文件名称，需要拓展名 例如：test.apk
     * @param listener 监听回调
     */
    public void download(String url, final String savePath, final String fileName, final DownloadListener listener) {

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                String sPath = FileUtil.isExistDir(savePath);
                if (sPath.equals("")) {
                    listener.onFailure(new IOException("目录定位失败"));
                    return;
                }
                Log.d("DownloadUtil", "最终缓存目录：" + sPath);

                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = FileUtil.isExistFile(sPath, fileName, 0);
                    Log.d("DownloadUtil", "文件绝对地址：" + file.getAbsolutePath());
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        //注意这里回调还没有回到主线程
                        listener.onLoading(progress);
                    }

                    fos.flush();
                    listener.onSuccess(file.getAbsolutePath());
                } catch (Exception e) {
                    listener.onFailure(e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }


            }

        });
    }


    /**
     * 断点下载
     * 非主线程执行
     *
     * @param url
     * @param savePath 存储路径，不用指定文件名称
     */
    public void download(String url, String savePath) {
        String ext = getFileExtension(url);//文件拓展名
        Request request = null;//请求体
        Call call = null;
        File file = null;//文件保存目录
        Response response = null;//请求响应
        InputStream inputStream = null;
        RandomAccessFile accessFile = null;//随机文件，可以自由移动文件开始位置
        long startIndex = 0;//文件下载开始位置
        try {
            String sPath = FileUtil.isExistDir(savePath);
            if (sPath.equals("")) {
                return;
            }
            file = new File(savePath, MD5Util.getMD5(url) + ".tmp");
            accessFile = new RandomAccessFile(file, "rwd");
            if (file.exists()) { //如果当前文件夹存在
                long read = accessFile.length();//获取已经下载文件的大小
                startIndex = read;
            }
            long total = getContentLength(url);
            Log.e("STARTINDEX", startIndex + "  total: " + total);
            if (startIndex == total) {
                return;
            }
            request = new Request.Builder().header("RANGE", "bytes=" + startIndex + "-" + total).url(url).build();
            call = client.newCall(request);
            response = call.execute();
            if (response != null && response.body() != null && response.code() == 206) {//206表示服务器支持断点续传
                inputStream = response.body().byteStream();
                accessFile.seek(startIndex);//移动到开始下载得位置
                byte[] blocks = new byte[50 * 1024];
                int len = -1;
                long sum = 0;
                while ((len = inputStream.read(blocks)) > 0) {
                    sum += len;
                    int progress = (int) (sum * 1.0f / total * 100);
                    Log.d(TAG, "download: " + progress);
                    accessFile.write(blocks, 0, len);
                }
            }

            //修正最终拓展名
            FileUtil.renameFile(file.getParent(), file.getName(), MD5Util.getMD5(url) + "." + ext);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.w("下载异常");
        } finally {
            Logger.w("流关闭");
            try {
                if (accessFile != null)
                    accessFile.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }


    /**
     * 返回远端目标文件大小
     *
     * @param url 远端url
     * @return 字节
     */
    private long getContentLength(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            if (response != null && response.body() != null && response.code() == 200) {
                long length = response.body().contentLength();
                response.close();
                return length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 获取下载链接文件拓展名
     *
     * @param url
     * @return
     */
    public static String getFileExtension(String url) {
        String extension = "";
        String[] pathContents = url.split("[\\\\/]");
        int pathContentsLength = pathContents.length;
        String lastPart = pathContents[pathContentsLength - 1];
        String[] lastPartContents = lastPart.split("\\.");
        if (lastPartContents.length > 1) {
            int lastPartContentLength = lastPartContents.length;
            for (int i = 0; i < lastPartContentLength; i++) {
                if (i < (lastPartContents.length - 1)) {
                    if (i < (lastPartContentLength - 2)) {
                    }
                }
            }
            extension = lastPartContents[lastPartContentLength - 1];
        }
        return extension;
    }


    public interface DownloadListener {
        void onFailure(Exception e);

        void onSuccess(String filePath);

        void onLoading(int progress);
    }
}
