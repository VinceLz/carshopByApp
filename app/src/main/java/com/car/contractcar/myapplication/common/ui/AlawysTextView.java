package com.car.contractcar.myapplication.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/12/20.
 */
public class AlawysTextView extends TextView {

    public AlawysTextView(Context context) {
        super(context);
    }

    public AlawysTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlawysTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
