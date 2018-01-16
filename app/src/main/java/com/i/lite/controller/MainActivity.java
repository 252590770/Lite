package com.i.lite.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.i.lite.R;
import com.i.lite.entity.GetListParam;
import com.i.lite.entity.GetListResult;
import com.i.lite.entity.NewsEntity;
import com.i.lite.inter.OnGetListInfoListener;
import com.i.lite.inter.OnNewsListener;
import com.i.lite.model.modelimpl.InfoListModelImpl;
import com.i.lite.utils.MyProgressDialog;

import java.util.ArrayList;

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


        progressDialog.showPop();
        model.getInfoList(new GetListParam("getDrivig",order_id+""),this);

        Log.i("ccccccc","model="+model.toString());
        Log.i("ccccccc","order_id="+order_id);
        order_id++;
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


}
