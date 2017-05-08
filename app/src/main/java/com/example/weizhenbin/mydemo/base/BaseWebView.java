package com.example.weizhenbin.mydemo.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by weizhenbin on 16/9/28.
 */

public class BaseWebView extends WebView {
    protected WebSettings webSettings=getSettings();
    private Paint p;
    private BaseWebChromeClient baseWebChromeClient;
    public BaseWebView(Context context) {
        this(context,null);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        p=new Paint();
        p.setColor(Color.BLUE);
       // webSettings.setsetPluginsEnabled(true);

        webSettings.setAllowFileAccess(true);
       // webSettings.setAllowUniversalAccessFromFileURLs(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            webSettings.setAllowUniversalAccessFromFileURLs(true);


        baseWebChromeClient=new BaseWebChromeClient();
        setWebChromeClient(baseWebChromeClient);
        webSettings.setJavaScriptEnabled(true);
        class JsObject {
            public String toString() { return "injectedObject"; }

            public Object requestAnimationFrame(){
                return null;
            }

        }
        addJavascriptInterface(new JsObject(), "injectedObject");
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(baseWebChromeClient.getNewProgress()>=100){
            return;
        }
        canvas.drawRect(0,0,getWidth()*baseWebChromeClient.getNewProgress()/100,8,p);
    }
}
