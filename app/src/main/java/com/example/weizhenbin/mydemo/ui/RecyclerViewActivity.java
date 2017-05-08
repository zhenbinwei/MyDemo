package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.SimpleRefeshHeader;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.ExpandRecyclerView;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.OnLoadListener;
import com.example.weizhenbin.mydemo.widget.refreshrecyclerview.OnRefreshListener;
import com.weizhenbin.show.R;

import java.util.ArrayList;


/**
 * Created by weizhenbin on 17/1/8.
 */

public class RecyclerViewActivity extends BaseActivity {
    ExpandRecyclerView rvConetents;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> ss=new ArrayList<>();
    TestAdapter testAdapter;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_recyclerview);
        rvConetents= (ExpandRecyclerView) findViewById(R.id.rv_contents);
        //layoutManager=new GridLayoutManager(this,2);
       // layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        layoutManager=new LinearLayoutManager(this);
        rvConetents.setLayoutManager(layoutManager);
        rvConetents.setSupportPullRefresh(true);
        rvConetents.setSupportPullLoad(true);
        for (int i=0;i<3;i++){
            ss.add("字符初始"+i);
        }
        testAdapter=new TestAdapter(this,ss);
        rvConetents.setAdapter(testAdapter);
        View v=LayoutInflater.from(this).inflate(R.layout.test,null);

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("RecyclerViewActivity", "点击了");
            }
        });
        rvConetents.addHeadView(new SimpleRefeshHeader(this));
        rvConetents.addHeadView(v);
        rvConetents.addHeadView(LayoutInflater.from(this).inflate(R.layout.test,null));
        rvConetents.addHeadView(LayoutInflater.from(this).inflate(R.layout.test,null));
        rvConetents.addFootView(LayoutInflater.from(this).inflate(R.layout.test,null));
        //rvConetents.addFootView(LayoutInflater.from(this).inflate(R.layout.test,null));
        //rvConetents.addFootView(LayoutInflater.from(this).inflate(R.layout.test,null));
        //rvConetents.addFootView(new SimpleLoadFooter(this));
        rvConetents.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("RecyclerViewActivity", "刷新");
                rvConetents.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ss.clear();
                        for (int i=0;i<2;i++){
                            ss.add("字符新加"+i);
                        }
                       testAdapter.notifyDataSetChanged();

                        rvConetents.refreshComplete();
                    }
                },3000);
            }
        });
        rvConetents.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                Log.d("RecyclerViewActivity", "加载");
                rvConetents.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvConetents.loadComplete();
                        for (int i=0;i<10;i++){
                            ss.add("字符新加"+i);
                        }
                        testAdapter.notifyDataSetChanged();
                    }
                },3000);
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    class TestAdapter extends RecyclerView.Adapter<MyHolder>{
        /**RecyclerView 没有添加头部和尾部相应的方法  都是加载不同类型view来实现的*/
        private Context context;
        private ArrayList<String> ss;
        public TestAdapter(Context context,ArrayList<String> ss) {
            this.context = context;
            this.ss=ss;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(context).inflate(R.layout.test_list_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.textView.setText("普通item"+ss.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TestAdapter", "点击了" + position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return ss.size();
        }
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.tv);
        }
    }
    class HeaderHolder extends RecyclerView.ViewHolder{
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }
    class FootHolder extends RecyclerView.ViewHolder{
        public FootHolder(View itemView) {
            super(itemView);
        }
    }
}
