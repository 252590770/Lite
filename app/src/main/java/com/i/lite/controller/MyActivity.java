package com.i.lite.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MyActivity extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }





}
