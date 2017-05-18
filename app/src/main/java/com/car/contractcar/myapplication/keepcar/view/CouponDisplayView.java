package com.car.contractcar.myapplication.keepcar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.car.contractcar.myapplication.R;

/**
 * 自定义边缘凹凸的优惠券效果view
 * Created by lijuan on 2016/9/26.
 */
public class CouponDisplayView extends LinearLayout {
    private Paint mPaint;
    /**
     * 半径
     */
    private float radius;
    /**
     * 圆间距
     */
    private float gap;

    /**
     * 圆数量
     */
    private int circleNum;
    private float remain;

    public CouponDisplayView(Context context) {
        this(context, null);
        Log.d("mDebug", "CouponDisplayView context");
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d("mDebug", "CouponDisplayView context, attrs");
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("mDebug", "CouponDisplayView context,attrs,defStyleAttr");
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CouponDisplayView, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CouponDisplayView_radius:
                    radius = a.getDimensionPixelSize(R.styleable.CouponDisplayView_radius, 10);
                    break;
                case R.styleable.CouponDisplayView_gap:
                    gap = a.getDimensionPixelSize(R.styleable.CouponDisplayView_radius, 8);
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("mDebug", "onSizeChanged,w=" + w + ",h=" + h + ",oldw=" + oldw + ",oldh=" + oldh);
        if (remain == 0) {
            //计算不整除的剩余部分
            remain = (int) (w - gap) % (2 * radius + gap);
        }
        circleNum = (int) ((w - gap) / (2 * radius + gap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("mDebug", "onDraw");
        for (int i = 0; i < circleNum; i++) {
            float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, getHeight(), radius, mPaint);
        }
    }
}