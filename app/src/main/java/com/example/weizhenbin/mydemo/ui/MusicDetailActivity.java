package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 17/7/1.
 */

public class MusicDetailActivity extends BaseActivity implements MusicServiceControl.OnMusicChangeListener{

    Toolbar toolbar;
    LinearLayout llMusic;
    ImageView ivPic;
    ImageView ivPrevious,ivPlay,ivNext;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_music_detail);
        toolbar= CommonToolbar.getToolBar(this);
        llMusic= (LinearLayout) findViewById(R.id.ll_music);
        ivPic= (ImageView) findViewById(R.id.iv_pic);
        ivPrevious= (ImageView) findViewById(R.id.iv_previous);
        ivNext= (ImageView) findViewById(R.id.iv_next);
        ivPlay= (ImageView) findViewById(R.id.iv_play);
        MusicServiceControl.getServiceControl().addListener(this);
    }

    @Override
    protected void initData() {
        setMusicInfo();
    }

    private void setMusicInfo() {
        MusicServiceControl.MusicInfo musicInfo=MusicServiceControl.getServiceControl().getMusicInfo();
        if(musicInfo!=null){
            Log.d("MusicDetailActivity", musicInfo.getSongname());
            toolbar.setTitle(musicInfo.getSongname());
            toolbar.setSubtitle(musicInfo.getSingername());
            if(!TextUtils.isEmpty(musicInfo.getAlbumpicBig())){
                Glide.with(MusicDetailActivity.this).load(musicInfo.getAlbumpicBig()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivPic.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch a = palette.getDominantSwatch();//getMutedSwatch();
                                if(a!=null) {
                                   llMusic.setBackgroundColor(a.getRgb());
                                    toolbar.setBackgroundColor(a.getRgb());
                                }
                            }
                        });
                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                    }
                });
            }
        }
    }

    @Override
    protected void initEvent() {
      ivNext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              MusicServiceControl.getServiceControl().next();
          }
      });
        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicServiceControl.getServiceControl().previous();
            }
        });
    }


    public static void startActivity(Context context){

        context.startActivity(new Intent(context,MusicDetailActivity.class));

    }

    @Override
    public void onMusicChange() {
        setMusicInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicServiceControl.getServiceControl().removeListener(this);
    }
}
