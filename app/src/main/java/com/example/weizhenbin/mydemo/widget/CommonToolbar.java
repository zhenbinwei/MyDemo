package com.example.weizhenbin.mydemo.widget;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        return (Toolbar) activity.findViewById(R.id.toolbar);
    }
    public static Toolbar getToolBar(View view){
        return  (Toolbar) view.findViewById(R.id.toolbar);
    }
}
