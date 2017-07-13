package com.example.weizhenbin.mydemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by weizhenbin on 2017/7/12.
 * 水平支持加载更多的HorizontalScrollView
 */

public class HorizontalLoadMoreScrollView extends HorizontalScrollView {

    // 拖动的距离 size = 4 的意思 只允许拖动屏幕的1/4  阻尼效果

    private static final int size = 4;
    private View inner;
    private float x=-1;
    private Paint p;
    private Paint textP;
    private String hintText="继续拖动";
    private String loadText="松开有惊喜";

    private String text=hintText;
    private ILoadMore iLoadMore;

    private int loadX=200;
    public HorizontalLoadMoreScrollView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalLoadMoreScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        setOverScrollMode(OVER_SCROLL_NEVER);
        p=new Paint();
        textP=new Paint();
        p.setColor(Color.RED);
        textP.setColor(Color.WHITE);
        textP.setTextSize(30);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        int dy=inner.getScrollX();
        if(dy>200){
            dy=200;
        }
        canvas.drawOval(new RectF(inner.getWidth()-dy/2+30,0,inner.getWidth()+dy/2+30,getHeight()),p);
        float txtH=getFontHeight(30);
        for (int i = 0; i < text.length(); i++) {
            canvas.drawText(text.toCharArray()[i]+"",inner.getWidth()-dy/4,i*txtH+(getHeight()-txtH*text.length())/2+20,textP);
        }
    }

    public int getFontHeight(float fontSize)
    {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
           return commOnTouchEvent(ev);
        }
    }

    public boolean commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(x==-1){
            x = ev.getX();
        }
        switch (action) {
            case MotionEvent.ACTION_UP:
                animation();
                if(inner.getScrollX()>loadX){
                    if(iLoadMore!=null){
                        iLoadMore.onLoadMore();
                    }
                }
                x=-1;
                break;
            case MotionEvent.ACTION_MOVE:
                float nowX = ev.getX();
                /**
                 * size=4 表示 拖动的距离为屏幕的高度的1/4
                 */
                int deltaX = (int) (x - nowX) / size;
                if (isNeedMove()) {
                        if(deltaX>=0) {
                            inner.scrollTo(deltaX, 0);
                            if(inner.getScrollX()>loadX){
                                text=loadText;
                            }else {
                                text=hintText;
                            }
                        }else {
                            inner.scrollTo(0, 0);
                            return super.onTouchEvent(ev);
                        }
                    return false;
                }else {
                    x = -1;
                    return super.onTouchEvent(ev);
                }
        }
       return super.onTouchEvent(ev);
    }

    // 开启动画移动

    public void animation() {
        int dx=Math.abs(inner.getScrollX());
        ValueAnimator valueAnimator=ValueAnimator.ofInt(dx,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int dx= (int) animation.getAnimatedValue();
                inner.scrollTo(dx,0);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = inner.getMeasuredWidth() - getWidth();
        int scrollX = getScrollX();
        if (scrollX == offset&&inner.getScrollX()>=0) {
           return true;
        }
        return false;
    }


    public interface ILoadMore{
        void onLoadMore();
    }
    /**默认提示语*/
    public void setHintText(String hintText) {
        this.hintText = hintText;
    }
    /**可刷新时提示语*/
    public void setLoadText(String loadText) {
        this.loadText = loadText;
    }

    /**加载监听*/
    public void setiLoadMore(ILoadMore iLoadMore) {
        this.iLoadMore = iLoadMore;
    }
    /**加载距离*/
    public void setLoadX(int loadX) {
        this.loadX = loadX;
    }
    /**文字颜色*/
    public void setTextColor(int color){
        if(textP!=null){
            textP.setColor(color);
        }
    }
    /**弧形背景色*/
    public  void  setOvalBgColor(int color){
        if(p!=null){
            p.setColor(color);
        }
    }
    /**字体大小*/
    public void setTextSize(int size){
        if(textP!=null){
            textP.setTextSize(size);
        }
    }
}
