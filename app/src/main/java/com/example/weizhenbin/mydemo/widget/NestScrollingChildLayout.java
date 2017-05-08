package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by weizhenbin on 16/10/13.
 */

public class NestScrollingChildLayout extends View implements NestedScrollingChild {

    private NestedScrollingChildHelper childHelper;
    private int[] consumed;
    private int[] offsetInWindow;
    private float downx=0;
    private float downy=0;
    private int dx;
    private int dy;
    public NestScrollingChildLayout(Context context) {
        this(context,null);
    }

    public NestScrollingChildLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestScrollingChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        childHelper=new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        consumed=new int[2];
        offsetInWindow=new int[2];
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
         childHelper.setNestedScrollingEnabled(enabled);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downx=event.getX();
                downy=event.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                dx= (int) (downx-event.getX());
                dy= (int) (downy-event.getY());
                Log.d("NestScrollingChildLayou", "move" + dy);
                if(dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow)){
                    dx-=consumed[0];
                    dy-=consumed[1];
                }else {
                    downx=event.getX();
                    downy=event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;

        }
        return true;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX,velocityY,consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX,velocityY);
    }

}
