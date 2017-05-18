package com.car.contractcar.myapplication.keepcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Administrator on 2017/4/22.
 */

public class NoHandlerButton extends Button {
    public NoHandlerButton(Context context) {
        super(context);
    }

    public NoHandlerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoHandlerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return false;
    }
}
