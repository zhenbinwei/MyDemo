package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.adapters.GanhuoListAdapter;
import com.example.weizhenbin.mydemo.base.NewItemBaseFragment;
import com.example.weizhenbin.mydemo.bean.GanhuoListBean;
import com.example.weizhenbin.mydemo.retrofit.IRequestCallBackAdapter;
import com.example.weizhenbin.mydemo.retrofit.RetrofitUtil;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.ExpandRecyclerView;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.OnLoadListener;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.OnRefreshListener;
import com.weizhenbin.show.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by weizhenbin on 17/1/2.
 */

public class NewItemFragment extends NewItemBaseFragment {
    ExpandRecyclerView contentList;
    String title;
    int page=1;
    GanhuoListAdapter ganhuoListAdapter;
    public List<GanhuoListBean.ResultsBean> results=new ArrayList<>();
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
        RecyclerView.LayoutManager layoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        contentList.setLayoutManager(layoutManager);
        contentList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
            }
        });
        contentList.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                getData();
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        new RetrofitUtil().setUrl("data/"+title+"/10/"+page).setiRequsetCallBack(new IRequestCallBackAdapter(){
            @Override
            public void requestSuccess(String s) {
                super.requestSuccess(s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    GanhuoListBean ganhuoListBean= GanhuoListBean.paseJsonData(jsonObject);
                    if(page==1){
                        results.clear();
                        contentList.refreshComplete();
                    }else {
                        contentList.loadComplete();
                    }
                    results.addAll(ganhuoListBean.results);
                    if(ganhuoListAdapter==null){
                        ganhuoListAdapter= new GanhuoListAdapter(getActivity(),results);
                        contentList.setAdapter(ganhuoListAdapter);
                    }else {
                        ganhuoListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).GET();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
