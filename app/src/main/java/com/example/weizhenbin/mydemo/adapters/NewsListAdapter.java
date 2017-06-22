package com.example.weizhenbin.mydemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.mydemo.bean.TodayNewsBean;
import com.example.weizhenbin.mydemo.ui.WebActivity;
import com.weizhenbin.show.R;

import java.util.List;

/**
 * Created by weizhenbin on 2017/6/14.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsHolder>{

    private Context context;
    private List<TodayNewsBean.ResultBean.ListBean> listBeen;

    public NewsListAdapter(Context context, List<TodayNewsBean.ResultBean.ListBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final TodayNewsBean.ResultBean.ListBean listBean=listBeen.get(position);
        holder.tvTitle.setText(listBean.title);
        if(!TextUtils.isEmpty(listBean.pic)) {
            holder.ivPic.setVisibility(View.VISIBLE);
            Glide.with(context).load(listBean.pic).asBitmap().into(holder.ivPic);
        }else {
            holder.ivPic.setVisibility(View.GONE);
        }
        holder.tvTime.setText(listBean.time);
        holder.tvSrc.setText(listBean.src);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.startWeb(context,listBean.weburl);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listBeen.size();
    }

   public class NewsHolder extends RecyclerView.ViewHolder{
       TextView tvTitle;
       TextView tvTime;
       TextView tvSrc;
       ImageView ivPic;
        public NewsHolder(View itemView) {
            super(itemView);
            tvTitle= (TextView) itemView.findViewById(R.id.tv_title);
            ivPic= (ImageView) itemView.findViewById(R.id.iv_pic);
            tvSrc= (TextView) itemView.findViewById(R.id.tv_src);
            tvTime= (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

}
