package com.xz.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SportsView extends View {
    private final String TAG = "SportsView";
    private int mHeight, mWidth;
    private float progress = 0;
    private Paint paint;
    private Paint innerPaint;
    private RectF rect;
    private RectF innerRect;
    private int viewHeight = 600;//view默认高度
    private int rectWidth = 500;//外圆大小
    private int innerRectWidth = 450;//内圆大小

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        if (progress > 100) {
            this.progress = 100;
            return;
        }
        this.progress = progress;
        invalidate();
    }

    public SportsView(Context context) {
        super(context);
        init(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStrokeWidth(15f);//描边宽度
        paint.setStrokeCap(Paint.Cap.ROUND);//圆角效果
        paint.setTextSize(48f);

        innerPaint = new Paint();
        innerPaint.setColor(Color.WHITE);
        innerPaint.setAntiAlias(true);//抗锯齿
        innerPaint.setStrokeWidth(15f);//描边宽度
        innerPaint.setStrokeCap(Paint.Cap.ROUND);//圆角效果


        //rect = new RectF(290, 300 - 250, 290 + 500, 50 + 500);//居中于view显示
        rect = new RectF();//居中于view显示
        innerRect = new RectF(0, 0, innerRectWidth, innerRectWidth);
        //内圆中心点  外圆-内圆
        innerRect.left = rectWidth - innerRectWidth;
        innerRect.top = rectWidth - innerRectWidth;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int centerX = mWidth / 2;
        int centerY = mHeight / 2;
        int rectRadiu = rectWidth / 2;
        rect.left = centerX - rectRadiu;
        rect.top = centerY - rectRadiu;
        rect.right = (centerX - rectRadiu) + rectWidth;
        rect.bottom = (centerY - rectRadiu) + rectWidth;

        //内圆居中待改
        //int innerRectRadiu = innerRectWidth / 2;
        //innerRect.left = centerX - innerRectRadiu - (rectWidth - innerRectWidth);
        //innerRect.top = centerY - innerRectRadiu - (rectWidth - innerRectWidth);
        //innerRect.right = (centerX - innerRectRadiu) + rectWidth;
        //innerRect.bottom = (centerY - innerRectRadiu) + rectWidth;


        //oval :指定圆弧的外轮廓矩形区域。
        //startAngle: 圆弧起始角度，单位为度。从180°为起始点
        //sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
        //useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。如果false会将圆弧的两端用直线连接
        //paint: 绘制圆弧的画板属性，如颜色，是否填充等
        canvas.drawArc(rect, 135, 360 * (progress / 100), true, paint);
        canvas.drawArc(innerRect, 135, 360 * (progress / 100), true, innerPaint);
        canvas.drawText(progress + "%", rectWidth / 2, rectWidth / 2, paint);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, viewHeight);
            mHeight = viewHeight;
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(mWidth, mHeight);
        } else {
            setMeasuredDimension(mWidth, viewHeight);
            mHeight = viewHeight;

        }
        Log.i(TAG, "onMeasure: " + mWidth);
        Log.i(TAG, "onMeasure: " + mHeight);
    }

}
