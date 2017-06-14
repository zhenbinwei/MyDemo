package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.base.BaseBaiduMapFragment;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;


/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: WelfareFragment
 * @ProjectName Show
 * @Package com.weizhenbin.show.ui
 * @date 2016/2/24 15:54
 */
public class WelfareFragment extends BaseBaiduMapFragment{

    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welfare, null);
    }

    @Override
    protected void initView(View view) {
        CommonToolbar.setTitle(view,"新闻");

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

}
