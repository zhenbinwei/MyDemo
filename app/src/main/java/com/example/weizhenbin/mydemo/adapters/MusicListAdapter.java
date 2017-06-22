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
import com.example.weizhenbin.mydemo.bean.MusicListBean;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.weizhenbin.show.R;

import java.util.List;

/**
 * Created by weizhenbin on 2017/6/14.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.NewsHolder>{

    private Context context;
    private List<MusicListBean.ShowapiResBodyBean.PagebeanBean.SonglistBean> listBeen;

    public MusicListAdapter(Context context, List<MusicListBean.ShowapiResBodyBean.PagebeanBean.SonglistBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.music_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final MusicListBean.ShowapiResBodyBean.PagebeanBean.SonglistBean listBean=listBeen.get(position);
        holder.tvSongname.setText(listBean.songname);
        if(!TextUtils.isEmpty(listBean.albumpic_small)){
            holder.ivPic.setVisibility(View.VISIBLE);
            Glide.with(context).load(listBean.albumpic_small).asBitmap().into(holder.ivPic);
        }else {
            holder.ivPic.setVisibility(View.GONE);
        }
        holder.tvSingername.setText(listBean.singername);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicServiceControl.start(listBean.url);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listBeen.size();
    }

   public class NewsHolder extends RecyclerView.ViewHolder{
       TextView tvSongname;
       TextView tvSingername;
       ImageView ivPic;
        public NewsHolder(View itemView) {
            super(itemView);
            tvSongname= (TextView) itemView.findViewById(R.id.tv_songname);
            tvSingername= (TextView) itemView.findViewById(R.id.tv_singername);
            ivPic= (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }

}
