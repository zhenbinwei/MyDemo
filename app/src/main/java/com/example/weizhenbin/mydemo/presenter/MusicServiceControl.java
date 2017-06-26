package com.example.weizhenbin.mydemo.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.weizhenbin.mydemo.IMusicAidlCallback;
import com.example.weizhenbin.mydemo.IMusicAidlInterface;

/**
 * Created by weizhenbin on 2017/6/22.
 */

public class MusicServiceControl {
    private static MusicServiceControl serviceControl;
    private static IMusicAidlInterface aidlService;
    private static boolean isInit=false;
    private  MusicInfo musicInfo;
    private MusicServiceControl(){
    }
    public static void init(Context context){
        if(context==null){
            throw new NullPointerException("context 不能为空");
        }
        if (serviceControl==null){
            serviceControl=new MusicServiceControl();
            Intent intent=new Intent(context,MusicService.class);
            context.bindService(intent,new MusicConnection(),Context.BIND_AUTO_CREATE);
        }
        isInit=true;
    }

    public static MusicServiceControl getServiceControl() {
        if(serviceControl==null){
            throw new NullPointerException("没初始化");
        }
        return serviceControl;
    }

    public <T extends MusicInfo> void start(T t){
        if(!isInit){
            throw new IllegalArgumentException("没初始化");
        }
        if(t ==null){
            throw new NullPointerException("信息不能为空");
        }
        musicInfo=t;
        try {
            aidlService.start(t.getDataPath());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public  MusicInfo getMusicInfo() {
        return musicInfo;
    }

    public  void pause(){
        if(!isInit){
            throw new IllegalArgumentException("没初始化");
        }
        try {
            aidlService.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int getStatus(){
        try {
           return aidlService.getStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void reStart(){
        if(!isInit){
            throw new IllegalArgumentException("没初始化");
        }
        try {
            aidlService.reStart();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
  private static class MusicConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlService = IMusicAidlInterface.Stub.asInterface(service);
            try {
                aidlService.setMusicCallback(new IMusicAidlCallback.Stub() {

                    @Override
                    public void onCurrentPosition(int position) throws RemoteException {

                    }

                    @Override
                    public void onComplete() throws RemoteException {
                        Log.d("MusicConnection", "播放完成");
                    }

                    @Override
                    public void onException(int type, String msg) throws RemoteException {

                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }


    public  interface MusicInfo{
        String getSongname();
        String getDataPath();
        String getSingername();
    }

}
