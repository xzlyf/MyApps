package com.xz.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author czr
 * @date 2020/3/27
 * 分割线，切割线，常见分割线控件
 */
public class CuttLineView extends View {


    private int viewWidth = 300;
    private int viewHeight = 100;

    public CuttLineView(Context context) {
        super(context);
    }

    public CuttLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CuttLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidthSize = getMeasuredWidth();
        int mHeightSize = getMeasuredHeight();
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //宽高自适应
            setMeasuredDimension(viewWidth, viewHeight);
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            viewWidth = mWidthSize;
            viewHeight = mHeightSize;
            setMeasuredDimension(mWidthSize, mHeightSize);
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.AT_MOST) {
            viewWidth = mWidthSize;
            setMeasuredDimension(viewWidth, viewHeight);
        } else {
            viewWidth = mWidthSize;
            viewHeight = mHeightSize;
            setMeasuredDimension(mWidthSize, mHeightSize);
        }

    }
}
