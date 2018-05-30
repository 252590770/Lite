package com.i.lite.update;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;


import com.i.lite.BuildConfig;
import com.i.lite.R;

import java.io.File;

import retrofit2.Retrofit;

import static com.i.lite.finals.Constant.Project_PATH;


public class DownLoadService extends Service {

    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    /**
     * 下载文件
     */
    private void loadFile() {

        Log.i("503","下载文件");

        initNotification();
        File file = new File(Project_PATH,"Lite.apk");
        new DownLoadAPKModel().downLoad(new FileDownLoadSubscriber(file) {
            @Override
            public void onSuccess(File file) {
                Toast.makeText(mContext, "下载成功", Toast.LENGTH_LONG).show();

                Log.i("503", "请求成功");
                // 安装软件
                cancelNotification();
                installApk(file);

            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();

                Log.i("503", "请求失败");
                cancelNotification();

            }



            @Override
            public void onProgress(long current, long total) {

                Log.i("503", current + "----" + total);
                updateNotification(current * 100 / total);

            }
//        },"http://f.hiphotos.baidu.com/image/pic/item/503d269759ee3d6db032f61b48166d224e4ade6e.jpg");
//        },"http://220.174.161.163:8088/apk/20180308.apk");
        },"apk/20180308.apk");




    }

    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        startActivity(intent);

    }



    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("0%")
                .setContentTitle("更新进度")
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {


            Log.i("503", "更新通知  progress ==" +  progress);

            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }
}
