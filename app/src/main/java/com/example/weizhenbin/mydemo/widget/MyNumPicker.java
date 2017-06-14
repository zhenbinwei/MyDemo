package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.weizhenbin.show.R;

import java.lang.reflect.Field;

/**
 * Created by weizhenbin on 2017/5/11.
 */

public class MyNumPicker extends NumberPicker {
    public MyNumPicker(Context context) {
        this(context,null);
    }

    public MyNumPicker(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNumPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setNumberPickerDividerColor(this, Color.RED);
        setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d("MyNumPicker", "picker.getChildCount():" + picker.getChildCount());
            }
        });
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        updateView(child);
    }

    private void updateView(View v) {
        if(v instanceof EditText){
            ((EditText) v).setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * 设置NumberPicker分割线颜色
     *
     * @param numberPicker：NumberPicker
     * @param color：int
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field SelectionDividerField : pickerFields) {
            if (SelectionDividerField.getName().equals("mSelectionDivider")) {
                SelectionDividerField.setAccessible(true);
                try {
                    SelectionDividerField.set(numberPicker, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
