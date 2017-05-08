package com.example.weizhenbin.mydemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.mydemo.base.BaseFragment;
import com.example.weizhenbin.mydemo.external.weibo.WBActionManage;
import com.example.weizhenbin.mydemo.external.weibo.WBShareResponseListener;
import com.example.weizhenbin.mydemo.retrofit.RetrofitUtil;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.example.weizhenbin.mydemo.widget.ListLinearlayout;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.weizhenbin.show.R;


/**
 * Created by weizhenbin on 16/12/13.
 */

public class DemosFragment extends BaseFragment {
    private ListLinearlayout linearlayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demos,null);
    }

    @Override
    protected void initView(View view) {
        linearlayout= (ListLinearlayout) view.findViewById(R.id.ll_infolist);
        CommonToolbar.setTitle(view,"Demos");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        linearlayout.addItem(0, "点赞效果", R.mipmap.ic_launcher,1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MeFragment", "1");
                startActivity(new Intent(getActivity(),ZanActivity.class));
            }
        });
        linearlayout.addItem(1,"边缘渐变",R.mipmap.ic_launcher,1,true,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MeFragment", "2");
            }
        });
        linearlayout.addItem(2,"回弹控件",R.mipmap.ic_launcher,20,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MeFragment", "3");
            }
        });
        linearlayout.addItem(3,"自动滚ViewPage",R.mipmap.ic_launcher,1,true,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MeFragment", "4");
            }
        });
        linearlayout.addItem(4,"嵌套滑动",R.mipmap.ic_launcher,1,true,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MeFragment", "5");
                startActivity(new Intent(getActivity(),NestScrollActivity.class));
            }
        });
        linearlayout.addItem(5, "视频播放器", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),VideoPlayActivity.class));
            }
        });
        linearlayout.addItem(5, "执行命令", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),RuntimeActivity.class));
            }
        });
        linearlayout.addItem(6, "数据选择器", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),DataSelectActivity.class));
            }
        });
        linearlayout.addItem(7, "SQLite", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SQLActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_open,R.anim.activity_stay);
            }
        });
        linearlayout.addItem(8, "视频", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),VideoActivity.class));
            }
        });
        linearlayout.addItem(9, "gankApi", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RetrofitUtil().setUrl("/data/Android/10/1").GET();
            }
        });
        linearlayout.addItem(10, "recyclerview", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),RecyclerViewActivity.class));
            }
        });
        linearlayout.addItem(10, "GlideDemo", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),GlideDemoActivity.class));
            }
        });
        linearlayout.addItem(11, "Web", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),WebActivity.class));
            }
        });
        linearlayout.addItem(11, "RxJava", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),RxJavaTestActivity.class));
            }
        });
        linearlayout.addItem(11, "微博认证", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WBActionManage.getInstance(getActivity()).weiBoAuth().addWeiboAuthListener(new WbAuthListener() {

                    @Override
                    public void onSuccess(Oauth2AccessToken oauth2AccessToken) {

                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

                    }
                });
            }
        });
        linearlayout.addItem(11, "微博分享", R.mipmap.ic_launcher, 1, true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              WBActionManage.getInstance(getActivity()).weiBoShare("","").addmWbShareResponseListener(new WBShareResponseListener() {
                  @Override
                  public void shareSuccess() {
                      Log.d("DemosFragment", "分享成功");
                  }

                  @Override
                  public void shareCancel() {
                      Log.d("DemosFragment", "分享取消");
                  }

                  @Override
                  public void shareFail() {
                      Log.d("DemosFragment", "分享失败");
                  }
              });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
