package com.xz.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;

import com.xz.xzwidget.R;

/**
 * @author czr
 * @date 2020/3/24
 * <p>
 * 水平滑动组件
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class SlideHorizontalView extends View {
    private final String TAG = "SlideHorizontalView";
    private int paddingLeft = 80;//顶部距离
    private int paddingTop = 80;//左侧距离
    private int mWidthSize;
    private int mHeightSize;
    private int mViewHeight = paddingLeft * 2;//默认高度
    private int mX = paddingLeft;//圆球x坐标
    private Paint mLinePrint;//线画笔、外圆
    private Paint mInnerPrint;//内圆画笔
    private Paint textPrint;//文字画笔
    private int mainColor;//主色调
    private int textColor;//文字色
    private int secondColor;//辅助色
    private int radius = 50;//外圆半径
    private int innerRadius = 40;//内圆半径
    private int moveX;
    private float distance;//字高度
    private int textSize = 42;//字体大小
    private int value = 0; //用户设置value
    private float progress = 0;
    private ObjectAnimator animator;//动画
    private OnScrollListener mListener;//滑动监听


    /**
     * 构造函数会在new的时候调用
     */
    public SlideHorizontalView(Context context) {
        this(context, null);
    }

    /**
     * 在布局中使用
     */
    public SlideHorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 布局layout中调用,但是会有style
     */
    public SlideHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initTypeAttr(context, attrs);


        mLinePrint = new Paint();
        mLinePrint.setColor(mainColor);
        mLinePrint.setStrokeCap(Paint.Cap.ROUND);
        mLinePrint.setStrokeWidth(12);
        mLinePrint.setStyle(Paint.Style.FILL); //描边效果

        mInnerPrint = new Paint();
        mInnerPrint.setColor(secondColor);
        mInnerPrint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPrint.setStrokeWidth(16);
        mInnerPrint.setStyle(Paint.Style.FILL);

        textPrint = new Paint();
        textPrint.setColor(textColor);
        textPrint.setStrokeCap(Paint.Cap.ROUND);
        textPrint.setStrokeWidth(4);
        textPrint.setTextSize(textSize);
        textPrint.setTextAlign(Paint.Align.CENTER);//文字居中
        //计算baseline 使文字居中y线上
        Paint.FontMetrics fontMetrics = textPrint.getFontMetrics();
        //获取文字的中心的位置
        distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        textPrint.setTextSize(0);

        animator = ObjectAnimator.ofFloat(this, "progress", 0, 0);
        animator.setInterpolator(new OvershootInterpolator(1f));//有回弹效果
        animator.setDuration(800);

        mListener = new OnScrollListener() {
            @Override
            public void onScroll(int value) {
                Log.i(TAG, "onScroll: " + value);
            }
        };

    }

    /**
     * 初始化属性
     */
    private void initTypeAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideHorizontalView);
        mainColor = array.getColor(R.styleable.SlideHorizontalView_x_main_color, Color.BLACK);
        secondColor = array.getColor(R.styleable.SlideHorizontalView_x_second_color, Color.WHITE);
        textColor = array.getColor(R.styleable.SlideHorizontalView_x_text_color, Color.BLACK);
        value = array.getInt(R.styleable.SlideHorizontalView_x_default_value, 0);
        boolean isShowNumber = array.getBoolean(R.styleable.SlideHorizontalView_x_show_number, true);
        setTextEnable(isShowNumber);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthSize = getMeasuredWidth();
        mHeightSize = getMeasuredHeight();
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //宽高自适应
            setMeasuredDimension(mWidthSize, mViewHeight);
            paddingTop = mViewHeight / 2;
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(mWidthSize, mHeightSize);
            paddingTop = mHeightSize / 2;
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidthSize, mViewHeight);
            paddingTop = mViewHeight / 2;
        } else {
            setMeasuredDimension(mWidthSize, mHeightSize);
            paddingTop = mHeightSize / 2;

        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //通过百分比数值获取mX的位置
        float i = (float) value / 100;
        mX = (int) (i * (mWidthSize - paddingLeft * 2)) + paddingLeft;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //横线
        canvas.drawLine(paddingLeft, paddingTop, mWidthSize - paddingLeft, paddingTop, mLinePrint);
        //外圆
        canvas.drawCircle(mX, paddingTop, radius, mLinePrint);
        //内圆
        canvas.drawCircle(mX, paddingTop, innerRadius, mInnerPrint);
        //文字
        canvas.drawText(getValue() + "", mX, paddingTop + distance, textPrint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //实现点击缩小一点的效果
                radius = 45;
                innerRadius = 35;
                //滑动时显示数字
                textPrint.setTextSize(textSize);
                break;
            case MotionEvent.ACTION_MOVE:

                if (moveX > paddingLeft && moveX < mWidthSize - paddingLeft) {
                    mX = moveX;

                    mListener.onScroll(getValue());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //圆的大小复原
                radius = 50;
                innerRadius = 40;
                //不滑动时不显示数字
                textPrint.setTextSize(0);
                break;
        }
        invalidate();
        return true;
    }


    /**
     * 返回数值百分比
     *
     * @return
     */
    public int getValue() {
        //(滑动距离-padding) / (总宽度-padding*2) * 100
        return (int) ((float) (mX - paddingLeft) / (mWidthSize - paddingLeft * 2) * 100);
    }

    /**
     * 设置数值显示位置
     *
     * @param value
     * @IntRange 限制参数范围
     */
    public void setValue(@IntRange(from = 0, to = 100) int value) {
        this.value = value;
        //通过百分比数值获取mX的位置
        float i = (float) value / 100;
        mX = (int) (i * (mWidthSize - paddingLeft * 2)) + paddingLeft;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }

    /**
     * 设置数值显示位置
     * 动画调用
     *
     * @param progress
     */
    public void setProgress(float progress) {
        this.progress = progress;
        float i = (float) progress / 100;
        mX = (int) (i * (mWidthSize - paddingLeft * 2)) + paddingLeft;
        invalidate();
    }

    /**
     * 设置滑动时字体是否显示
     *
     * @param enable
     */
    public void setTextEnable(boolean enable) {
        if (!enable) {
            textSize = 0;
        } else {
            textSize = 42;
        }
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

    public void setOnScrollListener(OnScrollListener listener) {
        mListener = listener;
    }

    /**
     * 滑动监听
     */
    public interface OnScrollListener {
        void onScroll(int value);
    }
}
