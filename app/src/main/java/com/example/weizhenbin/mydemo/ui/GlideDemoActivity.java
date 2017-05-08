package com.example.weizhenbin.mydemo.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.weizhenbin.show.R;


/**
 * Created by weizhenbin on 17/1/19.
 */

public class GlideDemoActivity extends BaseActivity {
    ImageView ivGlide;
    Button btLoad;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_glidedemo);
        ivGlide= (ImageView) findViewById(R.id.iv_glide);
        btLoad= (Button) findViewById(R.id.bt_load);
        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(GlideDemoActivity.this).load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                        .into(ivGlide);
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
