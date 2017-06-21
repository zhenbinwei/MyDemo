package com.example.weizhenbin.mydemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.bean.TodayNewsBean;
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
        holder.tvContent.setText(Html.fromHtml(listBeen.get(position).content));
    }


    @Override
    public int getItemCount() {
        return listBeen.size();
    }

   public class NewsHolder extends RecyclerView.ViewHolder{
       TextView tvContent;
        public NewsHolder(View itemView) {
            super(itemView);
            tvContent= (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

}
