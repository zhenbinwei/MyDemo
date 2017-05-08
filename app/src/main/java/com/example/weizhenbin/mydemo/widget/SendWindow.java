package com.example.weizhenbin.mydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 16/3/3.
 *
 * 发送消息的窗口
 *
 * 多种模式  发送文字 语音 图片（小号缩略图）
 */
public class SendWindow extends Dialog{

private SendType type=SendType.text;//默认文本

    private Context context;

    public SendWindow(Context context,SendType sendType) {
        this(context, R.style.customDialog,sendType);
    }

    public SendWindow(Context context, int themeResId,SendType sendType) {
        super(context, themeResId);
        this.type=sendType;
        this.context=context;

        setContentView(createLL());
    }


    public enum SendType{
        text,image,audio;
    }


    //父布局
    private LinearLayout createLL(){
        LinearLayout linearLayout=new ListLinearlayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
       //标题
        linearLayout.addView(createText("内容"));
        //内容录入区域
        linearLayout.addView(createEditText());
        //发送键
        linearLayout.addView(createButton());


        return linearLayout;
    }

    //标题
    private TextView createText(String title){
        TextView textView=new TextView(context);
        textView.setText(title);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,8,0,8);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    //发送按钮
    private Button createButton(){
        Button button=new Button(context);
        button.setText("发送");
        return button;
    }

    //类型子布局  text image audio
    private EditText createEditText(){
        EditText editText=new EditText(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,8,0,4);
        editText.setLayoutParams(params);
        editText.setHint("输入发送内容");
        return editText;
    }
    //image


    //audio

}
