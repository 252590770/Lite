package com.i.lite.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.i.lite.api.API;
import com.i.lite.config.CacheInterceptor;
import com.i.lite.finals.Constant;
import com.i.lite.utils.CrashHandler;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.CustomConverterFactory;

import static com.i.lite.finals.Constant.CACHE_PATH;

/**
 * Created by L on 2018/1/12.
 */

public class App extends Application {

    public static API api;
    private static Retrofit retrofit = null;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //全局错误日志打印
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        mkDirs();
    }

    //创建文件夹
    public void mkDirs(){

        File dir = new File(Constant.CACHE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }


    public static Context getContext() {
        return context;
    }


    public static API getAPI() {

        if (api == null) {
            api = App.getRetrofit().create(API.class);
        }

        Log.i("ccccccc", "api=" + api.toString());
        Log.i("ccccccc", "retrofit=" + retrofit.toString());


        return api;

    }

    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            initRetrofit();
        }
        return retrofit;
    }

    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static final int TIMEOUT_READ = 20;
    private static final int TIMEOUT_CONNECTION = 10;
    private static CacheInterceptor cacheInterceptor = new CacheInterceptor();


//    private static final long cacheSize = 1024 * 1024 * 20;// 缓存文件最大限制大小20M
    private static final long cacheSize = 1024 ;// 缓存文件最大限制大小20M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() +CACHE_PATH; // 设置缓存文件路径
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //

    /**
     * okhttp
     */
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            //打印日志
            .addInterceptor(getInterceptor())
            //设置Cache拦截器
            .addNetworkInterceptor(cacheInterceptor)
            .addInterceptor(cacheInterceptor)
            //缓存大小
            .cache(cache)
            //time out
            .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            //失败重连
            .retryOnConnectionFailure(true)
            .build();



    /**
     * 自定义日志拦截器
     *
     * @return
     */
    public static HttpLoggingInterceptor getInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i("cccccc", "--->" + message);
                    }
                });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
