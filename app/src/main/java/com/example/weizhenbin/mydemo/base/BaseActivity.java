package com.example.weizhenbin.mydemo.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.weizhenbin.mydemo.common.ApplicationManage;
import com.weizhenbin.show.R;


/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: BaseActivity
 * @ProjectName Show
 * @Package com.weizhenbin.show.base
 * @date 2016/2/24 9:29
 * 基础的Activity 所有Activity都要继承BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {


    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationManage.getInstance().addActivity(this);
        start();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base_layout);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.base_layout);
        if(coordinatorLayout!=null){
            View.inflate(this,layoutResID,coordinatorLayout);
        }
    }

    private void start() {
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationManage.getInstance().removeActivity(this);
    }


  /*  @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("BaseActivity", "onSaveInstanceState1");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.d("BaseActivity", "onRestoreInstanceState1");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key","value");
        Log.d("BaseActivity", "onSaveInstanceState0"+outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("BaseActivity", "onRestoreInstanceState0"+savedInstanceState);
    }*/
}
