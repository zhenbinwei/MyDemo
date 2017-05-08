package com.example.weizhenbin.mydemo.base;

import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: BaseBaiduMapFragment
 * @ProjectName Show
 * @Package com.weizhenbin.show.base
 * @date 2016/2/25 10:11
 */
public abstract class BaseBaiduMapFragment extends BaseFragment {
    protected TextureMapView textureMapView;
    protected BaiduMap baiduMap;
    protected Button zoomIn;
    protected Button zoomOut;

    @Override
    public void onPause() {
        super.onPause();
        if (textureMapView != null) {
            textureMapView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (textureMapView != null) {
            textureMapView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textureMapView != null) {
            textureMapView.onDestroy();
        }
    }


}
