package com.xz.widget.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

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
    private float distance;//字高度
    private int centerX;//view中心点X
    private int centerY;//view中心点Y
    private int rectRadiu;//圆半径
    private int innerRectRadiu;//圆半径

    private ObjectAnimator animator;//动画

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
        paint.setTextSize(58f);
        paint.setTextAlign(Paint.Align.CENTER);//文字居中

        innerPaint = new Paint();
        innerPaint.setColor(Color.WHITE);
        innerPaint.setAntiAlias(true);//抗锯齿
        innerPaint.setStrokeWidth(18f);//描边宽度


        rect = new RectF();//居中于view显示
        innerRect = new RectF();

        //计算baseline 使文字居中y线上
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //获取文字的中心的位置
        distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

        innerRectRadiu = innerRectWidth / 2;
        rectRadiu = rectWidth / 2;
        animator = ObjectAnimator.ofFloat(this, "progress", 0, 0);
        animator.setDuration(800);
        animator.setInterpolator(new OvershootInterpolator(1f));//有回弹效果

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.left = centerX - rectRadiu;
        rect.top = centerY - rectRadiu;
        rect.right = (centerX - rectRadiu) + rectWidth;
        rect.bottom = (centerY - rectRadiu) + rectWidth;

        innerRect.left = centerX - innerRectRadiu;
        innerRect.top = centerY - innerRectRadiu;
        innerRect.right = (centerX - innerRectRadiu) + innerRectWidth;
        innerRect.bottom = (centerY - innerRectRadiu) + innerRectWidth;


        //oval :指定圆弧的外轮廓矩形区域。
        //startAngle: 圆弧起始角度，单位为度。从180°为起始点
        //sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
        //useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。如果false会将圆弧的两端用直线连接
        //paint: 绘制圆弧的画板属性，如颜色，是否填充等

        //外圆
        canvas.drawArc(rect, 135, 360 * (progress / 100), true, paint);
        //内圆
//        canvas.drawArc(innerRect, 135, 360 * (progress / 100), true, innerPaint);
        canvas.drawArc(innerRect, 0, 360, true, innerPaint);
        //文字
        canvas.drawText(String.format("%.0f", progress) + "%", centerX, centerY + distance, paint);

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
        centerX = mWidth / 2;
        centerY = mHeight / 2;
        Log.i(TAG, "onMeasure: " + mWidth);
        Log.i(TAG, "onMeasure: " + mHeight);
    }

    public float getProgress() {
        return progress;
    }

    /**
     * 设置值
     *
     * @param progress
     */
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }


    /**
     * 播放动画，设置范围
     *
     * @param start
     * @param end
     */
    public void startAnim(int start, int end) {
        animator.setFloatValues(start, end);
        animator.start();
    }

}
