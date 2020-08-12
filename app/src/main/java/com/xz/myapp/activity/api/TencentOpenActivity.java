package com.xz.myapp.activity.api;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Request;
import com.xz.base.BaseActivity;
import com.xz.myapp.R;
import com.xz.utils.encodUtils.Base64Util;
import com.xz.utils.encodUtils.MD5Util;
import com.xz.utils.RandomUtil;
import com.xz.utils.netUtils.OkHttpClientManager;
import com.xz.utils.netUtils.SignMD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TencentOpenActivity extends BaseActivity {
    //存储地址
    private String mPath;
    //腾讯AI开放平台官网：https://ai.qq.com/doc/home.shtml
    //腾讯api
    private final String APP_KEY = "2z0A37T4tAmhoHWE";
    private final String APP_ID = "2131063130";
    //基础闲聊
    private final String GET_TEXTCHAT = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";
    //语音合成
    private final String GET_TTS = "https://api.ai.qq.com/fcgi-bin/aai/aai_tts";

    @Override
    public boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_tencent_open;
    }

    @Override
    public void initData() {
        setTitle("腾讯AI开放平台接口测试");
        mPath = getExternalCacheDir() + "/OpenApi/";
        File file = new File(mPath);
        if (!file.exists()) {
            file.mkdirs();
            Logger.d("存储文件夹创建成功");
        }
        //getTTs("你好，世界");
        //getTextChat("在干嘛");
    }


    /**
     * 合成语音
     *
     * @param st
     */
    private void getTTs(@NonNull String st) {
        Map<String, Object> params = new HashMap<>();
        params.put("app_id", APP_ID);
        params.put("time_stamp", System.currentTimeMillis() / 1000);
        params.put("nonce_str", RandomUtil.getRandomString(32));
        //======
        params.put("speaker", 6);//发音人
        params.put("format", 3);//音频格式
        params.put("volume", 0);//声音大小
        params.put("speed", 100);//语速
        params.put("aht", 0);//改变音高
        params.put("apc", 58);//音色
        params.put("text", st);//非空且长度上限150字节
        //======
        params.put("sign", SignMD5.getSignMD5(APP_KEY, params));
        OkHttpClientManager.getAsyn(mContext, GET_TTS, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Logger.e(request.toString());
            }

            @Override
            public void onResponse(String response) {
                Logger.w(response);

                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getInt("ret") == 0) {
                        String file = mPath + System.currentTimeMillis() + ".mp3";
                        JSONObject obj2 = obj.getJSONObject("data");
                        //输出mp3文件
                        Base64Util.decoderBase64File(obj2.getString("speech"), file);
                        //校验md5
                        String sMd5 = obj2.getString("md5sum");
                        //本地md5
                        String lMd5 = MD5Util.getFileMD5(new File(file)).toUpperCase();

                        Logger.d("MD5校验：" + sMd5.equals(lMd5) + "\n校验md5:" + sMd5 + "\n本地md5:" + lMd5);

                    } else {
                        Logger.w("服务器异常：" + obj.getInt("ret") + ":" + obj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.e("解析异常");
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.w("音频文件存储异常");
                }
            }
        }, params, true);
    }


    /**
     * 智能闲聊，服务器异常，稍后再调试
     */
    private void getTextChat(@NonNull String quest) {
        Map<String, Object> params = new HashMap<>();
        params.put("app_id", APP_ID);
        params.put("time_stamp", System.currentTimeMillis() / 1000);
        params.put("nonce_str", RandomUtil.getRandomString(32));
        //=====
        params.put("session", 10000);//会话标识，应用内唯一，相当于房间号
        params.put("question", quest);
        //=====
        params.put("sign", SignMD5.getSignMD5(APP_KEY, params));
        OkHttpClientManager.getAsyn(mContext, GET_TEXTCHAT, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Logger.e(request.toString());
            }

            @Override
            public void onResponse(String response) {
                Logger.w(response);
            }
        }, params, true);
    }


}
