package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.weizhenbin.show.R;


/**
 * Created by weizhenbin on 2017/6/19.
 */

public class WebProgressView extends View {

    private Paint paint;

    private int progress=0;
    private int w ,h;
    public WebProgressView(Context context) {
        this(context,null);
    }
    public WebProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WebProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0f,0f,(float) progress/100*w,(float) h,paint);
    }

    public void onProgressChange(int progress){
        this.progress=progress;
        invalidate();
        Log.d("WebProgressView", "progress:" + progress);
    }
}
