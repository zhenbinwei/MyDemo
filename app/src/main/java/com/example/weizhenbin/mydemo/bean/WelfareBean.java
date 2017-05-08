package com.example.weizhenbin.mydemo.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2017/4/6.
 */
public class WelfareBean {

    /**
     * error : false
     * results : [{"_id":"58e5bd9c421aa90d6f211e3f","createdAt":"2017-04-06T12:01:32.576Z","desc":"4-6","publishedAt":"2017-04-06T12:06:10.17Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-06-17493825_1061197430652762_1457834104966873088_n.jpg","used":true,"who":"代码家"}]
     */

    public boolean error;
    public List<ResultsBean> results;

    public static WelfareBean paseJsonData(JSONObject object){
        if(object==null){
            return null;
        }
        WelfareBean welfareBean=new WelfareBean();
        welfareBean.error=object.optBoolean("error");
        welfareBean.results=new ArrayList<>();
        JSONArray jsonArray=object.optJSONArray("results");
        if(jsonArray!=null){
            for (int i = 0; i < jsonArray.length(); i++) {
                welfareBean.results.add(ResultsBean.paseJsonData(jsonArray.optJSONObject(i)));

            }
        }
        return welfareBean;
    }

    public static class ResultsBean {
        /**
         * _id : 58e5bd9c421aa90d6f211e3f
         * createdAt : 2017-04-06T12:01:32.576Z
         * desc : 4-6
         * publishedAt : 2017-04-06T12:06:10.17Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-06-17493825_1061197430652762_1457834104966873088_n.jpg
         * used : true
         * who : 代码家
         */

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;

        public static  ResultsBean paseJsonData(JSONObject object){
            if(object==null){
                return null;
            }
            ResultsBean resultsBean=new ResultsBean();
            resultsBean._id=object.optString("_id");
            resultsBean.createdAt=object.optString("createdAt");
            resultsBean.desc=object.optString("desc");
            resultsBean.publishedAt=object.optString("publishedAt");
            resultsBean.source=object.optString("source");
            resultsBean.type=object.optString("type");
            resultsBean.url=object.optString("url");
            resultsBean.who=object.optString("who");
            resultsBean.used=object.optBoolean("used");
            return resultsBean;
        }
    }
}
