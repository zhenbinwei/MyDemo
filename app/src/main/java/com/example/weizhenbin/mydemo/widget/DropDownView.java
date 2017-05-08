package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.weizhenbin.show.R;

/**
 * @author zhenbin_wei
 * @date 2016/5/4 11:32
 * @Description: ${TODO}(用一句话描述该文件做什么)
 */
public class DropDownView extends LinearLayout {

    private float downY = 0;
    private Scroller scroller;
    private OnListener onListener;
    private View head;
    private int headHeight = 0;

    private static final int DROPDOWN = 0;//下拉
    private static final int REFRESH = 1;//可刷新
    private static final int REFRESHING = 2;//正在刷新

    private boolean hasDefHead = true;//有默认头部
    private int status = -1;

    private ProgressBar bar;
    private TextView text;

    private Context mContext;
    public DropDownView(Context context) {
        super(context);
        init(context);
    }

    public DropDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DropDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext=context;
        scroller = new Scroller(context);
        setDefHead(context);
        this.setOrientation(VERTICAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (headHeight == 0 && head != null) {
            headHeight = head.getHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) head.getLayoutParams();
            marginLayoutParams.topMargin = -headHeight;
        }
    }

    public void setOnListener(OnListener onListener) {
        this.onListener = onListener;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int diff = (int) Math.abs(ev.getY() - downY);
                if (diff > 30) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                /**需要滚动的距离*/
                int scrolldy = (int) (downY - event.getY());
                if (status == REFRESHING) {
                    scrollTo(0, -headHeight + scrolldy);
                } else {
                    scrollTo(0, scrolldy);
                }
                if (-getScrollY() < headHeight) {
                    updataDefHead(DROPDOWN);
                } else {
                    updataDefHead(REFRESH);
                }
                if (onListener != null) {
                    if (scrolldy < 0) {
                        onListener.process(scrolldy);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY()<-headHeight) {
                    scroller.startScroll(0, getScrollY(), 0, -getScrollY() - headHeight, 500);
                    status = REFRESHING;
                    updataDefHead(REFRESHING);
                } else {
                    scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                    status=-1;
                }
                invalidate();
                if (-getScrollY() > headHeight) {
                    if (onListener != null) {
                        onListener.finish();
                    }
                }
                break;
        }
        return true;
    }

    private void updataDefHead(int status) {
        switch (status) {
            case DROPDOWN:
                if (hasDefHead) {
                    bar.setVisibility(GONE);
                    text.setText("下拉刷新");
                }
                if(status!=REFRESHING&&head!=null) {
                    head.setAlpha(((float) -getScrollY() / (float) headHeight));
                }
                break;
            case REFRESH:
                Log.d("MyView1", "松开立即刷新");
                if (hasDefHead) {
                    bar.setVisibility(VISIBLE);
                    text.setText("松开立即刷新");
                }
                break;
            case REFRESHING:
                Log.d("MyView1", "正在刷新");
                if (hasDefHead) {
                    bar.setVisibility(VISIBLE);
                    text.setText("正在刷新");
                }
                break;
        }
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (scroller.computeScrollOffset()) {
            int currY = scroller.getCurrY();
            int currX = scroller.getCurrX();
            scrollTo(currX, currY);
            postInvalidate();
        }
        super.computeScroll();
    }


    /**
     * @author zhenbinwei
     * create at 2016/5/4 16:07
     * todo 添加一个头部
     */
    public void setHead(View view) {
        if (view != null) {
            hasDefHead = false;
            removeView(head);
            head = view;
            addView(view, 0);
        }else {
            hasDefHead=false;
            removeView(head);
        }
    }

    private void setDefHead(Context c) {
        head = View.inflate(c, R.layout.dropwownview_head, null);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        head.setLayoutParams(layoutParams);
        bar = (ProgressBar) head.findViewById(R.id.progressBar);
        text = (TextView) head.findViewById(R.id.text);
        addView(head);
    }

    public interface OnListener {
        void process(int process);
        void finish();
    }

    public int getHeadHeight() {
        return headHeight;
    }


    public View getHead() {
        return head;
    }

    public void hideHead() {
        scroller.startScroll(0, getScrollY(), 0, headHeight, 400);
        invalidate();
    }



}
