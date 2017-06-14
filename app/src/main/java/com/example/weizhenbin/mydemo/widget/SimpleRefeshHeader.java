package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.util.Tools;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.BaseRefreshHeader;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/3/4.
 */
public class SimpleRefeshHeader extends BaseRefreshHeader {
    ProgressBar progressBar;
    TextView tv;
    public SimpleRefeshHeader(Context context) {
        this(context,null);
    }

    public SimpleRefeshHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleRefeshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.simple_refresh_header,this);
        progressBar= (ProgressBar) findViewById(R.id.pb);
        tv= (TextView) findViewById(R.id.tv);
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void move(int height) {
        Log.d("SimpleRefeshHeader", "progressBar:" + progressBar.getHeight());
    }

    @Override
    protected int refreshHeight() {
        return Tools.dp2px(getContext(),60);
    }

    @Override
    protected void refresh(boolean canRefresh) {
        if(canRefresh){
            tv.setText("松开刷新");
        }else {
            tv.setText("继续下拉");
        }
    }

    @Override
    protected void loosenAndRefresh() {
        progressBar.setVisibility(VISIBLE);
        tv.setText("刷新...");
    }

    @Override
    protected void reset() {
        progressBar.setVisibility(GONE);
        tv.setText("");
    }
}
