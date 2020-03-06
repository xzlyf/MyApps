package com.xz.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.xz.xzwidget.R;

/**
 * 滑动解锁控件
 *
 * 2020.3.6未完成
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class SlideUnLockView extends FrameLayout {
    private Context mContext;
    private int mHeight, mWidth;
    private ImageView touchView;    //小圆球


    public SlideUnLockView(Context context) {
        this(context, null);

    }

    public SlideUnLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideUnLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mHeight = getHeight();
        mWidth = getWidth();
    }

    private void initView() {
        touchView = new ImageView(mContext);
        addView(touchView);
        aiterView();



    }

    private void aiterView() {
        //主view
        setBackground(getResources().getDrawable(R.drawable.slideunlock_background));
        setPadding(15,15,15,15);
        //LinearLayout.LayoutParams mainParms = (LinearLayout.LayoutParams) getLayoutParams();
        //mainParms.leftMargin = 10;
        //mainParms.rightMargin = 10;
        //setLayoutParams(mainParms);

        //小圆球
        final LayoutParams touchParams = (LayoutParams) touchView.getLayoutParams();
        touchParams.height = mHeight-30;
        touchParams.width = mHeight-30;
        touchView.setBackground(getResources().getDrawable(R.drawable.slideunlock_touch));
        touchView.setLayoutParams(touchParams);

        touchView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("xz", "onTouch: x:"+event.getRawX()+"   y:"+event.getRawY());
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        touchParams.leftMargin = (int) event.getRawX();
                        touchView.setLayoutParams(touchParams);

                        break;
                }
                return true;
            }
        });
    }


}
