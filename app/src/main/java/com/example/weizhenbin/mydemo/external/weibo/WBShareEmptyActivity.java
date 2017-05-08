package com.example.weizhenbin.mydemo.external.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 2017/4/24.
 */
public class WBShareEmptyActivity extends Activity implements WbShareCallback {

    /**
     * 微博微博分享接口实例
     */

    private int shareType = 1;//2 3图文 1文字

    private String shareText;

    private WbShareHandler wbShareHandler;

    private int shareImgRes;
    private String shareImgPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            if ("share".equals(intent.getStringExtra("action"))) {
                wbShareHandler = new WbShareHandler(this);
                wbShareHandler.registerApp();
                shareType = intent.getIntExtra("shareType", 1);
                shareText = intent.getStringExtra("shareText");
                shareImgPath = intent.getStringExtra("shareImgPath");
                shareImgRes = intent.getIntExtra("shareImgRes", 0);
                switch (shareType) {
                    case 1:
                            shareText(shareText);
                        break;
                    case 2:
                            shareImgAndText(shareText, shareImgPath);
                        break;
                    case 3:
                            shareImgAndText(shareText, shareImgRes);
                        break;
                }

            }
        }

    }

    public static void shareText(Context context, String text) {
        Intent intent = new Intent(context, WBShareEmptyActivity.class);
        intent.putExtra("action", "share");
        intent.putExtra("shareType", 1);
        intent.putExtra("shareText", text);
        context.startActivity(intent);
    }

    public static void shareTextAndImg(Context context, String text, String imgPath) {
        Intent intent = new Intent(context, WBShareEmptyActivity.class);
        intent.putExtra("action", "share");
        intent.putExtra("shareType", 2);
        intent.putExtra("shareText", text);
        intent.putExtra("shareImgPath", imgPath);
        context.startActivity(intent);
    }

    public static void shareTextAndImg(Context context, String text, int imageRes) {
        Intent intent = new Intent(context, WBShareEmptyActivity.class);
        intent.putExtra("action", "share");
        intent.putExtra("shareType", 3);
        intent.putExtra("shareText", text);
        intent.putExtra("shareImgRes", imageRes);
        context.startActivity(intent);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        if (wbShareHandler != null) {
            wbShareHandler.doResultIntent(intent, this);
        }
    }

    private void shareText(String shareText) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = shareText;
        weiboMessage.textObject = textObject;
        if (wbShareHandler != null) {
            wbShareHandler.shareMessage(weiboMessage, false);
        }
    }



    private void shareImgAndText(final String shareText, String imgPath) {
       /* ImageLoader.getInstance().loadImage(imgPath,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                ImageObject imageObject = new ImageObject();
                imageObject.setImageObject(loadedImage);
                weiboMessage.imageObject = imageObject;
                TextObject textObject = new TextObject();
                textObject.text = shareText;
                weiboMessage.textObject = textObject;
                if (wbShareHandler != null) {
                    wbShareHandler.shareMessage(weiboMessage, false);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                ImageObject imageObject = new ImageObject();
                imageObject.setImageObject(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                weiboMessage.imageObject = imageObject;
                TextObject textObject = new TextObject();
                textObject.text = shareText;
                weiboMessage.textObject = textObject;
                if (wbShareHandler != null) {
                    wbShareHandler.shareMessage(weiboMessage, false);
                }
            }
        });*/
    }

    private void shareImgAndText(String shareText, int imgRes) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = shareText;
        weiboMessage.textObject = textObject;
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap=null;
        if(imgRes<=0){
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }else {
            bitmap = BitmapFactory.decodeResource(getResources(), imgRes);
        }
        imageObject.setImageObject(bitmap);
        weiboMessage.imageObject=imageObject;
        if (wbShareHandler != null) {
            wbShareHandler.shareMessage(weiboMessage, false);
        }
    }


    @Override
    public void onWbShareSuccess() {
        if (WBActionManage.getInstance(WBShareEmptyActivity.this).getmWbShareResponseListener() != null) {
            WBActionManage.getInstance(WBShareEmptyActivity.this).getmWbShareResponseListener().shareSuccess();
        }
        finish();
    }

    @Override
    public void onWbShareCancel() {
        if (WBActionManage.getInstance(WBShareEmptyActivity.this).getmWbShareResponseListener() != null) {
            WBActionManage.getInstance(WBShareEmptyActivity.this).getmWbShareResponseListener().shareCancel();
        }
        finish();
    }

    @Override
    public void onWbShareFail() {
        if (WBActionManage.getInstance(WBShareEmptyActivity.this).getmWbShareResponseListener() != null) {
            WBActionManage.getInstance(WBShareEmptyActivity.this).getmWbShareResponseListener().shareFail();
        }
        finish();
    }
}
