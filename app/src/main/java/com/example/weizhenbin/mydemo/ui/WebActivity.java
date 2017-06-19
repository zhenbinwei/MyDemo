package com.example.weizhenbin.mydemo.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

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
    WebProgressView wpvProgress;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
        toolbar= CommonToolbar.getToolBar(this);
        commonWebView= (CommonWebView) findViewById(R.id.wb);
        wpvProgress= (WebProgressView) findViewById(R.id.wpv_progress);
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
                if(newProgress>=100){
                    wpvProgress.setVisibility(View.GONE);
                }else {
                    wpvProgress.setVisibility(View.VISIBLE);
                    wpvProgress.onProgressChange(newProgress);
                }
            }
        });
        commonWebView.setiReceivedTitle(new BaseWebChromeClient.IReceivedTitle() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (commonWebView!=null){
            commonWebView.removeAllViews();
            commonWebView.destroy();
            commonWebView=null;
        }
    }
}
