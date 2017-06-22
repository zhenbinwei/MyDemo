// IMusicAidlInterface.aidl
package com.example.weizhenbin.mydemo;

// Declare any non-default types here with import statements
import com.example.weizhenbin.mydemo.IMusicAidlCallback;

interface IMusicAidlInterface {

           void start(String dataPath);

           void pause();

           void setMusicCallback(IMusicAidlCallback callback);
}
