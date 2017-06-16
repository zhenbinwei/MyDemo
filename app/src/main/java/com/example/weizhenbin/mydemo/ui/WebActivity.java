package com.example.weizhenbin.mydemo.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.base.BaseWebChromeClient;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.example.weizhenbin.mydemo.widget.CommonWebView;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 16/5/16.
 */
public class WebActivity extends BaseActivity {
    CommonWebView commonWebView;
    String url;
    Toolbar toolbar;
    ProgressBar progressBar;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        toolbar= CommonToolbar.getToolBar(this);
        commonWebView= (CommonWebView) findViewById(R.id.wb);
        progressBar= (ProgressBar) findViewById(R.id.pb);
        url=getIntent().getStringExtra("url");
        commonWebView.loadUrl(url);
    }

    public static void startWeb(Context context,String url){
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        commonWebView.setiProgressChanged(new BaseWebChromeClient.IProgressChanged() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               /* if(newProgress>=100){
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }*/
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
