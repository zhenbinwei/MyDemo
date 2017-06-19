package com.example.weizhenbin.mydemo.ui;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.presenter.ImageCheckControl;
import com.example.weizhenbin.mydemo.widget.imgcheck.ImageBrowse;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/6/19.
 */

public class ImageCheckActivity extends BaseActivity {
    ImageBrowse imageBrowse;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_image_check);
        imageBrowse= (ImageBrowse) getSupportFragmentManager().findFragmentById(R.id.fm_iamgebrowse);
    }

    @Override
    protected void initData() {
        imageBrowse.showImageBrowse(ImageCheckControl.getCheckControl(this).getImageInfos());
        imageBrowse.setPageCurrentPosition(ImageCheckControl.getCheckControl(this).getCurrentPosition());
    }

    @Override
    protected void initEvent() {

    }
}
