package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by weizhenbin on 16/8/2.
 * 边缘阴影的控件
 */
public class ShadeView extends ImageView {

    private Paint paint;
    private LinearGradient shaderTop;
    private LinearGradient shaderLeft;
    private LinearGradient shaderButtom;
    private LinearGradient shaderRight;
    private boolean hasInit=false;
    public ShadeView(Context context) {
        this(context,null);
    }

    public ShadeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initFirst() {
        if(!hasInit){
        paint =new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        shaderTop=new LinearGradient(0, 0, 0, 50, 0xFF000000, 0,
                Shader.TileMode.CLAMP);
        shaderLeft=new LinearGradient(0, 0, 50, 0, 0xFF000000, 0,
                Shader.TileMode.CLAMP);
        shaderButtom=new LinearGradient(0, this.getHeight()-50, 0, this.getHeight(), 0, 0xFF000000,
                Shader.TileMode.CLAMP);
        shaderRight=new LinearGradient(this.getWidth()-50, 0,this.getWidth(), 0, 0, 0xFF000000,
                Shader.TileMode.CLAMP);
        hasInit=true;
        }
    }

    public void draw(Canvas canvas) {
        initFirst();
        canvas.saveLayer(0,0,this.getWidth(),this.getHeight(),null,Canvas.HAS_ALPHA_LAYER_SAVE_FLAG);
        super.draw(canvas);

        paint.setShader(shaderTop);
        canvas.drawRect(0, 0, this.getWidth(), 50,paint);
        paint.setShader(shaderLeft);
        canvas.drawRect(0, 0,50, this.getHeight(),paint);
        paint.setShader(shaderButtom);
        canvas.drawRect(0, this.getHeight()-50,this.getWidth(), this.getHeight(),paint);
        paint.setShader(shaderRight);
        canvas.drawRect(this.getWidth()-50, 0,this.getWidth(), this.getHeight(),paint);
    }


    public interface ShadeInterface{
        void selfDraw(Canvas canvas);
    }
}
