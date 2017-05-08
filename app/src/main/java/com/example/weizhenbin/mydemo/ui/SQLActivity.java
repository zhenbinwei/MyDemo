package com.example.weizhenbin.mydemo.ui;

import android.view.View;
import android.widget.Button;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.bean.UserInfo;
import com.example.weizhenbin.mydemo.util.DBHelper;
import com.example.weizhenbin.mydemo.widget.CommonToolbar;
import com.weizhenbin.show.R;


/**
 * Created by weizhenbin on 16/11/14.
 */

public class SQLActivity extends BaseActivity implements View.OnClickListener {

    private Button btCreatSql;
    private Button btDelSql;
    private Button btAdd;
    private Button btDel;
    private Button btUpdate;
    private Button btQuery;
    private Button btAddField;
    private Button btUpgrade;

    private void assignViews() {
        btCreatSql = (Button) findViewById(R.id.bt_creat_sql);
        btDelSql = (Button) findViewById(R.id.bt_del_sql);
        btAdd = (Button) findViewById(R.id.bt_add);
        btDel = (Button) findViewById(R.id.bt_del);
        btUpdate = (Button) findViewById(R.id.bt_update);
        btQuery = (Button) findViewById(R.id.bt_query);
        btAddField = (Button) findViewById(R.id.bt_add_field);
        btUpgrade = (Button) findViewById(R.id.bt_upgrade);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_sql);
        CommonToolbar.setTitle(this,"数据库操作");
        assignViews();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        btAdd.setOnClickListener(this);
        btCreatSql.setOnClickListener(this);
        btUpgrade.setOnClickListener(this);
        btQuery.setOnClickListener(this);
        btDel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btAddField.setOnClickListener(this);
    }

    DBHelper dbHelper;

    @Override
    public void onClick(View v) {
        if (v == btCreatSql) {
            dbHelper = new DBHelper(SQLActivity.this);
        } else if (v == btAdd) {
            UserInfo userInfo = new UserInfo();
            userInfo.id = "0";
            userInfo.name = "weizhenbin";
            userInfo.age = "26";
            dbHelper.add(userInfo);
        } else if (v == btUpgrade) {
            DBHelper.DB_VERSION = 3;
            dbHelper = new DBHelper(SQLActivity.this);
        } else if (v == btUpdate) {
            UserInfo userInfo=new UserInfo();
            userInfo.id="0";
            userInfo.name="jiangyanan";
            userInfo.age="22";
           dbHelper.update(userInfo);
        } else if (v == btQuery) {
            dbHelper.query("0");
        } else if (v == btDel) {
            dbHelper.del("0");
        }else if(v==btAddField){
            UserInfo userInfo = new UserInfo();
            userInfo.id = "0";
            userInfo.name = "weizhenbin";
            userInfo.age = "26";
            userInfo.test="test";
            dbHelper.add(userInfo);
        }
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.activity_stay,R.anim.activity_close);
    }
}
