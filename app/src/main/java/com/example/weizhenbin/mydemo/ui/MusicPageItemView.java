package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.weizhenbin.show.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.view.animation.Animation.INFINITE;
import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static android.view.animation.Animation.RESTART;
import static com.weizhenbin.show.R.id.rl_bg;

/**
 * Created by weizhenbin on 17/7/5.
 */

public class MusicPageItemView extends RelativeLayout{
    RelativeLayout rlBg;
    ImageView ivPic;
    Context context;
    public MusicPageItemView(Context context) {
        this(context,null);
    }

    public MusicPageItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicPageItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.music_page_item,this);
        rlBg= (RelativeLayout) findViewById(rl_bg);
        ivPic= (ImageView) findViewById(R.id.iv_pic);
    }
    public void setMusicInfo(MusicServiceControl.MusicInfo musicInfo){
        if(musicInfo!=null){
            Glide.with(context).load(musicInfo.getAlbumpicBig()).asBitmap().transform(new CropCircleTransformation(context)).into(ivPic);
        }
        rlBg.startAnimation(getAnimation());
    }

    public RotateAnimation getAnimation(){
        RotateAnimation rotateAnimation=new RotateAnimation(0,360,RELATIVE_TO_SELF,0.5f,RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(6000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(INFINITE);
        rotateAnimation.setRepeatMode(RESTART);
        return rotateAnimation;
    }
}
