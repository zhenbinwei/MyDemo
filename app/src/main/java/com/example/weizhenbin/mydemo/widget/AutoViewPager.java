package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by weizhenbin on 16/5/26.
 */
public class AutoViewPager extends ViewPager {
    private TimerThread timerThread;
    private MyHandler myHandler;
    public AutoViewPager(Context context) {
        super(context);
        init();
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        timerThread = new TimerThread();
        myHandler = new MyHandler();
    }


    public void start(){
        if(timerThread!=null){
            timerThread.isRun=true;
            timerThread.start();
        }
    }

    public void stop(){
        if(timerThread!=null){
            timerThread.isRun=false;
            timerThread=null;
        }
    }


    class TimerThread extends Thread {

        public boolean isRun = true;

        @Override
        public void run() {
            super.run();

            while (isRun) {
                try {
                    sleep(3000);
                    myHandler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if(getCurrentItem()<getAdapter().getCount()-1){
                        setCurrentItem(getCurrentItem()+1);
                    }else {
                        timerThread.isRun=false;
                    }

                    break;
            }
        }
    }

}
