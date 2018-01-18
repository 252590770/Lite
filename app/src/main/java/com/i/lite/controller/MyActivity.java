package com.i.lite.controller;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.i.lite.R;
import com.i.lite.app.App;
import com.i.lite.utils.SPTool;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
public class MyActivity extends Activity  {

    private static Uri imageUriFromCamera;
    private static Uri resultUri;
    public static final int GET_IMAGE_BY_CAMERA = 5001;
    public static final int GET_IMAGE_FROM_PHONE = 5002;
    public static final int CROP_IMAGE = 5003;
    Context mContext;
    ImageView mIvAvatar;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mContext = this;
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        iv = (ImageView) findViewById(R.id.iv);

        checkPermission();
        roadImageView(Uri.parse(SPTool.getContent(mContext, "USER_PHOTO")), iv);

        //头像上传服务器
        String filePath = Uri.parse(SPTool.getContent(mContext, "USER_PHOTO")).getPath();
        UploadFileThread("url",filePath,new Handler());

    }

    public void openCamera(View view) {
        openCameraImage();
    }

    public void openLocalImage(View view) {
        openLocalImage();
    }


    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(data.getData());
                }

                break;
            case GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    initUCrop(imageUriFromCamera);
                }

                break;

            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    resultUri = UCrop.getOutput(data);
                    roadImageView(resultUri, mIvAvatar);
                    //保存图片路径到缓存
                    SPTool.putContent(mContext, "USER_PHOTO", resultUri.toString());
                    roadImageView(resultUri, iv);
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initUCrop(Uri uri) {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)//设置比例
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(this);
    }

    private void roadImageView(Uri uri, ImageView imageView) {
        Glide.with(mContext).
                load(uri).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                bitmapTransform(new CropCircleTransformation(mContext)).//圆形设置
                thumbnail(0.5f).
                placeholder(R.drawable.circle_elves_ball).
                priority(Priority.LOW).
                error(R.drawable.circle_elves_ball).
                fallback(R.drawable.circle_elves_ball).
                into(imageView);
    }

    public void checkPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            List<String> permissionStrs = new ArrayList<>();
            int hasWriteSdcardPermission =
                    ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(hasWriteSdcardPermission !=
                    PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                );
            }

            int hasCameraPermission = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA);
            if(hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissionStrs.add(Manifest.permission.CAMERA);
            }
            String[] stringArray = permissionStrs.toArray(new String[0]);
            if (permissionStrs.size() > 0) {
                requestPermissions(stringArray,
                        000);
                return;
            }
        }


    }

    public  void openCameraImage() {
        imageUriFromCamera = createImagePathUri(this);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", imageUriFromCamera);

        startActivityForResult(intent, 5001);

    }

    public  void openLocalImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(intent, 5002);
    }

    public static Uri createImagePathUri(Context context) {
        Uri[] imageFilePath = new Uri[]{null};
        if(ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions((Activity)context, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
            imageFilePath[0] = Uri.parse("");
            Toast.makeText(App.getContext(),"请先获取写入SDCard权限",Toast.LENGTH_SHORT).show();
        } else {
            String status = Environment.getExternalStorageState();
            SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
            long time = System.currentTimeMillis();
            String imageName = timeFormatter.format(new Date(time));
            ContentValues values = new ContentValues(3);
            values.put("_display_name", imageName);
            values.put("datetaken", Long.valueOf(time));
            values.put("mime_type", "image/jpeg");
            if(status.equals("mounted")) {
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                imageFilePath[0] = context.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
            }
        }

        Log.i("", "生成的照片输出路径：" + imageFilePath[0].toString());
        return imageFilePath[0];
    }//1023403858


    public void UploadFileThread(String url, String filePath, Handler handler) {


    }



    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
