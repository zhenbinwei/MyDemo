package com.example.weizhenbin.mydemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.mydemo.bean.GanhuoListBean;
import com.weizhenbin.show.R;

import java.util.List;

/**
 * Created by weizhenbin on 2017/6/14.
 */

public class GanhuoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<GanhuoListBean.ResultsBean> ganhuoBeanList;

    public GanhuoListAdapter(Context context, List<GanhuoListBean.ResultsBean> ganhuoBeanList) {
        this.context = context;
        this.ganhuoBeanList = ganhuoBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==11){
            return new GanhuoHolder(LayoutInflater.from(context).inflate(R.layout.welfare_list_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(holder instanceof GanhuoHolder){
               Glide.with(context).load(ganhuoBeanList.get(position).url).into(((GanhuoHolder)holder).ivPic);
               ((GanhuoHolder)holder).tvContent.setText(ganhuoBeanList.get(position).who);
           }
    }

    @Override
    public int getItemViewType(int position) {
        if("福利".equals(ganhuoBeanList.get(position).type)){
            return 11;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return ganhuoBeanList.size();
    }

   public class GanhuoHolder extends RecyclerView.ViewHolder{
       ImageView ivPic;
       TextView tvContent;
        public GanhuoHolder(View itemView) {
            super(itemView);
            ivPic= (ImageView) itemView.findViewById(R.id.iv_pic);
            tvContent= (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
