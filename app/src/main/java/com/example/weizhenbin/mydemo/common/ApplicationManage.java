package com.example.weizhenbin.mydemo.common;

import android.app.Activity;

import java.util.ArrayList;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: ActivityStack
 * @ProjectName Show
 * @Package com.weizhenbin.show.common
 * @date 2016/2/24 9:38
 * 单例 用来管理Activity
 */
public class ApplicationManage {

    private static ApplicationManage instance;

    private ArrayList<Activity> activities;

    private ApplicationManage() {
        activities = new ArrayList<>();
    }

    public static ApplicationManage getInstance() {
        if (instance == null) {
            instance = new ApplicationManage();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null && activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    /**
     * 退出程序
     */
    public void exitApp() {
        if (activities != null) {
            int size = activities.size();
            for (int i = 0; i < size; i++) {
                activities.get(i).finish();
            }
            activities.clear();
        }
    }

}
