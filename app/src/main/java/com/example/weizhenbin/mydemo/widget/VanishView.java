package com.example.weizhenbin.mydemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by weizhenbin on 16/6/1.
 * <p/>
 * 一个可以随意拖动的view
 */
public class VanishView extends TextView {
    private Context context;
    /**窗口管理器*/
    private WindowManager windowManager;

    /**用来存储镜像的imageview*/
    private ImageView iv;

    /** 状态栏高度*/
    private int statusHeight = 0;

    /**按下的坐标x 相对于view自身*/
    private int dx = 0;

    /**按下的坐标y 相对于view自身*/
    private int dy = 0;

    /**镜像bitmap*/
    private Bitmap tmp;

    /**按下的坐标x 相对于屏幕*/
    private float downX = 0;

    /**按下的坐标y 相对于屏幕*/
    private float downY = 0;

    /**属性动画 用于回弹效果*/
    private ValueAnimator animator;

    /**窗口参数*/
    private WindowManager.LayoutParams mWindowLayoutParams;

    /**接口对象*/
    private OnListener listener;
    public VanishView(Context context) {
        super(context);
        init(context);
    }

    public VanishView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VanishView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        windowManager = ((Activity) context).getWindowManager();
        statusHeight = getStatusHeight(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = (int) event.getX();
                dy = (int) event.getY();
                downX = event.getRawX();
                downY = event.getRawY();
                addWindow(context, event.getRawX(), event.getRawY());
                setVisibility(INVISIBLE);
                break;
            case MotionEvent.ACTION_MOVE:
                mWindowLayoutParams.x = (int) (event.getRawX() - dx);
                mWindowLayoutParams.y = (int) (event.getRawY() - statusHeight - dy);
                windowManager.updateViewLayout(iv, mWindowLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
                int distance=distance(new MyPoint(event.getRawX(), event.getRawY()), new MyPoint(downX, downY));
                if(distance<400) {
                    scroll(new MyPoint(event.getRawX(), event.getRawY()), new MyPoint(downX, downY));
                }else {
                    if(listener!=null){
                        listener.onDismiss();
                    }
                    windowManager.removeView(iv);
                }
                break;
        }
        return true;
    }

    /**
     * 构建一个窗口 用于存放和移动镜像
     * */
    private void addWindow(Context context, float downX, float dowmY) {
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        iv = new ImageView(context);
        mWindowLayoutParams.format = PixelFormat.RGBA_8888;
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowLayoutParams.x = (int) (downX - dx);
        mWindowLayoutParams.y = (int) (dowmY - statusHeight - dy);
        //获取view的镜像bitmap
        this.setDrawingCacheEnabled(true);
        tmp = Bitmap.createBitmap(this.getDrawingCache());
        //释放缓存
        this.destroyDrawingCache();
        iv.setImageBitmap(tmp);
        windowManager.addView(iv, mWindowLayoutParams);
    }


    /**
     * 使用属性动画 实现缓慢回弹效果
     * */
    private void scroll(MyPoint start, MyPoint end) {
        animator = ValueAnimator.ofObject(new MyTypeEvaluator(), start, end);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                MyPoint point = (MyPoint) animation.getAnimatedValue();
                mWindowLayoutParams.x = (int) (point.x - dx);
                mWindowLayoutParams.y = (int) (point.y - statusHeight - dy);
                windowManager.updateViewLayout(iv, mWindowLayoutParams);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                windowManager.removeView(iv);
                setVisibility(VISIBLE);
            }

        });
        animator.start();
    }

    /**
     * 计算两点的距离
     */
    private int distance(MyPoint point1, MyPoint point2) {
        int distance = 0;
        if (point1 != null && point2 != null) {
            float dx = point1.x - point2.x;
            float dy = point1.y - point2.y;
            distance = (int) Math.sqrt(dx * dx + dy * dy);
        }
        return distance;
    }

    /**
     * 获取状态栏的高度
     */
    private static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    class MyPoint {
        float x;
        float y;

        public MyPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "MyPoint{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    class MyTypeEvaluator implements TypeEvaluator<MyPoint> {

        @Override
        public MyPoint evaluate(float fraction, MyPoint startValue, MyPoint endValue) {
            MyPoint point = startValue;
            point.x = startValue.x + fraction * (endValue.x - startValue.x);
            point.y = startValue.y + fraction * (endValue.y - startValue.y);
            return point;
        }
    }

    /**事件回调借口*/
   public interface OnListener{
        void onDismiss();
    }

    public void setListener(OnListener listener) {
        this.listener = listener;
    }
}
