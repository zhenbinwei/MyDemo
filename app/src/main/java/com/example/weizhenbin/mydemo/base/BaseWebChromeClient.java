package com.example.weizhenbin.mydemo.base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by weizhenbin on 16/9/28.
 */

public  class BaseWebChromeClient extends WebChromeClient {

    private int newProgress=0;

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        this.newProgress=newProgress;
    }

    public int getNewProgress() {
        return newProgress;
    }
}
