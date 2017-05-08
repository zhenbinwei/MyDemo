package com.example.weizhenbin.mydemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: MainAdapter
 * @ProjectName Show
 * @Package com.weizhenbin.show.adapters
 * @date 2016/2/24 15:29
 * 模块适配器
 */
public class MainAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private String[] strings=new String[]{"chat","main","me"};
    public MainAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
