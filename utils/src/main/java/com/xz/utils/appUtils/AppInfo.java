package com.xz.utils.appUtils;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * ying  2020/7/21
 * Describe ：应用信息
 */
public class AppInfo {
    public Drawable appIcon;  //应用图标
    public Intent intent;     //启动应用程序的Intent
    public String pkgName;    //应用包名
    public String name;    //应用名称
    public boolean isSystem; //是否是系统应用
    public String md5;
    public String sha1;
    public String sha256;
    public String version;  //版本
    public int versionCode;  //版本
    public Long firstInstallTime;//最后更新时间
    public Long lastUpdateTime;//最后更新时间
    public String apkPath;//安装包路径

    @Override
    public String toString() {
        return "AppInfo{" +
                "appIcon=" + appIcon +
                ", intent=" + intent +
                ", pkgName='" + pkgName + '\'' +
                ", name='" + name + '\'' +
                ", isSystem=" + isSystem +
                ", md5='" + md5 + '\'' +
                ", sha1='" + sha1 + '\'' +
                ", sha256='" + sha256 + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}