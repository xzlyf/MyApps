package com.xz.widget.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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

import com.xz.xzwidget.R;

public class SportsView extends View {
    private final String TAG = "SportsView";
    private int angle = 135;//圆角开始度数
    private int mHeight, mWidth;
    private float progress = 0;
    private Paint paint;
    private Paint innerPaint;
    private int mainColor;//主色调
    private int secondColor;//辅助色
    private RectF rect;
    private RectF innerRect;
    private int viewHeight = 600;//view默认高度
    private int rectWidth = 500;//外圆大小 默认大小
    private int innerRectWidth = 450;//内圆大小 默认大小 规则：内圆=外圆-50
    private float distance;//字高度
    private int centerX;//view中心点X
    private int centerY;//view中心点Y
    private int rectRadiu;//外圆半径
    private int innerRectRadiu;//内圆半径

    private ObjectAnimator animator;//动画

    public SportsView(Context context) {
        this(context, null);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        initTypeArrt(context, attrs);

        paint = new Paint();
        paint.setColor(mainColor);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStrokeWidth(15f);//描边宽度
        paint.setTextSize(58f);
        paint.setTextAlign(Paint.Align.CENTER);//文字居中

        innerPaint = new Paint();
        innerPaint.setColor(secondColor);
        innerPaint.setAntiAlias(true);//抗锯齿
        innerPaint.setStrokeWidth(18f);//描边宽度


        rect = new RectF();//居中于view显示
        innerRect = new RectF();

        //计算baseline 使文字居中y线上
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //获取文字的中心的位置
        distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;


        animator = ObjectAnimator.ofFloat(this, "progress", 0, 0);
        animator.setDuration(800);
        animator.setInterpolator(new OvershootInterpolator(1f));//有回弹效果

    }

    /**
     * 加载属性资源
     *
     * @param context
     * @param attrs
     */
    private void initTypeArrt(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SportsView);
        mainColor = array.getColor(R.styleable.SportsView_x_mainColor, Color.BLACK);
        secondColor = array.getColor(R.styleable.SportsView_x_secondColor, Color.WHITE);
        angle = array.getInt(R.styleable.SportsView_x_angleStart, 135);
        progress = array.getInt(R.styleable.SportsView_x_defaultValue, 0);
        array.recycle();

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
        canvas.drawArc(rect, angle, 360 * (progress / 100), true, paint);
        //内圆
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
        //更新一系列view的宽高
        centerX = mWidth / 2;
        centerY = mHeight / 2;
        rectWidth = mWidth / 2;
        innerRectWidth = rectWidth - 50;
        innerRectRadiu = innerRectWidth / 2;
        rectRadiu = rectWidth / 2;

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
