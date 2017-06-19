package com.example.weizhenbin.mydemo.base;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by weizhenbin on 16/9/28.
 */

public  class BaseWebChromeClient extends WebChromeClient {

   private IProgressChanged iProgressChanged;
    private  IReceivedTitle iReceivedTitle;
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

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (iReceivedTitle!=null){
            iReceivedTitle.onReceivedTitle(view,title);
        }
    }

    public void setiReceivedTitle(IReceivedTitle iReceivedTitle) {
        this.iReceivedTitle = iReceivedTitle;
    }

    public interface IProgressChanged{
        void onProgressChanged(WebView view, int newProgress);
    }
    public interface IReceivedTitle{
        void onReceivedTitle(WebView view, String title);
    }
}
