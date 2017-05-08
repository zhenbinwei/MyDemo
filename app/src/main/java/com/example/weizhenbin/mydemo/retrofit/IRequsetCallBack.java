package com.example.weizhenbin.mydemo.retrofit;

/**
 * Created by weizhenbin on 16/9/24.
 */

public interface IRequsetCallBack {

    void requestStart();

    void requestFail();

    void requestSuccess(String s);

}
