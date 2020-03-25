package com.xz.widget.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author czr
 * @date 2020/3/24
 * <p>
 * 水平滑动组件
 */
public class SlideHorizontalView extends View {
    /**
     * 构造函数会在new的时候调用
     */
    public SlideHorizontalView(Context context) {
        super(context);
    }

    /**
     * 在布局中使用
     */
    public SlideHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 布局layout中调用,但是会有style
     */
    public SlideHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
