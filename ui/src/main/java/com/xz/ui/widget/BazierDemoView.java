package com.xz.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.content.ContentValues.TAG;

/**
 * @author czr
 * @date 2020/3/26
 * <p>
 * 贝塞尔曲线测试
 */
public class BazierDemoView extends View {
    private int viewWidth;
    private int viewHeight;
    private Path mPath;
    private Paint mPaint;

    private int waveLen = 200;//波长
    private int cvY = 50;//控制点Y

    private ValueAnimator animator;
    private int dx;


    public BazierDemoView(Context context) {
        this(context, null);
    }

    public BazierDemoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BazierDemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);//样式：填充 描边
        mPaint.setStrokeWidth(5);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int mWidthSize = getMeasuredWidth();
        int mHeightSize = getMeasuredHeight();
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            viewWidth = mWidthSize;
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

    private int halfWaveLen;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //因为有两个波长，一个上波峰，一个下波峰。所以要除于2，否则会动画不完整
        halfWaveLen = waveLen / 2;
        mPath.moveTo(-waveLen + dx, viewHeight / 2);//波浪的开始位置
        //计算view宽度可以绘制多少个波澜长度
        for (int i = -waveLen; i < viewWidth + waveLen; i += waveLen) {
            //完美的弧线是的控制点x是波长的一半
            mPath.rQuadTo(halfWaveLen / 2, -cvY, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, cvY, halfWaveLen, 0);
        }
        canvas.drawPath(mPath, mPaint);
    }


    public int getWaveLen() {
        return waveLen;
    }


    public int getCvY() {
        return cvY;
    }

    public void refresh( int y, int waveLen) {
        stopAnim();
        Log.d(TAG, "refresh: "+ y + "=" + waveLen);
        this.waveLen = waveLen;
        this.cvY = y;
        invalidate();

    }

    /**
     * 播放动画
     */
    public void startAnim() {
        //根据一个动画不断得到0~waveLen的值dx，通过dx的增加不断去改变波浪开始的位置，dx的变化范围刚好是一个波浪的长度，
        //所以可以形成一个完整的波浪动画，假如dx最大小于waveLen的话， 就会不会画出一个完整的波浪形状
        animator = ValueAnimator.ofInt(0, waveLen);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }

    public void stopAnim() {
        if (animator != null) {
            animator.cancel();
        }
    }

}
