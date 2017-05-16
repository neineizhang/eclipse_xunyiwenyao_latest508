package com.zll.xunyiwenyao.util;

import android.widget.NumberPicker;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
/**
 * Created by neinei on 2017/5/12.
 */

public class MyNumberPicker extends NumberPicker {

    public MyNumberPicker(Context context, AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addView(View child) {
        this.addView(child, null);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        this.addView(child, -1, params);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setNumberPicker(child);
    }

    /**
     * 设置CustomNumberPicker的属性 颜色 大小
     *
     * @param view
     */
    public void setNumberPicker(View view) {
        if (view instanceof EditText) {

            ((EditText) view).setTextSize(15);
        }
//        LinearLayout.LayoutParams lps = (LinearLayout.L8youtParams) view.getLayoutParams();
//        //lps.setMargins(100, 0, 100, 0);
//        lps.weight = 100;
//        lps.height = 100;
//        view.setLayoutParams(lps);
    }
}