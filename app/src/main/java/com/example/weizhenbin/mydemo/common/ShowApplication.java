package com.example.weizhenbin.mydemo.common;

import android.app.Application;

import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;

/**
 * Created by weizhenbin on 16/2/23.
 */
public class ShowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       // SDKInitializer.initialize(this);
      //  BaiduLocationManage.getInstance().startLocation();
        MusicServiceControl.init(this);
    }
}
