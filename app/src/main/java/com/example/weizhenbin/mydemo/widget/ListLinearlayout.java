package com.example.weizhenbin.mydemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weizhenbin.mydemo.util.DensityUtil;
import com.weizhenbin.show.R;


/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: ListLinelayout
 * @ProjectName Show
 * @Package com.weizhenbin.show.widget
 * @date 2016/2/29 9:58
 * <p/>
 * 列表 线性不布局 用于展示设置页面
 */
public class ListLinearlayout extends LinearLayout {

    private Context context;
    private DensityUtil densityUtil;
    public ListLinearlayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ListLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ListLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        //垂直布局
        this.setOrientation(LinearLayout.VERTICAL);
        densityUtil=new DensityUtil(context);
    }


    public void addItem(int id,String content,OnClickListener onClickListener) {
        LinearLayout layout = createParent(id,0,onClickListener);
        TextView textView = (TextView) createItem();
        textView.setText(content);
        layout.addView(textView);
        this.addView(layout);
    }

    public void addItem(int id,String content, int resouceId,OnClickListener onClickListener) {
        LinearLayout layout = createParent(id,0,onClickListener);
        ImageView imageView = (ImageView) createImage();
        TextView textView = (TextView) createItem();
        imageView.setImageResource(resouceId);
        textView.setText(content);
        layout.addView(imageView);
        layout.addView(textView);
        this.addView(layout);
    }

    public void addItem(int id,String content, int resouceId,int marginTop, boolean interval,OnClickListener onClickListener) {
        LinearLayout layout = createParent(id,marginTop,onClickListener);
        ImageView imageView = (ImageView) createImage();
        TextView textView = (TextView) createItem();
        imageView.setImageResource(resouceId);
        textView.setText(content);
        layout.addView(imageView);
        layout.addView(textView);
        this.addView(layout);
        if(interval){
            this.addView(createLine());
        }
    }

    public void addItem(int id,String content, int resouceId, int marginTop,OnClickListener onClickListener) {
        LinearLayout layout = createParent(id,marginTop,onClickListener);
        ImageView imageView = (ImageView) createImage();
        TextView textView = (TextView) createItem();
        imageView.setImageResource(resouceId);
        textView.setText(content);
        layout.addView(imageView);
        layout.addView(textView);
        this.addView(layout);
    }


    private View createItem() {
        TextView textView = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextColor(getResources().getColor(R.color.black));
        return textView;
    }

    private View createLine() {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        textView.setBackgroundColor(getResources().getColor(R.color.line_gray));
        return textView;
    }

    private View createImage() {
        ImageView imageView = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(densityUtil.dip2px(30), densityUtil.dip2px(30));
        layoutParams.setMargins(densityUtil.dip2px(8), densityUtil.dip2px(2), densityUtil.dip2px(8), densityUtil.dip2px(2));
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    private LinearLayout createParent(int id,int marginTop,OnClickListener onClickListener) {
        LinearLayout parent = new LinearLayout(context);
        LayoutParams layoutParams= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                densityUtil.dip2px(40));
        layoutParams.setMargins(0, densityUtil.dip2px(marginTop), 0, 0);
        parent.setLayoutParams(layoutParams);
        parent.setOrientation(HORIZONTAL);
        parent.setGravity(Gravity.CENTER_VERTICAL);
        parent.setBackgroundResource(R.drawable.selector_click);
        parent.setOnClickListener(onClickListener);
        return parent;
    }

}
