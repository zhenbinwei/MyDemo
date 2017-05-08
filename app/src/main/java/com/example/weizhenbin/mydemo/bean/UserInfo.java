package com.example.weizhenbin.mydemo.bean;

import java.io.Serializable;

/**
 * Created by weizhenbin on 16/11/27.
 */

public class UserInfo implements Serializable {
    public static String ID="id";
    public static String NAME="name";
    public static String AGE="age";

    public String id;
    public String name;
    public String age;
    public String test;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
