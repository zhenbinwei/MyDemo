package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.adapters.NewsListAdapter;
import com.example.weizhenbin.mydemo.base.BaseFragment;
import com.example.weizhenbin.mydemo.bean.TodayNewsBean;
import com.example.weizhenbin.mydemo.retrofit.IRequestCallBackAdapter;
import com.example.weizhenbin.mydemo.retrofit.RetrofitUtil;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.ExpandRecyclerView;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.OnLoadListener;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.OnRefreshListener;
import com.weizhenbin.show.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by weizhenbin on 17/6/21.
 */

public class NewsFragment extends BaseFragment {
    ExpandRecyclerView contentList;
    int type_id;
    String type;
    int startIndex=0;
    NewsListAdapter newsListAdapter;
    List<TodayNewsBean.ResultBean.ListBean> listBeen=new ArrayList<>();
    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment,null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            type = (String) getArguments().get("type");
        }
    }

    public static NewsFragment createFragment(String type){
        NewsFragment newsFragment=new NewsFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    private void getData() {
        HashMap<String,String> parames=new HashMap<>();
        parames.put("channel",type);
        parames.put("start",startIndex+"");
        new RetrofitUtil(RetrofitUtil.ReqType.ALI_NEWS).setUrl("news/get").setiRequsetCallBack(new IRequestCallBackAdapter(){
            @Override
            public void requestSuccess(String s) {
                super.requestSuccess(s);
                Log.d("NewsFragment", s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    TodayNewsBean todayNewsBean= TodayNewsBean.paseJsonData(jsonObject);
                    if(startIndex==0){
                        listBeen.clear();
                        contentList.refreshComplete();
                    }else {
                        contentList.loadComplete();
                    }
                    listBeen.addAll(todayNewsBean.result.list);
                    if(newsListAdapter==null){
                        newsListAdapter= new NewsListAdapter(getActivity(),listBeen);
                        contentList.setAdapter(newsListAdapter);
                    }else {
                        newsListAdapter.notifyDataSetChanged();
                    }
                    startIndex=startIndex+Integer.parseInt(todayNewsBean.result.num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }).setParames(parames).GET();
    }

    @Override
    protected void initView(View view) {
        contentList= (ExpandRecyclerView) view.findViewById(R.id.content_list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        contentList.setLayoutManager(layoutManager);
        contentList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                startIndex=0;
                getData();
            }
        });
        contentList.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
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
}
