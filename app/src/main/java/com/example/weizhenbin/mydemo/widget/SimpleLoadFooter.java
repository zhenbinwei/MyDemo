package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.util.Tools;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.BaseLoadFooter;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/3/7.
 */
public class SimpleLoadFooter extends BaseLoadFooter {
    ProgressBar progressBar;
    TextView tv;
    public SimpleLoadFooter(Context context) {
        this(context,null);
    }

    public SimpleLoadFooter(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleLoadFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.simple_refresh_header,this);
        progressBar= (ProgressBar) findViewById(R.id.pb);
        tv= (TextView) findViewById(R.id.tv);
        progressBar.setVisibility(GONE);
    }

    @Override
    protected void move(int height) {

    }

    @Override
    protected int loadHeight() {
        return Tools.dp2px(getContext(),60);
    }

    @Override
    protected void load(boolean canRefresh) {
        if(canRefresh){
            tv.setText("松开加载");
        }else {
            tv.setText("继续上拉");
        }
    }

    @Override
    protected void loosenAndLoad() {
        progressBar.setVisibility(VISIBLE);
        tv.setText("加载...");
    }

    @Override
    protected void reset() {
        progressBar.setVisibility(GONE);
        tv.setText("");
    }
}
