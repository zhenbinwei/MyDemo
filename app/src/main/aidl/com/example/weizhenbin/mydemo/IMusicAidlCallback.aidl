// IMusicAidlCallback.aidl
package com.example.weizhenbin.mydemo;

// Declare any non-default types here with import statements

interface IMusicAidlCallback {
   void onCurrentPosition(int position);

   void onComplete();

   void onException(int type,String msg);
}
