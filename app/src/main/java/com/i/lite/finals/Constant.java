package com.i.lite.finals;

import android.os.Environment;

/**
 * Created by L on 2018/1/17.
 */

public class Constant {


    //项目主路径
    public static final String Project_PATH = Environment
            .getExternalStorageDirectory().getPath() + "/lite/";

    //头像路径
    public static final String USER_PATH = Project_PATH+ "user/";

    //异常日志路径
    public static final String LOG_PATH = Project_PATH+ "log/";

    //缓存路径
    public static final String CACHE_PATH = Project_PATH+ "cache/";

    //更新路径
    public static final String UPDATE_PATH =  Project_PATH+ "update/";



    public static final String IP = "220.174.161.163";//大庆
    public static final int PORT = 8088;
    public static final String baseUrl = "http://"+IP+":"+PORT+"/";








}
