package com.i.lite.model.modelimpl;

import android.net.http.HttpResponseCache;
import android.util.Log;
import android.widget.Toast;

import com.i.lite.api.API;
import com.i.lite.app.App;
import com.i.lite.controller.MainActivity;
import com.i.lite.entity.DrivigOrderParam;
import com.i.lite.entity.DrivigOrderResult;
import com.i.lite.entity.GetListParam;
import com.i.lite.entity.GetListResult;
import com.i.lite.inter.OnGetListInfoListener;
import com.i.lite.model.modelinter.InfoListModel;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by L on 2018/1/12.
 */

public class InfoListModelImpl implements InfoListModel {


    @Override
    public void getInfoList(GetListParam param,final OnGetListInfoListener getListInfoListener) {

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





/*

            App.getAPI().getListWithRx(param.method,param.order_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<GetListResult>>() {
                        @Override
                        public void accept(@NonNull ArrayList<GetListResult> getListResults) throws Exception {


                            try {

                                getListInfoListener.onSuccess(getListResults);

                            }catch (Exception e){
                                getListInfoListener.onError(null);
                                Log.i("ccccccc","onError="+e.toString());
                                Log.i("ccccccc","name  onError="+InfoListModelImpl.this.toString());
                            }


                        }
                    });

*/




    }

}
