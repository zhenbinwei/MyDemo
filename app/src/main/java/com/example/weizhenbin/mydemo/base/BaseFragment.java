package com.example.weizhenbin.mydemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: BaseFragment
 * @ProjectName Show
 * @Package com.weizhenbin.show.base
 * @date 2016/2/24 9:29
 * 基础的Fragment 所有Fragment都要继承BaseFragment
 */
public abstract class BaseFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = setView(inflater, container, savedInstanceState);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    protected abstract View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    protected abstract void initView(View view);
    protected abstract void initEvent();
    protected abstract void initData();
}
