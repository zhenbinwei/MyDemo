package com.example.weizhenbin.mydemo.presenter;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.weizhenbin.mydemo.IMusicAidlCallback;
import com.example.weizhenbin.mydemo.IMusicAidlInterface;

import java.io.IOException;

/**
 * Created by weizhenbin on 2017/6/22.
 */

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    IMusicAidlCallback mCallback;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }

   private class MusicBinder extends IMusicAidlInterface.Stub{
        @Override
        public void start(String dataPath) throws RemoteException {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(dataPath);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void pause() throws RemoteException {
            if(mediaPlayer!=null){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else {
                    mediaPlayer.start();
                }
            }
        }

        @Override
        public void setMusicCallback(IMusicAidlCallback callback) throws RemoteException {
            mCallback=callback;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {
                    mCallback.onComplete();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
