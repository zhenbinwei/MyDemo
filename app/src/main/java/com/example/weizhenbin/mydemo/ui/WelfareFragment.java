package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.mydemo.baidumap.BaiduLocationManage;
import com.example.weizhenbin.mydemo.base.BaseBaiduMapFragment;
import com.example.weizhenbin.mydemo.bean.LocationInfo;
import com.example.weizhenbin.mydemo.bean.WelfareBean;
import com.example.weizhenbin.mydemo.retrofit.IRequestCallBackAdapter;
import com.example.weizhenbin.mydemo.retrofit.RetrofitUtil;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.ExpandRecyclerView;
import com.weizhenbin.show.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: WelfareFragment
 * @ProjectName Show
 * @Package com.weizhenbin.show.ui
 * @date 2016/2/24 15:54
 */
public class WelfareFragment extends BaseBaiduMapFragment implements View.OnClickListener, BaiduLocationManage.OnLocationInfoListener {
    ExpandRecyclerView recyclerView;

    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welfare, null);
    }

    @Override
    protected void initView(View view) {
        CommonToolbar.setTitle(view,"福利");
        recyclerView= (ExpandRecyclerView) view.findViewById(R.id.content_list);

    }

    private float y;
    @Override
    protected void initEvent() {


    }

    @Override
    protected void initData() {



        new RetrofitUtil().setUrl("data/福利/10/1").setiRequsetCallBack(new IRequestCallBackAdapter(){
            @Override
            public void requestSuccess(String s) {
                super.requestSuccess(s);
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    WelfareBean.paseJsonData(jsonObject);
                    WelfareListAdapter welfareListAdapter=new WelfareListAdapter(getActivity(),WelfareBean.paseJsonData(jsonObject).results);
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(welfareListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).GET();


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLocation(LocationInfo info) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    class WelfareListAdapter extends RecyclerView.Adapter<MyHolder>{

        private Context context;
        private List<WelfareBean.ResultsBean> resultsBeen;

        public WelfareListAdapter(Context context, List<WelfareBean.ResultsBean> resultsBeen) {
            this.context = context;
            this.resultsBeen = resultsBeen;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.welfare_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Glide.with(context).load(resultsBeen.get(position).url).into(holder.ivPic);
            holder.tvContent.setText(resultsBeen.get(position).who);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Observable.create(new ObservableOnSubscribe<String>() {

                        @Override
                        public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                            e.onNext("aaa");
                            e.onComplete();
                        }
                    }).subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {
                            Log.d("WelfareFragment", s);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return resultsBeen.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ivPic;
        private TextView tvContent;
        public MyHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
