package com.xz.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private Paint linePaint;
    private Paint txtPaint;
    private int paddingLR = 50;//左右边距
    private final float distance;//文字基线位置
    private String txt = "分隔符";
    private float txtWidth;//字宽度

    public CuttLineView(Context context) {
        this(context, null);
    }

    public CuttLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CuttLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#646464"));
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4);
        txtPaint = new Paint();
        txtPaint.setColor(Color.parseColor("#646464"));
        txtPaint.setStrokeCap(Paint.Cap.ROUND);
        txtPaint.setTextSize(38);
        txtPaint.setTextAlign(Paint.Align.CENTER);
        //计算baseline 使文字居中y线上
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        //获取文字的中心的位置
        distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        txtWidth = txtPaint.measureText(txt);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //字体左边线
        canvas.drawLine(paddingLR, viewHeight / 2, (viewWidth / 2) - (txtWidth / 2), viewHeight / 2, linePaint);
        //字体右边线
        canvas.drawLine((viewWidth / 2) + (txtWidth / 2), viewHeight / 2, viewWidth - paddingLR, viewHeight / 2, linePaint);
        //字体
        canvas.drawText(txt, viewWidth / 2, viewHeight / 2 + distance, txtPaint);
    }
}
