package com.example.weizhenbin.mydemo.widget.imgcheck;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.weizhenbin.mydemo.presenter.ImageCheckControl;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/1/12.
 */
public class ImageItemView extends RelativeLayout {
    private TouchImageView touchImageView;
    private ProgressBar progressBar;
    private Context context;
    public ImageItemView(Context context) {
        this(context,null);
    }

    public ImageItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.page_item,this);
        touchImageView= (TouchImageView) findViewById(R.id.image_content);
        progressBar= (ProgressBar) findViewById(R.id.pb);

    }

    public  void setScaleType(ImageView.ScaleType scaleType){
        touchImageView.setScaleType(scaleType);
    }

    public void setImageBitmap(Bitmap imageBitmap){
        if(imageBitmap!=null){
            touchImageView.setImageBitmap(imageBitmap);
        }
    }

    public void setImageUrl(ImageCheckControl.ImageInfo imageUrl){
        if(imageUrl!=null){
            Glide.with(context).load(imageUrl.getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                   // touchImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    touchImageView.setImageBitmap(resource);
                }
            });
        }
    }

    public void setOnClick(OnClickListener onClickListener){
        if(touchImageView!=null) {
            touchImageView.setOnClickListener(onClickListener);
        }

    }

    public void setOnLongClick(OnLongClickListener onLongClickListener){
        if(touchImageView!=null) {
            touchImageView.setOnLongClickListener(onLongClickListener);
        }
    }

}
