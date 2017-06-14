package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.adapters.NewPageAdapter;
import com.example.weizhenbin.mydemo.base.BaseFragment;
import com.example.weizhenbin.mydemo.base.NewItemBaseFragment;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
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
    List<NewItemBaseFragment> newItemBaseFragments=new ArrayList<>();
    List<String> tabs=new ArrayList<>();
    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, null);
    }

    @Override
    protected void initView(View view) {
        CommonToolbar.setTitle(view,"最新");
        tabLayout= (TabLayout) view.findViewById(R.id.tl_tags);
        viewPager= (ViewPager) view.findViewById(R.id.vp_content);
        tabLayout.setupWithViewPager(viewPager,false);
        initTabs();
        for (int i = 0; i < tabs.size(); i++) {
            newItemBaseFragments.add(NewItemFragment.createFragment(tabs.get(i)));
        }
        viewPager.setAdapter(new NewPageAdapter(getFragmentManager(),newItemBaseFragments,tabs));
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
