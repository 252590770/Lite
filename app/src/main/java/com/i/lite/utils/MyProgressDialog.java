package com.i.lite.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.i.lite.R;
import com.i.lite.controller.MainActivity;

/**
 * Created by L on 2018/1/15.
 */

public class MyProgressDialog<T> {


    public static  MyProgressDialog mDialog;

    private Context context;

    public MyProgressDialog(Context context) {
        this.context = context;
    }

    private PopupWindow loadingWindow;
    private ImageView mPoint;


    public void closePop() {

        mPoint.clearAnimation();
        loadingWindow.dismiss();
    }

    public void showPop() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.ss, null);
        loadingWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        loadingWindow.setContentView(contentView);
        View rootview = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        loadingWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        loadingWindow.setOutsideTouchable(false);

        mPoint = (ImageView) contentView.findViewById(R.id.point);
//        RotateAnimation ra = new RotateAnimation(0,360, Animation.RELATIVE_TO_PARENT,0.37f,Animation.RELATIVE_TO_PARENT,0.37f);
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.50f, Animation.RELATIVE_TO_PARENT, 0.50f);
        ra.setDuration(2000);
        ra.setRepeatCount(Animation.INFINITE);
        ra.setRepeatMode(Animation.RESTART);
        mPoint.startAnimation(ra);

    }

}
