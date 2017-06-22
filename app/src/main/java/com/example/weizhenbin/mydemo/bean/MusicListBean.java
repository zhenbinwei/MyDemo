package com.example.weizhenbin.mydemo.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 17/6/22.
 */

public class MusicListBean {

    /**
     * showapi_res_code : 0
     * showapi_res_error : 
     * showapi_res_body : {"ret_code":0,"pagebean":{"songlist":[{"songname":"电台情歌 (《从你的全世界路过》电影电台版推广曲)","seconds":195,"albummid":"002pCFMW42h3jk","songid":108709929,"singerid":12530,"albumpic_big":"","albumpic_small":"","downUrl":"","url":"","singername":"邓超","albumid":1626434}]}}
     */

    public int showapi_res_code;
    public String showapi_res_error;
    public ShowapiResBodyBean showapi_res_body;

    public static MusicListBean paseJsonData(JSONObject jsonObject){
        if(jsonObject==null){
            return null;
        }
        MusicListBean musicListBean=new MusicListBean();
        musicListBean.showapi_res_code=jsonObject.optInt("showapi_res_code");
        musicListBean.showapi_res_error=jsonObject.optString("showapi_res_error");
        musicListBean.showapi_res_body=ShowapiResBodyBean.paseJsonData(jsonObject.optJSONObject("showapi_res_body"));
        return musicListBean;
    }

    public static class ShowapiResBodyBean {
        /**
         * ret_code : 0
         * pagebean : {"songlist":[{"songname":"电台情歌 (《从你的全世界路过》电影电台版推广曲)","seconds":195,"albummid":"002pCFMW42h3jk","songid":108709929,"singerid":12530,"albumpic_big":"","albumpic_small":"","downUrl":"","url":"","singername":"邓超","albumid":1626434}]}
         */

        public int ret_code;
        public PagebeanBean pagebean;

        public static ShowapiResBodyBean paseJsonData(JSONObject jsonObject){
            if(jsonObject==null){
                return null;
            }
            ShowapiResBodyBean showapiResBodyBean=new ShowapiResBodyBean();
            showapiResBodyBean.ret_code=jsonObject.optInt("ret_code");
            showapiResBodyBean.pagebean=PagebeanBean.paseJsonData(jsonObject.optJSONObject("pagebean"));
            return showapiResBodyBean;
        }

        public static class PagebeanBean {
            public List<SonglistBean> songlist;


            public static PagebeanBean paseJsonData(JSONObject jsonObject){
                if(jsonObject==null){
                    return null;
                }
                PagebeanBean pagebeanBean=new PagebeanBean();
                JSONArray jsonArray = jsonObject.optJSONArray("songlist");
                if(jsonArray!=null){
                    pagebeanBean.songlist=new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        pagebeanBean.songlist.add(SonglistBean.paseJsonData(jsonArray.optJSONObject(i)));

                    }
                }
                return pagebeanBean;
            }


            public static class SonglistBean {
                /**
                 * songname : 电台情歌 (《从你的全世界路过》电影电台版推广曲)
                 * seconds : 195
                 * albummid : 002pCFMW42h3jk
                 * songid : 108709929
                 * singerid : 12530
                 * albumpic_big : 
                 * albumpic_small : 
                 * downUrl : 
                 * url : 
                 * singername : 邓超
                 * albumid : 1626434
                 */

                public String songname;
                public int seconds;
                public String albummid;
                public int songid;
                public int singerid;
                public String albumpic_big;
                public String albumpic_small;
                public String downUrl;
                public String url;
                public String singername;
                public int albumid;

               public static SonglistBean paseJsonData(JSONObject jsonObject){

                   if(jsonObject==null){
                       return null;
                   }
                   SonglistBean songlistBean=new SonglistBean();
                   songlistBean.songname=jsonObject.optString("songname");
                   songlistBean.albummid=jsonObject.optString("albummid");
                   songlistBean.albumpic_big=jsonObject.optString("albumpic_big");
                   songlistBean.albumpic_small=jsonObject.optString("albumpic_small");
                   songlistBean.downUrl=jsonObject.optString("downUrl");
                   songlistBean.singername=jsonObject.optString("singername");
                   songlistBean.url=jsonObject.optString("url");
                   songlistBean.seconds=jsonObject.optInt("seconds");
                   songlistBean.songid=jsonObject.optInt("songid");
                   songlistBean.albumid=jsonObject.optInt("albumid");
                   songlistBean.singerid=jsonObject.optInt("singerid");
                   return songlistBean;

               }
            }
        }
    }
}
