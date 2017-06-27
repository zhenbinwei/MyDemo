package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.weizhenbin.mydemo.util.Tools;
import com.weizhenbin.show.R;

import java.util.Random;

/**
 * Created by weizhenbin on 2017/6/27.
 */

public class MusicAnimIcon extends View {

    private Paint paint;
    private int w;
    private int h;

    private int dw=0;
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
        dw= Tools.dp2px(context,3);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(w==0||h==0){
            w=getWidth();
            h=getHeight();
        }
        for (int i = 0; i <3; i++) {
            Random random=new Random();
            int n=random.nextInt(4);
            canvas.drawRect(i*(w/3)+dw,h/5*n,(i+1)*(w/3)-dw,h,paint);
        }
        postInvalidateDelayed(200);
    }
}
