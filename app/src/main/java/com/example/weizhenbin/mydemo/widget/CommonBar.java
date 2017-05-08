package com.example.weizhenbin.mydemo.widget;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weizhenbin.show.R;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: CommonBar
 * @ProjectName Show
 * @Package com.weizhenbin.show.widget
 * @date 2016/2/29 14:02
 * @deprecated
 */
public class CommonBar {
    private static ImageView ivBack;
    private static TextView tvTitle;
    private static LinearLayout llMore;
    private static LinearLayout llBar;

    private static void init(Activity context) {
        ivBack = (ImageView) context.findViewById(R.id.iv_back);
        tvTitle = (TextView) context.findViewById(R.id.tv_title);
        llMore = (LinearLayout) context.findViewById(R.id.ll_more);
        llBar = (LinearLayout) context.findViewById(R.id.ll_bar);
    }

    private static void init(View view) {
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        llMore = (LinearLayout) view.findViewById(R.id.ll_more);
        llBar = (LinearLayout) view.findViewById(R.id.ll_bar);
    }

    public static void addTitle(Activity context,
                                boolean isShowBcak,
                                View.OnClickListener back,
                                String titleContent,
                                int barBgColorId,
                                View moreView) {
        init(context);
        if (ivBack != null) {
            if (isShowBcak) {
                ivBack.setVisibility(View.VISIBLE);
            } else {
                ivBack.setVisibility(View.GONE);
            }
            ivBack.setOnClickListener(back);
        }
        if (tvTitle != null) {
            tvTitle.setText(titleContent);
        }
        if (llBar != null) {
            if (barBgColorId != 0) {
                llBar.setBackgroundColor(barBgColorId);
            }
        }
        if (llMore != null&& moreView != null) {
            llMore.addView(moreView);
        }
    }

    public static void addTitle(View view,
                                boolean isShowBcak,
                                View.OnClickListener back,
                                String titleContent,
                                int barBgColorId,
                                View moreView) {
        init(view);
        if (ivBack != null) {
            if (isShowBcak) {
                ivBack.setVisibility(View.VISIBLE);
            } else {
                ivBack.setVisibility(View.GONE);
            }
            ivBack.setOnClickListener(back);
        }
        if (tvTitle != null) {
            tvTitle.setText(titleContent);
        }
        if (llBar != null) {
            if (barBgColorId != 0) {
                llBar.setBackgroundColor(barBgColorId);
            }
        }
        if (llMore != null && moreView != null) {
            llMore.addView(moreView);
        }
    }

    public static void addNobackTitle(Activity activity, String titleContent) {
        addTitle(activity, false, null, titleContent, 0, null);
    }

    public static void addNobackTitle(View view, String titleContent) {
        addTitle(view, false, null, titleContent, 0, null);
    }
}
