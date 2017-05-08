package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by weizhenbin on 2017/3/1.
 */
public class ExternalLayout extends FrameLayout{

    private NestedScrollingParentHelper parentHelper;


    RecyclerView  recyclerView;

    public ExternalLayout(Context context) {
        this(context,null);
    }

    public ExternalLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExternalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parentHelper=new NestedScrollingParentHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(recyclerView==null){
            for (int i = 0; i < getChildCount(); i++) {
                if(getChildAt(i) instanceof RecyclerView){
                    recyclerView= (RecyclerView) getChildAt(i);
                    break;
                }
            }}
    }

    float downY;
    float lastY;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

   @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {




       switch (ev.getAction()){
           case MotionEvent.ACTION_DOWN:
               downY=ev.getRawY();
               break;
           case MotionEvent.ACTION_MOVE:
               //移动的时候
               lastY=ev.getRawY();
               Log.d("ExternalLayout", "downY:" + downY+";lastY:"+lastY);
               if (lastY-downY>0){
                   Log.d("ExternalLayout", "手指在起点下方");
                   if (!ViewCompat.canScrollVertically(recyclerView, -1)||!ViewCompat.canScrollVertically(recyclerView, 1)){
                       onInterceptTouchEvent(ev);
                   }
               }else {
                   Log.d("ExternalLayout", "手指在起点上方");
               }
           case MotionEvent.ACTION_UP:
              // downY=0;
              // lastY=0;
               break;
       }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

      /*  Log.d("ExternalLayout", "2222dy:" + dy+";;isTop:"+isTop);
        boolean hiddenTop = dy > 0 && getScrollY() < 0;
        boolean showTop = dy < 0 && getScrollY() > -300 && !ViewCompat.canScrollVertically(recyclerView, -1);
        Log.d("ExternalLayout", "showTop:" + showTop);
        boolean dyy=dy>0&&(getScrollY()+dy>0);
        if (showTop||hiddenTop)
        {
           *//* if(dyy){
                scrollBy(0, -getScrollY());
                consumed[1] = -getScrollY();
            }else {
                scrollBy(0, dy);
                consumed[1] = dy;
            }*//*
            Log.d("ExternalLayout", "拦截");
            return true;
        }*/
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                    if (!ViewCompat.canScrollVertically(recyclerView, -1)&&lastY-downY>0){
                        recyclerView.requestDisallowInterceptTouchEvent(true);
                       return true;
                    }else if(!ViewCompat.canScrollVertically(recyclerView, 1)&&lastY-downY<0){
                        recyclerView.requestDisallowInterceptTouchEvent(true);
                        return true;
                    }else {
                        recyclerView.requestDisallowInterceptTouchEvent(false);
                    }
        }

        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ExternalLayout", "外处理");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:

                    scrollTo(0, (int) (downY - lastY) / 2);
                break;
            case MotionEvent.ACTION_UP:
                scrollTo(0,0);
                break;

        }
        return super.onTouchEvent(event);
    }



  /*  @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("ExternalLayout", "ACTION_MOVE");
                if(!recyclerView.canScrollVertically(-1)){
                    this.requestDisallowInterceptTouchEvent(false);
                    canMove=true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP :
                break;
        }
        return false;
    }*/



   /* @Override
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
        boolean hiddenTop = dy > 0 && getScrollY() < 0;
        boolean showTop = dy < 0 && getScrollY() > -300 && !ViewCompat.canScrollVertically(target, -1);
        boolean dyy=dy>0&&(getScrollY()+dy>0);
        if ((showTop||hiddenTop))
        {
            if(dyy){
                scrollBy(0, -getScrollY());
                consumed[1] = -getScrollY();
            }else {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
        Log.d("ExternalLayout", "target:" + target);
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
    }*/
}
