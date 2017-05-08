package com.example.weizhenbin.mydemo.external.weibo;

import android.content.Context;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WbAuthListener;

/**
 * Created by weizhenbin on 2017/4/24.
 */
public class WBActionManage {

    private static WBActionManage wbActionManage;

    private Context context;

    private WbAuthListener mWeiboAuthListener;
    private WBShareResponseListener mWbShareResponseListener;
    private WBActionManage(Context context){
       WbSdk.install(context,new AuthInfo(context,Constants.APP_KEY,Constants.REDIRECT_URL,Constants.SCOPE));
       this.context=context;
    }

    public static WBActionManage getInstance(Context context){
        if(wbActionManage==null){
            wbActionManage=new WBActionManage(context);
        }
        return wbActionManage;
    }

    public WbAuthListener getWeiboAuthListener() {
        return mWeiboAuthListener;
    }

    public WBShareResponseListener getmWbShareResponseListener() {
        return mWbShareResponseListener;
    }

    /**认证后登陆*/
    public AuthAction weiBoAuth(){
        WBAuthEmptyActivity.WeiboAuth(context);
        return new AuthAction();
    }

    /**分享 文字*/
    public ShareAction weiBoShare(String text){
        WBShareEmptyActivity.shareText(context,text);
        return new ShareAction();
    }
    /**分享 文字*/
    public ShareAction weiBoShare(String text, int imgRes){
        WBShareEmptyActivity.shareTextAndImg(context,text,imgRes);
        return new ShareAction();
    }
    public ShareAction weiBoShare(String text, String imgPath){
        WBShareEmptyActivity.shareTextAndImg(context,text,imgPath);
        return new ShareAction();
    }
    public class AuthAction{
       public void addWeiboAuthListener(WbAuthListener weiboAuthListener){
            mWeiboAuthListener=weiboAuthListener;
        }
    }
    public class ShareAction{
        public void addmWbShareResponseListener(WBShareResponseListener wbShareResponseListener) {
            mWbShareResponseListener = wbShareResponseListener;
        }
    }

}
