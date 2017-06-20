package com.example.weizhenbin.mydemo.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by weizhenbin on 16/9/24.
 */

public interface AppService {
    @POST("{url}")
    Call<String> postResponse(@Path("url") String url, @FieldMap Map<String, String> stringMap);


    @GET("{url}")
    Call<String> getResponse(@Path(value = "url",encoded = true) String url, @QueryMap Map<String, String> stringMap);
}
