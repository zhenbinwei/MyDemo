package com.example.weizhenbin.mydemo.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.weizhenbin.mydemo.ui.ImageCheckActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2017/6/19.
 */

public class ImageCheckControl {
    private List<ImageInfo> imageInfos=new ArrayList<>();

    private static ImageCheckControl checkControl;

    private Context context;

    private int currentPosition=0;
    private ImageCheckControl(Context context){
        this.context=context;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public static ImageCheckControl getCheckControl(Context context) {
        if(checkControl==null){
            checkControl=new ImageCheckControl(context);
        }
        return checkControl;
    }

    public void openImageCheck(List<? extends ImageInfo> imageInfos){
        List<ImageInfo> infos=new ArrayList<>();
        if(imageInfos!=null&&!imageInfos.isEmpty()){
            for (ImageInfo imageInfo:imageInfos
                 ) {
                if(imageInfo.isImage()){
                    infos.add(imageInfo);
                }
            }
        }
        this.imageInfos=infos;
        context.startActivity(new Intent(context, ImageCheckActivity.class));
    }
    public void openImageCheck(List<? extends ImageInfo> imageInfos,int position){
        List<ImageInfo> infos=new ArrayList<>();
        if(imageInfos!=null&&!imageInfos.isEmpty()){
        for (int i = 0; i < imageInfos.size(); i++) {
               ImageInfo imageInfo=imageInfos.get(i);
                if(imageInfo.isImage()){
                    if (position==i){
                        currentPosition=infos.size();
                    }
                    infos.add(imageInfo);
                }
        }}
        this.imageInfos=infos;
        context.startActivity(new Intent(context, ImageCheckActivity.class));
    }
    public List<ImageInfo> getImageInfos() {
        return imageInfos;
    }

    public  interface ImageInfo{
         String getUrl();
         boolean isImage();
    }
}
