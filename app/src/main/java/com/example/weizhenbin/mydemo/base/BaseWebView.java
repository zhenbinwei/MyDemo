package com.example.weizhenbin.mydemo.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        baseWebChromeClient=new BaseWebChromeClient();
        setWebChromeClient(baseWebChromeClient);
        webSettings.setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient());
    }

    public void setiProgressChanged(BaseWebChromeClient.IProgressChanged iProgressChanged) {
        if (baseWebChromeClient!=null){
            baseWebChromeClient.setiProgressChanged(iProgressChanged);
        }
    }
    public void setiReceivedTitle(BaseWebChromeClient.IReceivedTitle iReceivedTitle) {
        if(baseWebChromeClient!=null){
            baseWebChromeClient.setiReceivedTitle(iReceivedTitle);
        }
    }
}
