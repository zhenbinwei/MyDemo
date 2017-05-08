package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.base.NewItemBaseFragment;
import com.example.weizhenbin.mydemo.retrofit.RetrofitUtil;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.ExpandRecyclerView;
import com.weizhenbin.show.R;


/**
 * Created by weizhenbin on 17/1/2.
 */

public class NewItemFragment extends NewItemBaseFragment {
    ExpandRecyclerView contentList;
    String title;
    @Override
    public String pageTitle(int position) {
        return title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            title = (String) getArguments().get("title");
        }
    }

    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_item_fragment,null);
    }

    public static NewItemFragment createFragment(String title){
        NewItemFragment newItemFragment=new NewItemFragment();
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        newItemFragment.setArguments(bundle);
        return newItemFragment;
    }

    @Override
    protected void initView(View view) {
        contentList= (ExpandRecyclerView) view.findViewById(R.id.content_list);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
