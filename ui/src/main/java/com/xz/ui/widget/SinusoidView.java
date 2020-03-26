package com.xz.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xz.xzwidget.R;

/**
 * @author czr
 * @date 2020/3/26
 * 贝塞尔曲线 正弦曲线 模拟波浪运动效果
 */
public class SinusoidView extends View {

    private int viewHeight = 300;
    private int viewWidth = 300;
    private Paint mPrint;
    private Path mPath;
    //一个波浪长，相当于两个二阶贝塞尔曲线的长度 两个波峰的距离
    private int mItemWaveLength;
    //波浪在Y轴方向的位置 波峰高度为总view高度的3/4 固定
    private int originY = 75;
    //波澜幅度
    private int range;
    private int dx;
    private int firstColor;
    private ValueAnimator animator;

    /**
     * 构造函数会在new的时候调用
     */
    public SinusoidView(Context context) {
        this(context, null);
    }

    /**
     * 在布局中使用
     */
    public SinusoidView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 布局layout中调用,但是会有style
     */
    public SinusoidView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SinusoidView);
        firstColor = array.getColor(R.styleable.SinusoidView_x_firstColor, Color.BLACK);
        mItemWaveLength = array.getInt(R.styleable.SinusoidView_x_itemWaveLen, 300);
        range = array.getInt(R.styleable.SinusoidView_x_range, 80);

        mPrint = new Paint();
        mPrint.setColor(firstColor);
        mPrint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath = new Path();
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

        // 波峰高度为总view高度的3/4
        originY = viewHeight - (int) (viewHeight * 0.75);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        int halfWaveLen = mItemWaveLength / 2;//半个波长，即一个贝塞尔曲线长度

        mPath.moveTo(-mItemWaveLength + dx, originY);//波浪的开始位置

        //每一次for循环添加一个波浪的长度到path中，根据view的宽度来计算一共可以添加多少个波浪长度
        for (int i = -mItemWaveLength; i < getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -range, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, range, halfWaveLen, 0);
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPrint);

    }

    /**
     * 播放动画
     */
    public void startAnim() {
        //根据一个动画不断得到0~mItemWaveLength的值dx，通过dx的增加不断去改变波浪开始的位置，dx的变化范围刚好是一个波浪的长度，
        //所以可以形成一个完整的波浪动画，假如dx最大小于mItemWaveLength的话， 就会不会画出一个完整的波浪形状
        animator = ValueAnimator.ofInt(0, mItemWaveLength);
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


    /**
     * 停止动画
     */
    public void stopAnim() {
        if (animator != null) {
            animator.end();
        }
    }
}
