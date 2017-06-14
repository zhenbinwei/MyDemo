package com.example.weizhenbin.mydemo.ui;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.weizhenbin.show.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by weizhenbin on 2017/4/17.
 */
public class RxJavaTestActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_rxjava);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
      findViewById(R.id.bt_test1).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              /**已有的数据*/
             /* String[] strs=new String[]{"1","2","3"};
              Observable.from(strs).subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      Log.d("RxJavaTestActivity", s);
                  }
              });
              Observable.just("String").subscribe(new Action1<String>() {
                  @Override
                  public void call(String s) {
                      Log.d("RxJavaTestActivity", s);
                  }
              });*/
              /**新创建的数据*/
              Observable.create(new Observable.OnSubscribe<Object>() {
                  @Override
                  public void call(Subscriber<? super Object> subscriber) {
                      if(!subscriber.isUnsubscribed()) {
                          subscriber.onNext("String");
                      }
                      subscriber.onCompleted();
              }
              }).subscribe(new Subscriber<Object>() {
                  @Override
                  public void onCompleted() {

                      Log.d("RxJavaTestActivity", "完成");
                  }

                  @Override
                  public void onError(Throwable e) {

                  }

                  @Override
                  public void onNext(Object o) {
                      Log.d("RxJavaTestActivity", "o:" + o);
                  }
              });
              Observable.create(new Observable.OnSubscribe<Object>() {
                  @Override
                  public void call(Subscriber<? super Object> subscriber) {
                      if(!subscriber.isUnsubscribed()) {
                          subscriber.onNext("String");
                      }
                  }
              }).subscribe(new Action1<Object>() {
                  @Override
                  public void call(Object o) {
                      Log.d("RxJavaTestActivity", "o:" + o);
                  }
              });
          }
      });
        findViewById(R.id.bt_test2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
                layoutParams.width = 200;
                layoutParams.height =200;
                layoutParams.format = PixelFormat.RGBA_8888;
                getWindow().setAttributes(layoutParams);*/

                WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
                layoutParams.width=200;
                layoutParams.height=200;
                layoutParams.dimAmount = 0.0f;
                layoutParams.x=0;
                layoutParams.y=0;
                getWindow().setAttributes(layoutParams);
            }
        });
    }
}
