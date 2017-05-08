package com.example.weizhenbin.mydemo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by weizhenbin on 16/8/8.
 * appso水平滚动进度条
 */
public class ProgressScrollView extends ScrollView {

    private Paint p;
    private int top=0;
    private float progressWidth=0;
    public ProgressScrollView(Context context) {
        this(context,null);
    }

    public ProgressScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0,top,progressWidth,top+10,p);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        top=t;
        progressWidth=top/((float)this.getChildAt(0).getHeight()-this.getHeight())*this.getWidth();
    }

    private void init(){
        p=new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);
    }




}
