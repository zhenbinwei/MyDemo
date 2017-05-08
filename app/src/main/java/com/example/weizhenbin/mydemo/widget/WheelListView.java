package com.example.weizhenbin.mydemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by weizhenbin on 2016/10/26.
 */
public class WheelListView extends ListView implements AbsListView.OnScrollListener {

    private Paint paintLine,paintShader;
    private int selectColor= Color.BLUE;//选择字体颜色
    private int defColor= Color.BLACK;//默认字体颜色
    private int selectTextSize=20;//选择字体大小  单位sp
    private int defTextSize=15;//默认字体大小  单位sp

    private LinearGradient shaderTop;
    private LinearGradient shaderButtom;
    private boolean hasInit=false;

    public int selectPosition;
    public WheelListView(Context context) {
        this(context,null);
    }

    public WheelListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WheelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnScrollListener(this);
        paintLine=new Paint();
        paintLine.setStrokeWidth(2);
        paintLine.setColor(selectColor);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE){
            if(view instanceof ListView){
                View v = view.getChildAt(0);
                int position= view.getFirstVisiblePosition();
                if(-v.getTop()>v.getHeight()/2){
                    ScrollAnim((ListView) view,position, v.getTop(),-v.getHeight());
                    selectPosition=position+1;
                }else {
                    ScrollAnim((ListView) view,position, v.getTop(),0);
                    selectPosition=position;
               }

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(view instanceof ListView){
            for (int i = 0; i < view.getChildCount(); i++) {
                View v=view.getChildAt(i);
                if(v instanceof TextView){
                if(v.getY()+v.getHeight()/2>view.getHeight()/3&&v.getY()+v.getHeight()/2<view.getHeight()/3*2){
                    ((TextView) v).setTextColor(selectColor);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectTextSize);
                }else {
                    ((TextView) v).setTextColor(defColor);
                    ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP,defTextSize);
                }
                }
            }
        }
    }
    private void initFirst() {
        if(!hasInit){
            paintShader =new Paint();
            paintShader.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            shaderTop=new LinearGradient(0, 0, 0, 100, 0xFF000000, 0,
                    Shader.TileMode.CLAMP);
            shaderButtom=new LinearGradient(0, this.getHeight()-100, 0, this.getHeight(), 0, 0xFF000000,
                    Shader.TileMode.CLAMP);
            hasInit=true;
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(20,getHeight()/3,getWidth()-20,getHeight()/3,paintLine);
        canvas.drawLine(20,getHeight()/3*2,getWidth()-20,getHeight()/3*2,paintLine);
    }
    @Override
    public void draw(Canvas canvas) {
        initFirst();
        canvas.saveLayer(0,0,this.getWidth(),this.getHeight(),null,Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        super.draw(canvas);
        paintShader.setShader(shaderTop);
        canvas.drawRect(0, 0, this.getWidth(), 100,paintShader);
        paintShader.setShader(shaderButtom);
        canvas.drawRect(0, this.getHeight()-100,this.getWidth(), this.getHeight(),paintShader);
    }
    /**
     * 平滑滚动
     * */
    private void ScrollAnim(final ListView v, final int position, int y1, int y2){
        ValueAnimator valueAnimator= ValueAnimator.ofInt(y1,y2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setSelectionFromTop(position,((Integer) animation.getAnimatedValue()).intValue());
            }
        });
        valueAnimator.setDuration(150);
        valueAnimator.start();
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public void setDefColor(int defColor) {
        this.defColor = defColor;
    }

    public void setSelectTextSize(int selectTextSize) {
        this.selectTextSize = selectTextSize;
    }

    public void setDefTextSize(int defTextSize) {
        this.defTextSize = defTextSize;
    }
}
