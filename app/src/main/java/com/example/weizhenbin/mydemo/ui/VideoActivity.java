package com.example.weizhenbin.mydemo.ui;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;

import java.io.IOException;

/**
 * Created by weizhenbin on 16/12/13.
 */

public class VideoActivity extends BaseActivity implements View.OnClickListener{




    @Override
    protected void initEvent() {
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        pre.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_video);
        CommonToolbar.setTitle(this,"视频");
        surface1 = (SurfaceView)findViewById(R.id.surface1);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        pre = (Button)findViewById(R.id.pre);
        mediaPlayer1 = new MediaPlayer();
        //设置播放时打开屏幕
        surface1.getHolder().setKeepScreenOn(true);
        surface1.getHolder().addCallback(new SurfaceViewLis());
    }

    @Override
    protected void initData() {
    }


    private SurfaceView surface1;
    private Button start, stop, pre;
    private MediaPlayer mediaPlayer1;

    private int postion = 0;





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                try {
                    play();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.pre:
                if (mediaPlayer1.isPlaying()) {
                    mediaPlayer1.pause();
                } else {
                    mediaPlayer1.start();
                }
                break;
            case R.id.stop:
                if (mediaPlayer1.isPlaying())
                    mediaPlayer1.stop();
                break;
            default:
                break;
        }

    }

    public void play() throws IllegalArgumentException, SecurityException,
            IllegalStateException, IOException {
        mediaPlayer1.reset();
        mediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer1.setDataSource("http://lmbang.u.qiniudn.com/video01-30-1.mp4");
        // 把视频输出到SurfaceView上
        mediaPlayer1.setDisplay(surface1.getHolder());
        mediaPlayer1.prepareAsync();
        mediaPlayer1.start();
    }

    private class SurfaceViewLis implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                play();
                mediaPlayer1.seekTo(postion);
                if (mediaPlayer1.isPlaying()) {
                    // 保存当前播放的位置
                    postion = mediaPlayer1.getCurrentPosition();
                    mediaPlayer1.pause();
                }
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mediaPlayer1.isPlaying()) {
                // 保存当前播放的位置
                postion = mediaPlayer1.getCurrentPosition();
                mediaPlayer1.stop();
            }
        }

    }

    @Override
    public void onPause() {
        if (mediaPlayer1.isPlaying()) {
            // 保存当前播放的位置
            postion = mediaPlayer1.getCurrentPosition();
            mediaPlayer1.stop();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer1.isPlaying())
            mediaPlayer1.stop();
        mediaPlayer1.release();
        super.onDestroy();
    }



}
