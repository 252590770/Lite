package com.i.lite.model.modelimpl;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.i.lite.app.App;
import com.i.lite.entity.GetListParam;
import com.i.lite.entity.GetListResult;
import com.i.lite.inter.OnGetListInfoListener;
import com.i.lite.model.modelinter.InfoListModel;
import com.i.lite.utils.NetworkUtil;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by L on 2018/1/12.
 */

public class InfoListModelImpl implements InfoListModel {


    @Override
    public void getInfoList(GetListParam param,final OnGetListInfoListener getListInfoListener) {

        if (!NetworkUtil.isNetworkAvailable(App.getContext())) {
//            Toast.makeText(App.getContext(), "没有网络O", Toast.LENGTH_SHORT).show();
            getListInfoListener.onError(new Exception("没有网络o"));
            return;
        }

        LayoutInflater.from(App.getContext());

        App.getAPI().getListWithRx(param.method,param.order_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<GetListResult>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull ArrayList<GetListResult> listResults) {
                        try {
                            getListInfoListener.onSuccess(listResults);
                        }catch (Exception e){
                            getListInfoListener.onError(e);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getListInfoListener.onError(e);
                        Log.i("ccccccc","onError="+e.toString());
                        Log.i("ccccccc","onError="+e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });










    }

}
