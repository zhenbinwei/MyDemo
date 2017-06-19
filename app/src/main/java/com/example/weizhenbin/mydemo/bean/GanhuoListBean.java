package com.example.weizhenbin.mydemo.bean;

import com.example.weizhenbin.mydemo.presenter.ImageCheckControl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2017/6/14.
 */

public class GanhuoListBean {
    public boolean error;
    public List<ResultsBean> results;

    public static GanhuoListBean paseJsonData(JSONObject object) {
        if (object == null) {
            return null;
        }
        GanhuoListBean ganhuoListBean = new GanhuoListBean();
        ganhuoListBean.error = object.optBoolean("error");
        ganhuoListBean.results = new ArrayList<>();
        JSONArray jsonArray = object.optJSONArray("results");
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                ganhuoListBean.results.add(ResultsBean.paseJsonData(jsonArray.optJSONObject(i)));
            }
        }
        return ganhuoListBean;
    }


    public static class ResultsBean implements ImageCheckControl.ImageInfo {
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
        public List<String> images;

        public static ResultsBean paseJsonData(JSONObject object) {
            if (object == null) {
                return null;
            }
            ResultsBean resultsBean = new ResultsBean();
            resultsBean._id = object.optString("_id");
            resultsBean.createdAt = object.optString("createdAt");
            resultsBean.desc = object.optString("desc");
            resultsBean.publishedAt = object.optString("publishedAt");
            resultsBean.source = object.optString("source");
            resultsBean.type = object.optString("type");
            resultsBean.url = object.optString("url");
            resultsBean.who = object.optString("who");
            resultsBean.used = object.optBoolean("used");
            JSONArray jsonArray = object.optJSONArray("images");
            if (jsonArray != null) {
                resultsBean.images = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    resultsBean.images.add(jsonArray.optString(i));
                }
            }
            return resultsBean;
        }

        @Override
        public String getUrl() {
                return url;
        }

        @Override
        public boolean isImage() {
            if(type.equals("福利")){
                return true;
            }
            return false;
        }
    }


}
