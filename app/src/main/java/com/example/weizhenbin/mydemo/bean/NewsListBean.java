package com.example.weizhenbin.mydemo.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2017/6/21.
 */

public class NewsListBean {


    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"title":"巫山云雨枉断肠：女摄影师Erika Lust记录的性爱","date":"2016-06-13 10:31","author_name":"POCO摄影","thumbnail_pic_s":"aa","thumbnail_pic_s02":"bb","thumbnail_pic_s03":"cc","url":"http://mini.eastday.com/mobile/160613103108379.html?qid=juheshuju","uniquekey":"160613103108379","type":"头条","realtype":"娱乐"}]}
     */

    public String reason;
    public ResultBean result;

   public static NewsListBean paseJsonData(JSONObject jsonObject){
       if(jsonObject==null){
           return null;
       }
       NewsListBean newsListBean=new NewsListBean();
       newsListBean.reason=jsonObject.optString("reason");
       newsListBean.result=ResultBean.paseJsonData(jsonObject.optJSONObject("result"));
       return newsListBean;
   }


    public static class ResultBean {
        /**
         * stat : 1
         * data : [{"title":"巫山云雨枉断肠：女摄影师Erika Lust记录的性爱","date":"2016-06-13 10:31","author_name":"POCO摄影","thumbnail_pic_s":"aa","thumbnail_pic_s02":"bb","thumbnail_pic_s03":"cc","url":"http://mini.eastday.com/mobile/160613103108379.html?qid=juheshuju","uniquekey":"160613103108379","type":"头条","realtype":"娱乐"}]
         */

        public String stat;
        public List<DataBean> data;


        public static ResultBean paseJsonData(JSONObject jsonObject){
            if(jsonObject==null){
                return null;
            }
             ResultBean resultBean=new ResultBean();
            resultBean.stat=jsonObject.optString("stat");
            JSONArray jsonArray=jsonObject.optJSONArray("data");
            if(jsonArray!=null){
                resultBean.data=new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    resultBean.data.add(DataBean.paseJsonData(jsonArray.optJSONObject(i)));
                }
            }
            return resultBean;
        }

        public static class DataBean {
            /**
             * title : 巫山云雨枉断肠：女摄影师Erika Lust记录的性爱
             * date : 2016-06-13 10:31
             * author_name : POCO摄影
             * thumbnail_pic_s : 图片1
             * thumbnail_pic_s02 : 图片2
             * thumbnail_pic_s03 : 图片3
             * url : http://mini.eastday.com/mobile/160613103108379.html?qid=juheshuju
             * uniquekey : 160613103108379
             * type : 头条
             * realtype : 娱乐
             */

            public String title;
            public String date;
            public String author_name;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;
            public String url;
            public String uniquekey;
            public String type;
            public String realtype;

            public static DataBean paseJsonData(JSONObject jsonObject){
                if(jsonObject==null){
                    return null;
                }
                DataBean dataBean=new DataBean();
                dataBean.title=jsonObject.optString("title");
                dataBean.date=jsonObject.optString("date");
                dataBean.author_name=jsonObject.optString("author_name");
                dataBean.thumbnail_pic_s=jsonObject.optString("thumbnail_pic_s");
                dataBean.thumbnail_pic_s02=jsonObject.optString("thumbnail_pic_s02");
                dataBean.thumbnail_pic_s03=jsonObject.optString("thumbnail_pic_s03");
                dataBean.url=jsonObject.optString("url");
                dataBean.uniquekey=jsonObject.optString("uniquekey");
                dataBean.type=jsonObject.optString("type");
                dataBean.realtype=jsonObject.optString("realtype");
                return dataBean;
            }

        }
    }
}
