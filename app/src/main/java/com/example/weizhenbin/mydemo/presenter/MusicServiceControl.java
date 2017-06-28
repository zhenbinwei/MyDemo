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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2017/6/22.
 */

public class MusicServiceControl {
    public static final int MODE_SINGE=1;
    public static final int MODE_LIST=2;


    private static MusicServiceControl serviceControl;
    private static IMusicAidlInterface aidlService;
    private static boolean isInit=false;
    private  MusicInfo musicInfo;//单曲
    private List<? extends MusicInfo> musicInfos;//播放列表

    private int previousIndex=0;

    private int mCurrentIndex=0;

    private List<OnMusicChangeListener> onMusicChangeListeners;
    private MusicServiceControl(){
        onMusicChangeListeners=new ArrayList<>();
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

    public void startList(List<? extends MusicInfo> list){
        if(list!=null&&!list.isEmpty()){
            this.musicInfos=list;
            previousIndex=mCurrentIndex;
            mCurrentIndex=0;
            start(musicInfos.get(mCurrentIndex));
        }
    }

    public int getPreviousIndex() {
        return previousIndex;
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    public void startList(List<? extends MusicInfo> list, int currentIndex){
        if(list!=null&&!list.isEmpty()){
            this.musicInfos=list;
            if(currentIndex<list.size()) {
                previousIndex=mCurrentIndex;
                mCurrentIndex=currentIndex;
                start(musicInfos.get(mCurrentIndex));
            }else {
                previousIndex=mCurrentIndex;
                mCurrentIndex=0;
                start(musicInfos.get(mCurrentIndex));
            }
        }
    }

    public void addListener(OnMusicChangeListener onMusicChangeListener){
        if(onMusicChangeListeners!=null){
            onMusicChangeListeners.add(onMusicChangeListener);
        }
    }
    public void removeListener(OnMusicChangeListener onMusicChangeListener){
        if(onMusicChangeListeners!=null){
            onMusicChangeListeners.remove(onMusicChangeListener);
        }
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
        notifyChange();
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

    public void next(){
        previousIndex=mCurrentIndex;
        mCurrentIndex++;
         if(musicInfos!=null&&mCurrentIndex<musicInfos.size()-1){
             musicInfo=musicInfos.get(mCurrentIndex);
             start(musicInfo);

         }
    }

    private void notifyChange(){
        if(onMusicChangeListeners!=null){
            for (OnMusicChangeListener changeListener:onMusicChangeListeners
                 ) {
                changeListener.onMusicChange();
            }
        }
    }

    public void previous(){
        previousIndex=mCurrentIndex;
        mCurrentIndex--;
        if(musicInfos!=null&&mCurrentIndex<musicInfos.size()&&mCurrentIndex>=0){
            musicInfo=musicInfos.get(mCurrentIndex);
            start(musicInfo);

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
    public  void reStart(){
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
                        serviceControl.next();
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
        int getSongid();
    }

    public interface OnMusicChangeListener{
        void onMusicChange();
    }
}
