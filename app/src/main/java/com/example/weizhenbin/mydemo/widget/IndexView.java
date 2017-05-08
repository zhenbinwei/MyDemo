package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by weizhenbin on 16/5/9.
 * viewpage的导航点
 */
public class IndexView extends View {

    private int indexNum=4;//点数
    private int selectIndex=3;//当前选中的
    private int cR;//点半径
    private int colorId= Color.RED;//点颜色
    private int selectColorId=Color.BLUE;//选中的颜色
    private int viewHeight;

    private Paint p;
    public IndexView(Context context) {
        super(context);
        init( context);
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init( context);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init( context);
    }

    private void init(Context context){
        p=new Paint();
        p.setColor(colorId);
    }

    public void setIndexNum(int indexNum) {
        this.indexNum = indexNum;
        invalidate();
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        invalidate();
    }

    public void setcR(int cR) {
        this.cR = cR;
        invalidate();
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight=h;
        Log.d("IndexView", "viewHeight:" + viewHeight);
        cR=  (viewHeight)/2;
        Log.d("IndexView", "cR:" + cR);
        getLayoutParams().width=viewHeight*indexNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <indexNum ; i++) {
            if(i==selectIndex){
                p.setColor(selectColorId);
            }else {
                p.setColor(colorId);
            }
            canvas.drawCircle((viewHeight/2)+i*(viewHeight),(viewHeight)/2,cR*0.8f,p);
        }
    }
}
