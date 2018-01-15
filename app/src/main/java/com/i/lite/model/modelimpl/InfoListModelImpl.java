package com.i.lite.model.modelimpl;

import android.util.Log;

import com.i.lite.api.API;
import com.i.lite.app.App;
import com.i.lite.controller.MainActivity;
import com.i.lite.entity.DrivigOrderParam;
import com.i.lite.entity.DrivigOrderResult;
import com.i.lite.entity.GetListParam;
import com.i.lite.entity.GetListResult;
import com.i.lite.inter.OnGetListInfoListener;
import com.i.lite.model.modelinter.InfoListModel;

import java.util.ArrayList;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by L on 2018/1/12.
 */

public class InfoListModelImpl implements InfoListModel {

    public API api;

    @Override
    public void getInfoList(GetListParam param,final OnGetListInfoListener getListInfoListener) {

        Log.i("cccccc","Order_id == "+param.order_id);

        api = App.getRetrofit().create(API.class);
        api.getListWithRx(param.method,param.order_id)//method=getDrivigOrder&page=1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<GetListResult>>() {
                    @Override
                    public void accept(@NonNull ArrayList<GetListResult> getListResults) throws Exception {
                        getListInfoListener.onSuccess(getListResults);
                    }
                });

    }

}
