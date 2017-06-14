package com.example.weizhenbin.mydemo.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weizhenbin.mydemo.base.BaseActivity;
import com.example.weizhenbin.mydemo.widget.MyNumPicker;
import com.example.weizhenbin.mydemo.widget.WheelListView;
import com.weizhenbin.show.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 16/10/24.
 */

public class DataSelectActivity extends BaseActivity {
    WheelListView wheelListView;
    List<String> data1;
    MyNumPicker myNumPicker;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_data_select);
        wheelListView= (WheelListView) findViewById(R.id.wlv);
        myNumPicker= (MyNumPicker) findViewById(R.id.np);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DataSelectActivity.this,"选中"+data1.get((wheelListView.selectPosition+1)%data1.size()),Toast.LENGTH_SHORT).show();
            }
        });
        myNumPicker.setMinValue(0);
        myNumPicker.setMaxValue(100);
    }

    @Override
    protected void initData() {
        data1=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data1.add("数据"+i);
        }
        wheelListView.setAdapter(new DataAdapter(this,data1));
        wheelListView.setSelection(1000);
    }

    @Override
    protected void initEvent() {

    }


    class DataAdapter extends BaseAdapter {

        private Context context;
        private List<String> datas;
        public DataAdapter(Context context, List<String> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas!=null?2000:0;
        }

        @Override
        public String getItem(int position) {
            return datas!=null?datas.get(position%datas.size()):null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView=new TextView(context);
                convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getResources().getDimensionPixelSize(R.dimen.select_item_height)));
                ((TextView) convertView).setGravity(Gravity.CENTER);
            }
            if(convertView instanceof TextView){
                ((TextView) convertView).setText(""+getItem(position));
            }
            return convertView;
        }
    }
}
