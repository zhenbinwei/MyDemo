package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.adapters.NewPageAdapter;
import com.example.weizhenbin.mydemo.base.BaseFragment;
import com.example.weizhenbin.mydemo.base.GanhuoBaseFragment;
import com.weizhenbin.show.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: WelfareFragment
 * @ProjectName Show
 * @Package com.weizhenbin.show.ui
 * @date 2016/2/24 15:54
 */
public class NewFragment extends BaseFragment implements View.OnClickListener{

    TabLayout tabLayout;
    ViewPager viewPager;
    List<GanhuoBaseFragment> ganhuoBaseFragments =new ArrayList<>();
    List<String> tabs=new ArrayList<>();
    Toolbar toolbar;
    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, null);
    }

    @Override
    protected void initView(View view) {
        tabLayout= (TabLayout) view.findViewById(R.id.tl_tags);
        viewPager= (ViewPager) view.findViewById(R.id.vp_content);
        toolbar= (Toolbar) view.findViewById(R.id.tb);
        toolbar.setTitle("首页");
        tabLayout.setupWithViewPager(viewPager,false);
        initTabs();
        for (int i = 0; i < tabs.size(); i++) {
            ganhuoBaseFragments.add(GanhuoFragment.createFragment(tabs.get(i)));
        }
        viewPager.setAdapter(new NewPageAdapter(getFragmentManager(), ganhuoBaseFragments,tabs));
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
    }




    @Override
    public void onClick(View v) {


    }


    private void initTabs(){
        //category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
        //福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
        tabs.add("福利");
        tabs.add("Android");
        /*  tabs.add("iOS");
        tabs.add("休息视频");
        tabs.add("拓展资源");
        tabs.add("前端");*/
    }


}
