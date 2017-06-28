package com.example.weizhenbin.mydemo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.weizhenbin.mydemo.bean.MusicListBean;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.example.weizhenbin.mydemo.widget.MusicAnimIcon;
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
    public void onBindViewHolder(NewsHolder holder, int position, List<Object> payloads) {
        if(payloads.isEmpty()){
            onBindViewHolder(holder,position);
        }else {
            if(position<listBeen.size()){
                if(MusicServiceControl.getServiceControl().getMusicInfo()!=null){
                    if(listBeen.get(position).songid==MusicServiceControl.getServiceControl().getMusicInfo().getSongid()){
                        holder.maPlayTag.setVisibility(View.VISIBLE);
                    }else {
                        holder.maPlayTag.setVisibility(View.GONE);
                    }
                }else {
                    holder.maPlayTag.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(final NewsHolder holder, final int position) {
        final MusicListBean.ShowapiResBodyBean.PagebeanBean.SonglistBean listBean=listBeen.get(position);
        holder.tvSongname.setText(listBean.songname);
        if(!TextUtils.isEmpty(listBean.albumpic_big)){
            holder.ivPic.setVisibility(View.VISIBLE);
            Glide.with(context).load(listBean.albumpic_big).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    holder.ivPic.setImageBitmap(resource);

                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            Palette.Swatch a = palette.getDominantSwatch();//getMutedSwatch();
                            if(a!=null) {
                                holder.rlBg.setBackgroundColor(a.getRgb());
                            }
                        }
                    });
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    holder.ivPic.setImageResource(R.drawable.def_fff);
                }
            });
        }else {
            holder.ivPic.setVisibility(View.GONE);
        }
        if(MusicServiceControl.getServiceControl().getMusicInfo()!=null){
            if(listBean.songid==MusicServiceControl.getServiceControl().getMusicInfo().getSongid()){
                holder.maPlayTag.setVisibility(View.VISIBLE);
            }else {
                holder.maPlayTag.setVisibility(View.GONE);
            }
        }else {
            holder.maPlayTag.setVisibility(View.GONE);
        }

        holder.tvSingername.setText(listBean.singername);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicServiceControl.getServiceControl().startList(listBeen,position);
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
       RelativeLayout rlBg;
       MusicAnimIcon maPlayTag;
        public NewsHolder(View itemView) {
            super(itemView);
            tvSongname= (TextView) itemView.findViewById(R.id.tv_songname);
            tvSingername= (TextView) itemView.findViewById(R.id.tv_singername);
            ivPic= (ImageView) itemView.findViewById(R.id.iv_pic);
            rlBg= (RelativeLayout) itemView.findViewById(R.id.rl_bg);
            maPlayTag= (MusicAnimIcon) itemView.findViewById(R.id.ma_play_tag);
        }
    }

}
