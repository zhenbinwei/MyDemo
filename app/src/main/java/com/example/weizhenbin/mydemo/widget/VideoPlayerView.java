package com.example.weizhenbin.mydemo.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.util.Tools;
import com.weizhenbin.show.R;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by weizhenbin on 2017/4/19.
 */
public class VideoPlayerView extends RelativeLayout implements View.OnClickListener, TextureView.SurfaceTextureListener {
    public static final int EVENT_PROGRESS = 1001;//监听进度
    public static final int EVENT_HIDE_CONTROL_ICON = 1002;//隐藏控制条


    private Activity activity;
    private Context context;
    private ImageView ivLayer;
    private RelativeLayout rlPlayContent;
    private TextureView textureView;
    private SeekBar sbProgress;
    private ImageButton ibPlayPause, ibFullScreen;
    private TextView tvMaxTime, tvCurrentTime;
    private LinearLayout llControlBar;
    private MediaPlayer mediaPlayer;
    private EventHandler eventHandler;
    private boolean isTrackingTouch = false;//拖动进度条
    private boolean isHideControlIcon = true;

    private boolean isTouchMove = false;

    private boolean isPrepared=false;


    public VideoPlayerView(Context context) {
        this(context, null);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.video_player, this);
        initView();
        initEvent();

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        this.context = context;
        this.setOnClickListener(this);
        LayoutParams layoutParams = (LayoutParams) rlPlayContent.getLayoutParams();
        layoutParams.height = Tools.getDeviceWidth(context)*3/4;
        layoutParams.width = Tools.getDeviceWidth(context);
        rlPlayContent.setLayoutParams(layoutParams);
        bindData("http://lmbang.u.qiniudn.com/video01-30-1.mp4");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(!hasWindowFocus){
            if(mediaPlayer.isPlaying()){
                ivLayer.setImageBitmap(textureView.getBitmap());
                ivLayer.setVisibility(VISIBLE);
                mediaPlayer.pause();
            }
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    private void initEvent() {
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser || isTouchMove) {
                    //用户手动拖动控制条
                    mediaPlayer.seekTo((int) ((float) progress / (float) seekBar.getMax() * mediaPlayer.getDuration()));
                }
                tvMaxTime.setText(formatTime(mediaPlayer.getDuration()));
                tvCurrentTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTrackingTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isTrackingTouch = false;
            }
        });
    }

    private void initView() {
        textureView = (TextureView) findViewById(R.id.textureview);
        sbProgress = (SeekBar) findViewById(R.id.sb_progress);
        ibFullScreen = (ImageButton) findViewById(R.id.ib_full_screen);
        ibPlayPause = (ImageButton) findViewById(R.id.ib_play_pause);
        tvMaxTime = (TextView) findViewById(R.id.tv_max_time);
        tvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        rlPlayContent = (RelativeLayout) findViewById(R.id.rl_play_content);
        ivLayer = (ImageView) findViewById(R.id.iv_layer);
        llControlBar = (LinearLayout) findViewById(R.id.ll_control_bar);
        eventHandler = new EventHandler();
        eventHandler.sendEmptyMessageDelayed(EVENT_PROGRESS, 1000);
        ibPlayPause.setOnClickListener(this);
        ibFullScreen.setOnClickListener(this);
        textureView.setSurfaceTextureListener(this);//设置监听函数  重写4个方法
    }

    public void bindData(String videoPath){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            //"http://lmbang.u.qiniudn.com/video01-30-1.mp4"
            mediaPlayer.setDataSource(videoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void preparedStart(){
        if(mediaPlayer!=null){
            mediaPlayer.setSurface(surface1);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    isPrepared=true;
                    mediaPlayer.start();
                }
            });
        }
    }


    private void pauseOrStart(){
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                    ivLayer.setImageBitmap(textureView.getBitmap());
                    ivLayer.setVisibility(VISIBLE);
                mediaPlayer.pause();
            }else {
                mediaPlayer.start();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivLayer.setVisibility(GONE);
                    }
                },200);

            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ibFullScreen) {
            if (activity != null) {
                Configuration cf = getResources().getConfiguration();
                if (cf.orientation == cf.ORIENTATION_LANDSCAPE) {
                    //当前横屏
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ibFullScreen.setImageResource(R.drawable.jc_enlarge);
                    LayoutParams layoutParams = (LayoutParams) rlPlayContent.getLayoutParams();
                    layoutParams.height = Tools.getDeviceWidth(context)*3/4;
                    layoutParams.width = Tools.getDeviceWidth(context);
                    rlPlayContent.setLayoutParams(layoutParams);
                } else if (cf.orientation == cf.ORIENTATION_PORTRAIT) {
                    //当前竖屏
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    LayoutParams layoutParams = (LayoutParams) rlPlayContent.getLayoutParams();
                    layoutParams.height = Tools.getDeviceHeight(context);
                    layoutParams.width = Tools.getDeviceWidth(context);
                    rlPlayContent.setLayoutParams(layoutParams);
                    ibFullScreen.setImageResource(R.drawable.jc_shrink);
                }
            }
        } else if (v == ibPlayPause) {
            if(isPrepared){
                pauseOrStart();
            }else {
                preparedStart();
            }
        }
    }

    private void controlIcon() {
        if (isHideControlIcon) {
            llControlBar.setVisibility(VISIBLE);
            ibPlayPause.setVisibility(VISIBLE);
            isHideControlIcon = false;
            eventHandler.removeMessages(EVENT_HIDE_CONTROL_ICON);
            eventHandler.sendEmptyMessageDelayed(EVENT_HIDE_CONTROL_ICON, 3000);
        } else {
            llControlBar.setVisibility(GONE);
            ibPlayPause.setVisibility(GONE);
            isHideControlIcon = true;
            eventHandler.removeMessages(EVENT_HIDE_CONTROL_ICON);
        }
    }
    Surface surface1;
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        surface1 = new Surface(surface);
        if(mediaPlayer!=null){
            mediaPlayer.setSurface(surface1);
        }

       /* surface1 = new Surface(surface);
        mediaPlayer.setSurface(surface1);
        if (!userPause) {
            rStart();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivLayer.setVisibility(GONE);
                }
            }, 400);
        }
        if(eventHandler==null){
            eventHandler=new EventHandler();
        }
        eventHandler.sendEmptyMessage(EVENT_PROGRESS);*/
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {


    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }



    private class EventHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case EVENT_PROGRESS:
                    if (mediaPlayer != null && mediaPlayer.isPlaying() && !isTrackingTouch) {
                        sbProgress.setProgress((int) ((float) mediaPlayer.getCurrentPosition() / (float) mediaPlayer.getDuration() * sbProgress.getMax()));
                    }
                    eventHandler.sendEmptyMessageDelayed(EVENT_PROGRESS, 1000);
                    break;
                case EVENT_HIDE_CONTROL_ICON:
                    controlIcon();
                    break;
            }
        }
    }


    private String formatTime(long mis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(mis);
    }

    public void backEvent() {

    }
}
