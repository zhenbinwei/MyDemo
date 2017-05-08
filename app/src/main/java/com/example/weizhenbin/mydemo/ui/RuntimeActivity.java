package com.example.weizhenbin.mydemo.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.weizhenbin.show.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by weizhenbin on 16/10/18.
 */

public class RuntimeActivity extends BaseActivity {
    private Button btExec;
    private TextView tvContent;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_runtime);
        btExec= (Button) findViewById(R.id.bt_exec);
        tvContent= (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
       btExec.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               exec();
           }
       });
    }

    private void exec(){
        //这个类是一个很好用的工具，java中可以执行java命令，android中可以执行shell命令
        Runtime mRuntime = Runtime.getRuntime();
        try {
            //Process中封装了返回的结果和执行错误的结果
            Process mProcess = mRuntime.exec("su");
            BufferedReader mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));
            StringBuffer mRespBuff = new StringBuffer();
            char[] buff = new char[1024];
            int ch = 0;
            while((ch = mReader.read(buff)) != -1){
                mRespBuff.append(buff, 0, ch);
            }
            mReader.close();
            tvContent.setText(mRespBuff.toString());
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
