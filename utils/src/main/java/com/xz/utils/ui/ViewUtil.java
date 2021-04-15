package com.xz.utils.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Size;
import android.view.Display;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class ViewUtil {
    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取屏幕宽高
     *
     * @param activity
     * @return Size().getWidth() 宽  Size().getHeight()  高
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Size getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return new Size(display.getWidth(), display.getHeight());
    }

    /**
     * 获取屏幕宽高
     *
     * @param activity
     * @return [宽，高]
     */
    public static int[] getScreenSizeV2(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return new int[]{display.getWidth(), display.getHeight()};
    }

    /**
     * px 转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp转 px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }

}
