package com.i.lite.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.i.lite.R;
import com.i.lite.app.App;
import com.i.lite.entity.GetListParam;
import com.i.lite.entity.GetListResult;
import com.i.lite.entity.NewsEntity;
import com.i.lite.inter.OnGetListInfoListener;
import com.i.lite.inter.OnNewsListener;
import com.i.lite.model.modelimpl.InfoListModelImpl;
import com.i.lite.utils.MyProgressDialog;

import java.io.IOException;
import java.util.ArrayList;

//20180312  修改测试
public class MainActivity extends Activity implements OnNewsListener ,OnGetListInfoListener {

    TextView tv;
    int order_id=1;
    InfoListModelImpl model = new InfoListModelImpl();

    MyProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        progressDialog = new MyProgressDialog(this);
    }

    public void getNews(View view) {


//        progressDialog.showPop();
//        model.getInfoList(new GetListParam("getDrivig",order_id+""),this);
//
//        Log.i("ccccccc","model="+model.toString());
//        Log.i("ccccccc","order_id="+order_id);
//        order_id++;

        playMedia();


    }
    private static int songIndex = 0;
    private static ArrayList<String> songArrayList; //播放声音列表
    private static MediaPlayer player;
    public static final String baseUrl = "http://221.202.112.250:8088";//

    public static void playMedia(  ) {

        if(player==null){
            player = new MediaPlayer();
        }

        String url = "";
//        if(key.contains(".wav")){
//            url =  baseUrl+"travel/uploadVoice/"+key;
//            Log.i("cccccccc","uploadVoice ==1 "+url);
//        }else {
//            String[]  strs=key.split(".p");
//            url =  baseUrl+"travel/uploadVoice/"+strs[0]+".wav";
//            Log.i("cccccccc","uploadVoice ==2 "+url);
//        }

        url = baseUrl+"/travel/uploadVoice/"+"20180226112439223018700493859.wav";

        System.out.println(url);

        Uri uri = Uri.parse("url=="+url);
        try {
            player.setDataSource(App.getContext(),uri);
            System.out.println("play");
//        player = MediaPlayer.create(this, Uri.parse(url));
            player.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//设置重复播放
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //设置重复播放
//                player.start();
//                player.setLooping(true);
            }
        });

    }







    @Override
    public void onSuccess(NewsEntity newsEntity) {
        Log.i("cccccc",newsEntity.toString());
    }

    @Override
    public void onError(OnNewsListener listener) {
    }


    @Override
    public void onSuccess(ArrayList<GetListResult> listResults) {

        Log.i("cccccc",listResults.get(0).toString());
        tv.setText(listResults.get(0).getDriv_dest());

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.closePop();
                    }
                });
            }
        }.start();

    }

    @Override
    public void onError(Object error) {

        Throwable e = (Throwable)error;
        Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        progressDialog.closePop();
    }


    public void onNext(View view) {

        showGPSDialog(2,"提示","接单成功，导航至乘客位置");
//        startActivity(new Intent(this,MyActivity.class));

    }





    //提示对话框
    public Dialog dialog;
    public TextView btn_one;
    public TextView btn_right;
    public interface ClickSureListener {
        public void click();
    }
    private ClickSureListener clickSureListener;
    public void showTipDialog(int type, String titleStr, String contentStr, final String version_url) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(MainActivity.this).create();
        }

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = (View) inflater.inflate(R.layout.dialog_base, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        btn_one = (TextView) view.findViewById(R.id.btn_one);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.bottom_layout);
        btn_right = (TextView) view.findViewById(R.id.btn_right);
        if (type == 1) {
            btn_one.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        } else if (type == 2) {
            btn_one.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }
        btn_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // sure();
                clickSureListener.click();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // sure();
                clickSureListener.click();
            }
        });
        title.setText(titleStr);
        content.setText(contentStr);

        view.findViewById(R.id.btn_left).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        view.findViewById(R.id.btn_right).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //"http://ps.tupppai.com/wefun_v1.0.1.apk"
                        Uri uri = Uri.parse("http://" + version_url.trim());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        dialog.getWindow().setContentView(view);
    }

    public void showGPSDialog(int type, String titleStr, String contentStr) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(MainActivity.this).create();
        }

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = (View) inflater.inflate(R.layout.dialog_base, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        btn_one = (TextView) view.findViewById(R.id.btn_one);
        LinearLayout layout = (LinearLayout) view
                .findViewById(R.id.bottom_layout);
        btn_right = (TextView) view.findViewById(R.id.btn_right);
        if (type == 1) {
            btn_one.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        } else if (type == 2) {
            btn_one.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }
        btn_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // sure();
                clickSureListener.click();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // sure();
                clickSureListener.click();
            }
        });
        title.setText(titleStr);
        content.setText(contentStr);

        view.findViewById(R.id.btn_left).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        view.findViewById(R.id.btn_right).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //"开始导航

                    }
                });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        dialog.getWindow().setContentView(view);
    }
    //提示对话框




}
