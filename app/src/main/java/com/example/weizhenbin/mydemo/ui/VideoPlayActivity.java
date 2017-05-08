package com.example.weizhenbin.mydemo.ui;

import android.util.Log;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.VideoPlayerView;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/4/19.
 */
public class VideoPlayActivity extends BaseActivity {
    VideoPlayerView videoPlayerView;
    @Override
    protected void initView() {
        setContentView(R.layout.video_layout);
        videoPlayerView= (VideoPlayerView) findViewById(R.id.vp);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("VideoPlayActivity", "onDestroy");
    }
}
