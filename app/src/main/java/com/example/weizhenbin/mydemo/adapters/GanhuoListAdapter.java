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
import com.example.weizhenbin.mydemo.ui.WebActivity;
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
        }else if(viewType==12){
            return new CommonHolder(LayoutInflater.from(context).inflate(R.layout.common_list_item,parent,false));
        }else{
            return new CommonHolder(LayoutInflater.from(context).inflate(R.layout.common_list_item,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GanhuoListBean.ResultsBean resultsBean=ganhuoBeanList.get(position);
           if(holder instanceof GanhuoHolder){
               Glide.with(context).load(resultsBean.url).into(((GanhuoHolder)holder).ivPic);
               ((GanhuoHolder)holder).tvContent.setText(resultsBean.who);
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       WebActivity.startWeb(context,resultsBean.url);
                   }
               });
           }else if(holder instanceof CommonHolder){
               ((CommonHolder)holder).tvTitle.setText(resultsBean.desc);
               if (resultsBean.images!=null&&resultsBean.images.size()>0){
                   ((CommonHolder)holder).ivPic.setVisibility(View.VISIBLE);
                   Glide.with(context).load(resultsBean.images.get(0)).asBitmap().into(((CommonHolder)holder).ivPic);
               }else {
                   ((CommonHolder)holder).ivPic.setVisibility(View.GONE);
               }
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       WebActivity.startWeb(context,resultsBean.url);
                   }
               });
           }
    }

    @Override
    public int getItemViewType(int position) {
        if("福利".equals(ganhuoBeanList.get(position).type)){
            return 11;
        }else if("Android".equals(ganhuoBeanList.get(position).type)
                ||"iOS".equals(ganhuoBeanList.get(position).type)
                ||"前端".equals(ganhuoBeanList.get(position).type)
                ||"休息视频".equals(ganhuoBeanList.get(position).type)
                ||"拓展资源".equals(ganhuoBeanList.get(position).type)){
            return 12;
        }
        return super.getItemViewType(position);
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
    public class CommonHolder extends RecyclerView.ViewHolder{
        ImageView ivPic;
        TextView tvTitle;
        public CommonHolder(View itemView) {
            super(itemView);
            ivPic= (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
