package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by weizhenbin on 17/7/1.
 */

public class MusicDetailActivity extends BaseActivity implements MusicServiceControl.OnMusicChangeListener{

    Toolbar toolbar;
    LinearLayout llMusic;
    ImageView ivPrevious,ivPlay,ivNext;
    ViewPager viewPager;
    ImageView ivBg;
    ImageView ivAction;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_music_detail);
        toolbar= CommonToolbar.getToolBar(this);
        toolbar.setBackgroundColor(Color.parseColor("#01000000"));
        llMusic= (LinearLayout) findViewById(R.id.ll_music);
        ivPrevious= (ImageView) findViewById(R.id.iv_previous);
        ivNext= (ImageView) findViewById(R.id.iv_next);
        ivPlay= (ImageView) findViewById(R.id.iv_play);
        viewPager= (ViewPager) findViewById(R.id.music_page);
        ivBg= (ImageView) findViewById(R.id.bg);
        ivAction= (ImageView) findViewById(R.id.iv_action);
        MusicServiceControl.getServiceControl().addListener(this);

    }

    @Override
    protected void initData() {
        setMusicInfo();
        viewPager.setAdapter(new ViewPageAdapter(MusicDetailActivity.this,MusicServiceControl.getServiceControl().getMusicInfos()));
        viewPager.setCurrentItem(MusicServiceControl.getServiceControl().getCurrentIndex());
    }

    private void setMusicInfo() {
        MusicServiceControl.MusicInfo musicInfo=MusicServiceControl.getServiceControl().getMusicInfo();
        if(musicInfo!=null){
            Log.d("MusicDetailActivity", musicInfo.getSongname());
            toolbar.setTitle(musicInfo.getSongname());
            toolbar.setSubtitle(musicInfo.getSingername());
            if(!TextUtils.isEmpty(musicInfo.getAlbumpicBig())){
                Glide.with(MusicDetailActivity.this).load(musicInfo.getAlbumpicBig())
                        .bitmapTransform(new BlurTransformation(MusicDetailActivity.this,23,4)).into(ivBg);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("MusicDetailActivity", "position:" + position);
                MusicServiceControl.getServiceControl().setmCurrentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("MusicDetailActivity", "state:" + state);
                Log.d("MusicDetailActivity", "ivAction.getRotation():" + ivAction.getRotationX()+";"+ivAction.getRotationY());
                if(state==1){
                    ivAction.startAnimation(getAnimation(0,-30,0,0));
                }else if(state==0){
                    ivAction.startAnimation(getAnimation(-30,0,0,0));
                }
            }
        });
    }
    public RotateAnimation getAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY){
        RotateAnimation rotateAnimation=new RotateAnimation(fromDegrees,toDegrees,pivotX,pivotY);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        return rotateAnimation;
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


    class ViewPageAdapter extends PagerAdapter{

        List<? extends MusicServiceControl.MusicInfo> musicInfos;
        List<MusicPageItemView> views=new ArrayList<>();

        public ViewPageAdapter(Context context,List<? extends MusicServiceControl.MusicInfo> musicInfos) {
            this.musicInfos = musicInfos;
            for (int i = 0; i < 5; i++) {
                views.add(new MusicPageItemView(context));
            }
        }

        @Override
        public int getCount() {
            return musicInfos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MusicPageItemView v=views.get(position%5);
            v.setMusicInfo(musicInfos.get(position));
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v=views.get(position%5);
            container.removeView(v);
        }
    }


}
