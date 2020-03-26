package com.xz.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author czr
 * @date 2020/3/26
 * 绘画控件
 */
public class DrawingView extends View {
    private int mVieWidht;
    //默认高度
    private int mVieHeight = 300;
    private Path mPath;
    private Paint mPaint;
    private float mPreX,mPreY;

    public DrawingView(Context context) {
        this(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            //宽高自适应
            mVieWidht = width;
            setMeasuredDimension(mVieWidht, height);
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            mVieWidht = width;
            mVieHeight = height;
            setMeasuredDimension(width, height);
        } else if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.AT_MOST) {
            mVieWidht = width;
            setMeasuredDimension(width, mVieHeight);
        } else {
            mVieWidht = width;
            mVieHeight = height;
            setMeasuredDimension(mVieWidht, mVieHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_MOVE:
                //不平滑
                //mPath.lineTo(event.getX(), event.getY());

                //用贝塞尔曲线绘制比较平滑，作为一个功能使用 ，平滑功能
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 清理画布
     */
    public void clean() {
        mPath.reset();
        invalidate();
    }

}
