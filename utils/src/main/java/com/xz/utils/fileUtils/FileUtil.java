package com.xz.utils.fileUtils;

import android.util.Log;
import android.widget.Toast;

import com.xz.utils.R;

import java.io.File;
import java.io.IOException;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2020/11/12
 */
public class FileUtil {
    /**
     * 判断路径是否为目录
     * 若不存在则创建
     * <p>
     * 注意：安卓10已经不支持根目录上创建文件夹
     * 解决方法： 配置文件加上 android:requestLegacyExternalStorage="true"
     *
     * @param saveDir 目标文件夹路径
     * @return
     */
    public static String isExistDir(String saveDir) {
        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.exists()) {
            boolean statue = downloadFile.mkdirs();
            if (statue) {
                Log.d("xz", "dir is create ");
            } else {
                Log.w("xz", "dir not create ");
                return "";
            }
        }
        Log.d("xz", "final path:" + downloadFile.getAbsolutePath());
        return downloadFile.getAbsolutePath();
    }

    /**
     * 如果存在文件同名 就在文件名后面加上_index
     * 例如： xxx(1).apk    xxx(2).apk   ...
     *
     * @param saveDir  保存的目录
     * @param fileName 文件名
     * @param index    写0即可
     * @return
     */
    public static File isExistFile(String saveDir, String fileName, int index) {
        boolean isAgain;
        File file;
        StringBuilder finalName = new StringBuilder();
        String[] arry = fileName.split("\\.");

        do {
            file = new File(saveDir, fileName);
            if (file.exists()) {
                isAgain = true;
                index++;
                finalName.append(arry[0]);
                finalName.append("(");
                finalName.append(index);
                finalName.append(")");
                finalName.append(".");
                finalName.append(arry[1]);
                fileName = finalName.toString();
                finalName.delete(0, finalName.length());
            } else {
                isAgain = false;
            }
        } while (isAgain);
        return file;
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldname 原来的文件名
     * @param newname 新文件名
     */
    public static File renameFile(String path, String oldname, String newname) {
        boolean isResult;
        if (!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path, oldname);
            File newfile = new File(path, newname);
            if (!oldfile.exists()) {
                return null;//重命名文件不存在
            }
            if (newfile.exists()) {
                //若在该目录下已经有一个文件和新文件名相同，则改为xxx(1).xx
                newfile = new File(isExistFile(path, newname, 0).getAbsolutePath());
            }
            isResult = oldfile.renameTo(newfile);
            if (isResult) {
                return newfile;
            }
        }
        return null;
    }

}
