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

    }

    public static void start(){
        try {
            aidlService.start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void pause(){
        try {
            aidlService.pause();
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



}
