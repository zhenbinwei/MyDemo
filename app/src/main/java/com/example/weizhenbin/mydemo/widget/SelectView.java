package com.example.weizhenbin.mydemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.example.weizhenbin.mydemo.util.DensityUtil;
import com.weizhenbin.show.R;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: SelectView
 * @ProjectName Show
 * @Package com.weizhenbin.show.widget
 * @date 2016/2/29 16:35
 * 自定义view 用于选择
 */
public class SelectView extends ViewGroup {
    private int w;
    private int h;
    private int tabW;
    private int tabH;
    private DensityUtil densityUtil;//像素转换工具
    private long duration = 1500;//弹出速率 最少1000 默认1500
    private boolean isShow = false;
    private int mode = 1; //0 水平 1垂直 默认水平
    private int marginButtom = 20;
    private int marginRight = 20;
    private ImageView center;//开关

    public SelectView(Context context) {
        super(context);
        init(context);
        addEvent();
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        addEvent();
    }


    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        addEvent();
    }

    private void init(Context context) {
        center = (ImageView) crateItem(context);
        center.setVisibility(VISIBLE);
        this.addView(center, 0);
        densityUtil = new DensityUtil(context);
        tabH = densityUtil.dip2px(40);
        tabW = densityUtil.dip2px(40);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            getChildAt(i).layout(w - tabH - marginRight, h - tabH - marginButtom, w - marginRight, h - marginButtom);

        }
    }

    private View crateItem(Context context) {
        final ImageView imageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(tabW, tabH);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setVisibility(GONE);
        return imageView;
    }

    public SelectView addItme(Context context, int resouceId, OnClickListener onClickListener) {
        ImageView imageView = (ImageView) crateItem(context);
        if (resouceId != 0) {
            imageView.setImageResource(resouceId);
        }
        imageView.setOnClickListener(onClickListener);
        this.addView(imageView, 0);
        return this;
    }

    private void addEvent() {
        getChildAt(getChildCount() - 1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    hide();
                    isShow = false;
                    setClickable(false);
                } else {
                    show();
                    isShow = true;
                    setClickable(true);
                }
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SelectView", "点击了");
                hide();
                isShow = false;
                setClickable(false);
            }
        });

    }


    private void show() {
        int size = getChildCount();
        ObjectAnimator animator;
        for (int i = 0; i < size - 1; i++) {
            final View v = getChildAt(i);
            if (mode == 1) {
                animator = ObjectAnimator.ofFloat(v, "y", getChildAt(i).getY(), getChildAt(i).getY() - (i * tabH + tabH)).setDuration(duration < 1000 ? 1000 : duration);
            } else {
                animator = ObjectAnimator.ofFloat(v, "x", getChildAt(i).getX(), getChildAt(i).getX() - (i * tabW + tabW)).setDuration(duration < 1000 ? 1000 : duration);
            }
            animator.setInterpolator(new BounceInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    v.setVisibility(VISIBLE);
                }
            });
            animator.start();
        }
    }

    private void hide() {
        int size = getChildCount();
        ObjectAnimator animator;
        for (int i = 0; i < size - 1; i++) {
            final View v = getChildAt(i);
            if (mode == 1) {
                animator = ObjectAnimator.ofFloat(v, "y", getChildAt(i).getY(), h - tabH - marginButtom).setDuration(duration < 1000 ? 500 : duration - 500);

            } else {
                animator = ObjectAnimator.ofFloat(v, "x", getChildAt(i).getX(), w - tabW - marginRight).setDuration(duration < 1000 ? 500 : duration - 500);

            }
            animator.setInterpolator(new BounceInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    v.setVisibility(GONE);
                }
            });
            animator.start();
        }
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getTabW() {
        return tabW;
    }

    public void setTabW(int tabW) {
        this.tabW = densityUtil.dip2px(tabW);
        invalidate();
    }

    public int getTabH() {
        return tabH;
    }

    public void setTabH(int tabH) {
        this.tabH = densityUtil.dip2px(tabH);
        invalidate();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public ImageView getCenter() {
        return center;
    }
}
