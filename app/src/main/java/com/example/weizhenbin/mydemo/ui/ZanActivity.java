package com.example.weizhenbin.mydemo.ui;

import android.view.View;
import android.widget.Button;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.example.weizhenbin.mydemo.widget.ZanBean;
import com.example.weizhenbin.mydemo.widget.ZanView;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 16/10/10.
 */

public class ZanActivity extends BaseActivity implements View.OnClickListener{

    private ZanView zv;
    private Button btZan;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_zan);
        zv= (ZanView) findViewById(R.id.zv_zv);
        btZan= (Button) findViewById(R.id.bt_zan);
        CommonToolbar.setTitle(this,"赞动效");
    }

    @Override
    protected void initData() {
      btZan.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onClick(View v) {
        if(v==btZan){
            zv.addZanXin(new ZanBean(this,R.drawable.zhibo_xin_l,zv));
        }
    }
}
