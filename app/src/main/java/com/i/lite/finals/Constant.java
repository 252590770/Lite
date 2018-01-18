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

    //更新路径
    public static final String UPDATE_PATH =  Project_PATH+ "update/";

}
