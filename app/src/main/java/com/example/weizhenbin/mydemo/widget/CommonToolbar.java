package com.example.weizhenbin.mydemo.widget;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.weizhenbin.mydemo.util.Tools;
import com.weizhenbin.show.R;

/**
 * Created by weizhenbin on 16/12/27.
 */

public class CommonToolbar {

    public static void setTitle(View view, String title){
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(title);
        }
    }
    public static void setTitle(Activity activity, String title){
        Toolbar toolbar= (Toolbar) activity.findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(title);
        }
    }

    public static Toolbar getToolBar(Activity activity){
        Toolbar toolbar= (Toolbar) activity.findViewById(R.id.toolbar);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(activity), 0, 0);
        }
        return toolbar;
    }
    public static Toolbar getToolBar(View view){
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            toolbar.setPadding(0, Tools.getStatusBarHeight(view.getContext()), 0, 0);
        }
        return  toolbar;
    }
}
