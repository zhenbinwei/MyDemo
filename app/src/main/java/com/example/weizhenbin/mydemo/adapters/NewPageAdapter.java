package com.example.weizhenbin.mydemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.weizhenbin.mydemo.base.GanhuoBaseFragment;

import java.util.List;

/**
 * Created by weizhenbin on 17/1/2.
 */

public class NewPageAdapter extends FragmentPagerAdapter {

    List<GanhuoBaseFragment> fragments;
    List<String> tabs;
    public NewPageAdapter(FragmentManager fm, List<GanhuoBaseFragment> fragments, List<String> tabs) {
        super(fm);
        this.fragments=fragments;
        this.tabs=tabs;
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
        if(position<tabs.size()) {
            return tabs.get(position);
        }else {
            return "";
        }
    }

}
