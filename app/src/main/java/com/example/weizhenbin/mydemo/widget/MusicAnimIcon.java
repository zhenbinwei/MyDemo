package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weizhenbin.show.R;

import java.util.Random;

/**
 * Created by weizhenbin on 2017/6/27.
 */

public class MusicAnimIcon extends View {

    private Paint paint;
    private int w;
    private int h;
    private int columnCount;
    private int columnSpace;
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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Music_Attrs);
        if (typedArray != null) {
            columnCount =  typedArray.getInt(R.styleable.Music_Attrs_column_count, 1);
            columnSpace = (int) typedArray.getDimension(R.styleable.Music_Attrs_column_space, 0);
            typedArray.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(w==0||h==0){
            w=getWidth();
            h=getHeight();
        }
        for (int i = 0; i <columnCount; i++) {
            Random random=new Random();
            int n=random.nextInt(4);
            canvas.drawRect(i*(w/columnCount)+columnSpace,h/5*n,(i+1)*(w/columnCount)-columnSpace,h,paint);
        }
        postInvalidateDelayed(200);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView,int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        //判断view是否被用户可见visibility VISIBLE = 0x00000000;INVISIBLE = 0x00000004;GONE = 0x00000008;
        if(visibility==VISIBLE) {
            invalidate();
        }
    }
}
