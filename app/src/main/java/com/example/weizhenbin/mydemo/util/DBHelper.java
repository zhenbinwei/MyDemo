package com.example.weizhenbin.mydemo.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.weizhenbin.mydemo.bean.UserInfo;


/**
 * Created by weizhenbin on 16/11/27.
 */

public class DBHelper {
    public static int DB_VERSION = 2;

    private SqliteHelper sqliteHelper;

    private static String DB_NAME = "weizhenbin.db";
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        sqliteHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION);
        db = sqliteHelper.getWritableDatabase();
    }

    public void add(UserInfo userInfo) {
        if (db != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserInfo.ID, userInfo.id);
            contentValues.put(UserInfo.NAME, userInfo.name);
            contentValues.put(UserInfo.AGE, userInfo.age);
            long id = db.insert(SqliteHelper.TB_NAME, null, contentValues);
            Log.d("DBHelper", "id:" + id);
        }
    }

    public void del(String id){
        if(db!=null){
         db.delete(SqliteHelper.TB_NAME,"id=?",new String[]{id});
        }
    }

    public void update(UserInfo newUserInfo){
        if(db!=null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(UserInfo.ID, newUserInfo.id);
            contentValues.put(UserInfo.NAME, newUserInfo.name);
            contentValues.put(UserInfo.AGE, newUserInfo.age);
            db.update(SqliteHelper.TB_NAME,contentValues,"id=?",new String[]{newUserInfo.id});
        }
    }

    public void query(String id){
        if(db!=null){
          Cursor cursor=db.query(SqliteHelper.TB_NAME,new String[]{UserInfo.ID,UserInfo.NAME,UserInfo.AGE,"test"},"id=?",new String[]{id},null,null,null);
            if(cursor!=null){
               while (cursor.moveToNext()){
                   UserInfo userInfo=new UserInfo();
                   userInfo.id=cursor.getString(cursor.getColumnIndex(UserInfo.ID));
                   userInfo.name=cursor.getString(cursor.getColumnIndex(UserInfo.NAME));
                   userInfo.age=cursor.getString(cursor.getColumnIndex(UserInfo.AGE));
                   userInfo.test=cursor.getString(cursor.getColumnIndex("test"));
                   Log.d("DBHelper", "userInfo:" + userInfo);
               }
                cursor.close();
            }
        }
    }
}
