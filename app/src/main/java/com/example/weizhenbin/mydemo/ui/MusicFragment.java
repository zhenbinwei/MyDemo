package com.example.weizhenbin.mydemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.adapters.MusicListAdapter;
import com.example.weizhenbin.mydemo.base.BaseFragment;
import com.example.weizhenbin.mydemo.bean.MusicListBean;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.example.weizhenbin.mydemo.retrofit.IRequestCallBackAdapter;
import com.example.weizhenbin.mydemo.retrofit.RetrofitUtil;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.ExpandRecyclerView;
import com.weizhenbin.show.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by weizhenbin on 17/6/21.
 */

public class MusicFragment extends BaseFragment implements MusicServiceControl.OnMusicChangeListener{
    ExpandRecyclerView contentList;
    int type_id;
    String type;
    int startIndex=0;
    MusicListAdapter musicListAdapter;
    List<MusicListBean.ShowapiResBodyBean.PagebeanBean.SonglistBean> listBeen=new ArrayList<>();
    String topid="26";
    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_fragment,null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            type = (String) getArguments().get("type");
            if(!TextUtils.isEmpty(type)){
                if(type.equals("欧美")){
                   topid="3";
                }else if(type.equals("内地")){
                    topid="5";
                }else if(type.equals("日本")){
                    topid="17";
                }else if(type.equals("港台")){
                    topid="6";
                }else if(type.equals("韩国")){
                    topid="16";
                }else if(type.equals("民谣")){
                    topid="18";
                }else if(type.equals("摇滚")){
                    topid="19";
                }else if(type.equals("销量")){
                    topid="23";
                }else if(type.equals("热歌")){
                    topid="26";
                }
            }
        }
    }

    public static MusicFragment createFragment(String type){
        MusicFragment newsFragment=new MusicFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    private void getData() {
        HashMap<String,String> parames=new HashMap<>();
        parames.put("topid",topid);
        new RetrofitUtil(RetrofitUtil.ReqType.ALI_MUSIC).setUrl("top").setiRequsetCallBack(new IRequestCallBackAdapter(){
            @Override
            public void requestSuccess(String s) {
                super.requestSuccess(s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    MusicListBean musicListBean= MusicListBean.paseJsonData(jsonObject);

                    listBeen.addAll(musicListBean.showapi_res_body.pagebean.songlist);
                    if(musicListAdapter==null){
                        musicListAdapter= new MusicListAdapter(getActivity(),listBeen);
                        contentList.setAdapter(musicListAdapter);
                    }else {
                        musicListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }).setParames(parames).GET();
    }

    @Override
    protected void initView(View view) {
        contentList= (ExpandRecyclerView) view.findViewById(R.id.content_list);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),3);
        contentList.setLayoutManager(layoutManager);
        MusicServiceControl.getServiceControl().addListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MusicServiceControl.getServiceControl().removeListener(this);
    }

    @Override
    public void onMusicChange() {
        if(musicListAdapter!=null){
            musicListAdapter.notifyDataSetChanged();
        }
    }
}
