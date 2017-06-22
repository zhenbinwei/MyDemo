package com.example.weizhenbin.mydemo.ui;

import android.view.View;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.presenter.MusicServiceControl;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/6/22.
 */

public class MusicActivity extends BaseActivity {

    @Override
    protected void initView() {
          setContentView(R.layout.activity_music);
          findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MusicServiceControl.start();
            }
          });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicServiceControl.pause();
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }


}
