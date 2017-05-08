package com.example.weizhenbin.mydemo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.weizhenbin.mydemo.bean.UserInfo;


/**
 * Created by weizhenbin on 16/11/27.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public static String TB_NAME="user";

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                TB_NAME+ "("+
                UserInfo. ID+ " integer primary key,"+
                UserInfo. NAME+ " varchar,"+
                UserInfo. AGE+ " varchar"+
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("SqliteHelper", "数据库升级");
        db.execSQL("ALTER TABLE "+TB_NAME+" ADD COLUMN test varchar");
    }
}
