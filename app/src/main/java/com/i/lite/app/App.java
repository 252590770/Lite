package com.i.lite.app;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by L on 2018/1/12.
 */

public class App extends Application {

    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
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
