package com.example.weizhenbin.mydemo.retrofit;

import android.util.Log;

import com.example.weizhenbin.mydemo.config.AppUrl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by weizhenbin on 16/9/24.
 */

public class RetrofitUtil {

    private String baseUrl = AppUrl.GANk_HOST;
    private Retrofit retrofit;
    private AppService appService;
    private String url = "/";
    private Map<String, String> baseParames;
    private IRequsetCallBack iRequsetCallBack;
    public RetrofitUtil() {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        appService = retrofit.create(AppService.class);
        baseParames = new HashMap<>();
    }


    public RetrofitUtil setUrl(String url) {
        if (url != null && !url.equals("")) {
            if (url.contains(baseUrl)) {
                url = url.replace(baseUrl, "");
            }
            if(url.startsWith("/")){
                url=url.substring(1);
            }
            this.url = url;
        }
        return this;
    }

    public RetrofitUtil setiRequsetCallBack(IRequsetCallBack iRequsetCallBack) {
        this.iRequsetCallBack = iRequsetCallBack;
        return this;
    }

    public RetrofitUtil setParames(HashMap<String, String> parames) {
        if (parames != null) {
            baseParames.putAll(parames);
        }
        return this;
    }

    public void GET() {
        try {
            if(iRequsetCallBack!=null){
                iRequsetCallBack.requestStart();
            }
            Call<String> stringCall = appService.getResponse(url, baseParames);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(iRequsetCallBack!=null){
                        iRequsetCallBack.requestSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if(iRequsetCallBack!=null){
                        iRequsetCallBack.requestFail();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void POST() {
        try {
            Call<String> stringCall = appService.getResponse(url, baseParames);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.d("NewFragment", "" + response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("NewFragment", "" + t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
