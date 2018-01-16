package com.i.lite.app;

import android.app.Application;
import android.util.Log;

import com.i.lite.api.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by L on 2018/1/12.
 */

public class App extends Application {

    public static API api;
    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
    }




    public static API getAPI() {

        if(api == null ){
            api = App.getRetrofit().create(API.class);
        }

        Log.i("ccccccc","api="+api.toString());
        Log.i("ccccccc","retrofit="+retrofit.toString());

        return api;

    }


    public static Retrofit getRetrofit() {

        if(retrofit == null ){
            initRetrofit();
        }
        return retrofit;
    }

    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://c.m.163.com/")
                .baseUrl("http://192.168.0.253:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
