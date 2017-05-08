package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by weizhenbin on 16/6/12.
 * 口香糖
 */
public class ChewingGumView extends View{

    private Paint p;
    private float ax=0;
    private float ay=0;

    private float bx=0;
    private float by=0;

    private float ar=30;
    private float br=30;
    private float tmpBr=30;
    private float tmpAr=30;
    private int maxDistance=300;//弹性距离
    private int distance=0;

    private float sin=0;
    private float cos=0;

    Path path;
    public ChewingGumView(Context context) {
        super(context);
        init( context);
    }

    public ChewingGumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context);
    }

    public ChewingGumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context);
    }

    private void init(Context context){
        p=new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(2);
        p.setColor(0xffff5555);
        path=new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ax=event.getX();
                ay=event.getY();
                bx=event.getX();
                by=event.getY();
                distance=0;
                tmpBr=30;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                ax=event.getX();
                ay=event.getY();
                invalidate();
                //Log.d("ChewingGumView", "距离:" + distance(ax, ay, bx, by));
                distance=distance(ax, ay, bx, by);
                tmpBr=((maxDistance-distance)/(float)maxDistance)*br;
                tmpAr=((maxDistance-distance/3)/(float)maxDistance)*ar;
               // Log.d("ChewingGumView", "比值" + ((maxDistance - distance) / (float) maxDistance));
                //Log.d("ChewingGumView", "br:" + br);



                Log.d("ChewingGumView", "sin:" + sin);
                Log.d("ChewingGumView", "cos:" + cos);


                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(bx,by,tmpBr,p);
        canvas.drawCircle(ax,ay,tmpAr,p);
        sin=(ay-by)/distance;
        cos=(ax-bx)/distance;
        path.reset();
        // A点
        path.moveTo(
                (float) (bx - 30 * sin),
                (float) (by - 30 * cos)
        );
        // B点
        path.lineTo(
                (float) (bx + 30 * sin),
                (float) (by + 30 * cos)
        );
        // C点
        path.lineTo(
                (float) (ax + 30 * sin),
                (float) (ay + 30 * cos)
        );
        // D点
        path.lineTo(
                (float) (ax - 30 * sin),
                (float) (ay - 30 * cos)
        );
        // A点
        path.lineTo(
                (float) (bx - 30 * sin),
                (float) (by - 30 * cos)
        );
        canvas.drawPath(path, p);
    }


    /**
     * 计算两点的距离
     */
    private int distance(float x1,float y1,float x2,float y2) {
        int distance = 0;
            float dx = x1 - x2;
            float dy = y1 - y2;
            distance = (int) Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
}
