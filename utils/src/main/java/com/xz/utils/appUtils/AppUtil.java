package com.xz.utils.appUtils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Process;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author czr
 * @date 2020/3/31
 * <p>
 * app 相关信息
 * 获取app签名信息
 */
public class AppUtil {
    public final static String MD5 = "MD5";
    public final static String SHA1 = "SHA1";
    public final static String SHA256 = "SHA256";
    private static String ERROR = "error!";

    /**
     * 判断自己（此App）是否正在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isStart(Context context, String packageName) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();

        //获取本App的唯一标识
        int myPid = Process.myPid();
        //利用一个增强for循环取出手机里的所有进程
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            //通过比较进程的唯一标识和包名判断进程里是否存在该App
            if (info.pid == myPid && packageName.equals(info.processName)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获取此app包名
     *
     * @param context
     * @return
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param context
     * @param packageName
     * @param type
     * @return
     */
    public static String getSingInfo(Context context, String packageName, String type) {
        String tmp = "error!";
        try {
            Signature[] signs = getSignatures(context, packageName);
            Log.e("getSingInfo ", "signs =  " + Arrays.asList(signs));
            Signature sig = signs[0];

            if (MD5.equals(type)) {
                tmp = getSignatureString(sig, MD5);
            } else if (SHA1.equals(type)) {
                tmp = getSignatureString(sig, SHA1);
            } else if (SHA256.equals(type)) {
                tmp = getSignatureString(sig, SHA256);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 返回对应包的签名信息
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     *
     * @param sig
     * @param type
     * @return
     */
    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = AppUtil.ERROR;
        try {
            StringBuffer buffer = new StringBuffer();
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                digest.reset();
                digest.update(hexBytes);
                byte[] byteArray = digest.digest();
                for (int i = 0; i < byteArray.length; i++) {
                    if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                        buffer.append("0").append(Integer.toHexString(0xFF & byteArray[i])); //补0，转换成16进制
                    } else {
                        buffer.append(Integer.toHexString(0xFF & byteArray[i]));//转换成16进制
                    }
                }
                fingerprint = buffer.toString().toUpperCase(); //转换成大写
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return fingerprint;
    }

    /**
     * 格式化 签名
     *
     * @param string
     * @param b      true 加冒号大写  false 去冒号并小写
     * @return
     */
    public static String colon(String string, boolean b) {
        if (string.equals(ERROR)) return ERROR;
        StringBuffer stringBuffer = new StringBuffer();
        String colon = ":";
        boolean contains = string.contains(colon);
        if (b) {
            if (contains) {
                stringBuffer.append(string.toUpperCase());
            } else {
                String s = string.toUpperCase();
                for (int i = 2; i < string.length(); i = i + 2) {
                    stringBuffer.append(s.substring(i - 2, i));
                    stringBuffer.append(colon);
                }
                stringBuffer.append(s.substring(s.length() - 2));
            }
        } else {
            if (contains) {
                String[] split = string.toLowerCase().split(colon);
                for (String s : split) {
                    stringBuffer.append(s);
                }
            } else {
                stringBuffer.append(string.toLowerCase());
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 获取手机已安装的应用
     * <p>
     * type 0 全部 1 系统 2 非系统
     */
    public static ArrayList<AppInfo> getAppList(Context context, int type) {
        ArrayList<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            AppInfo appInfo = new AppInfo();
            appInfo.pkgName = packageInfo.packageName;
            appInfo.isSystem = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
            appInfo.name = packageInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.version = packageInfo.versionName;
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(pm);
            appInfo.md5 = getSingInfo(context, appInfo.pkgName, MD5);
            appInfo.sha1 = getSingInfo(context, appInfo.pkgName, SHA1);
            appInfo.sha256 = getSingInfo(context, appInfo.pkgName, SHA256);
            appInfo.firstInstallTime = packageInfo.firstInstallTime;
            appInfo.lastUpdateTime = packageInfo.lastUpdateTime;
            appInfo.versionCode = packageInfo.versionCode;
            /*1、系统签名的软件：/system/app
                2、安装到内存上的非系统签名软件：/data/app
                3、安装到sd卡上的非系统签名软件：/mnt/asec/包名-数字/pkg.apk*/
            appInfo.apkPath = packageInfo.applicationInfo.sourceDir;
            appInfo.intent = pm.getLaunchIntentForPackage(appInfo.pkgName);
            Log.i("getAppList", appInfo.toString());

            switch (type) {
                case 1:
                    if (appInfo.isSystem) list.add(appInfo);
                    break;
                case 2:
                    if (!appInfo.isSystem) list.add(appInfo);
                    break;
                default:
                    list.add(appInfo);
            }
        }
        return list;
    }

}
