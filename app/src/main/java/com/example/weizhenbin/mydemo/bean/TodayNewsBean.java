package com.example.weizhenbin.mydemo.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 17/6/21.
 */

public class TodayNewsBean {

    /**
     * status : 0
     * msg : ok
     * result : {"channel":"头条","num":"10","list":[{"title":"中国开闸放水27天解救越南旱灾","time":"2016-03-16 07:23","src":"中国网","category":"mil","pic":"","content":"","url":"","weburl":""}]}
     */

    public String status;
    public String msg;
    public ResultBean result;


    public static TodayNewsBean paseJsonData(JSONObject jsonObject){
        if(jsonObject==null){
            return null;
        }
        TodayNewsBean todayNewsBean=new TodayNewsBean();
        todayNewsBean.status=jsonObject.optString("status");
        todayNewsBean.msg=jsonObject.optString("msg");
        todayNewsBean.result=ResultBean.paseJsonData(jsonObject.optJSONObject("result"));
        return todayNewsBean;
    }

    public static class ResultBean {
        /**
         * channel : 头条
         * num : 10
         * list : [{"title":"中国开闸放水27天解救越南旱灾","time":"2016-03-16 07:23","src":"中国网","category":"mil","pic":"","content":"","url":"","weburl":""}]
         */

        public String channel;
        public String num;
        public List<ListBean> list;


        public static ResultBean paseJsonData(JSONObject jsonObject){
            if(jsonObject==null){
                return null;
            }
            ResultBean resultBean=new ResultBean();
            resultBean.channel=jsonObject.optString("channel");
            resultBean.num=jsonObject.optString("num");
            JSONArray jsonArray=jsonObject.optJSONArray("list");
            if(jsonArray!=null){
                resultBean.list=new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    resultBean.list.add(ListBean.paseJsonData(jsonArray.optJSONObject(i)));
                }
            }
            return resultBean;
        }


        public static class ListBean {
            /**
             * title : 中国开闸放水27天解救越南旱灾
             * time : 2016-03-16 07:23
             * src : 中国网
             * category : mil
             * pic : 
             * content : 
             * url : 
             * weburl : 
             */

            public String title;
            public String time;
            public String src;
            public String category;
            public String pic;
            public String content;
            public String url;
            public String weburl;

           public static ListBean paseJsonData(JSONObject jsonObject){
               if (jsonObject==null){
                   return null;
               }
               ListBean listBean=new ListBean();
               listBean.title=jsonObject.optString("title");
               listBean.time=jsonObject.optString("time");
               listBean.src=jsonObject.optString("src");
               listBean.category=jsonObject.optString("category");
               listBean.pic=jsonObject.optString("pic");
               listBean.content=jsonObject.optString("content");
               listBean.url=jsonObject.optString("url");
               listBean.weburl=jsonObject.optString("weburl");
               return listBean;
           }
        }
    }
}
