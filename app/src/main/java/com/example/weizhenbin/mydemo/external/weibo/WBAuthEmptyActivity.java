package com.example.weizhenbin.mydemo.external.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by weizhenbin on 2017/4/24.
 * 空的actvivty
 */
public class WBAuthEmptyActivity extends Activity {

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent()!=null) {
            if ("auth".equals(getIntent().getStringExtra("action"))){
                mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
                mSsoHandler = new SsoHandler(WBAuthEmptyActivity.this);
                mSsoHandler.authorize(new AuthListener());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public static void WeiboAuth(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,WBAuthEmptyActivity.class);
        intent.putExtra("action","auth");
        context.startActivity(intent);
    }


    class AuthListener implements WbAuthListener {

        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            if(WBActionManage.getInstance(WBAuthEmptyActivity.this).getWeiboAuthListener()!=null){
                WBActionManage.getInstance(WBAuthEmptyActivity.this).getWeiboAuthListener().onSuccess(oauth2AccessToken);
            }
            finish();
        }

        @Override
        public void cancel() {
            if(WBActionManage.getInstance(WBAuthEmptyActivity.this).getWeiboAuthListener()!=null){
                WBActionManage.getInstance(WBAuthEmptyActivity.this).getWeiboAuthListener().cancel();
            }
            finish();
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            if(WBActionManage.getInstance(WBAuthEmptyActivity.this).getWeiboAuthListener()!=null){
                WBActionManage.getInstance(WBAuthEmptyActivity.this).getWeiboAuthListener().onFailure(wbConnectErrorMessage);
            }
            finish();
        }
    }

}
