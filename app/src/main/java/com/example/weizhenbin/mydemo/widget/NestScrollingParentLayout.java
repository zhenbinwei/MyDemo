package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by weizhenbin on 16/10/13.
 */

public class NestScrollingParentLayout extends LinearLayout implements NestedScrollingParent{
    private NestedScrollingParentHelper parentHelper;
    public NestScrollingParentLayout(Context context) {
        this(context,null);
    }

    public NestScrollingParentLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestScrollingParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parentHelper=new NestedScrollingParentHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
         parentHelper.onNestedScrollAccepted(child,target,nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
         parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
       // Log.d("NestScrollingParentLayo", "父view滚动了");


        //consumed[0]+=dx;
        //consumed[1]+=dy;
       // if(getScrollY())
       // Log.d("NestScrollingParentLayo", "getScrollY():" + getScrollY());
        Log.d("NestScrollingParentLayo", "dy:" + dy);
         //if(getScrollY()>400||getScrollY()<0){
         //    return;
         //}
        if(dy<0){
           if(getScrollY()<=0){
               return;
           }
        }else {
            if(getScrollY()>400){
                return;
            }
        }

        scrollBy(0,dy);
        consumed[0]=dx;
        consumed[1]=dy;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }
}
