package com.example.weizhenbin.mydemo.base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by weizhenbin on 16/9/28.
 */

public  class BaseWebChromeClient extends WebChromeClient {

   private IProgressChanged iProgressChanged;
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(iProgressChanged!=null){
            iProgressChanged.onProgressChanged(view,newProgress);
        }
    }

    public void setiProgressChanged(IProgressChanged iProgressChanged) {
        this.iProgressChanged = iProgressChanged;
    }

    public interface IProgressChanged{
        void onProgressChanged(WebView view, int newProgress);
    }
}
