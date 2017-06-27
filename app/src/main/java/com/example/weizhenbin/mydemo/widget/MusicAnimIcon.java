package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/6/27.
 */

public class MusicAnimIcon extends View {

    private Paint paint;
    private int w;
    private int h;

    public MusicAnimIcon(Context context) {
        this(context,null);
    }

    public MusicAnimIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicAnimIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.white));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(w==0||h==0){
            w=getWidth();
            h=getHeight();
        }
        for (int i = 0; i < 4; i++) {
            canvas.drawRect(i*(w/4),h/4*i,(i+1)*(w/4),h,paint);
        }
    }
}
