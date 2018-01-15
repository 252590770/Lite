package com.i.lite.model.modelimpl;

import com.i.lite.api.API;
import com.i.lite.app.App;
import com.i.lite.controller.MainActivity;
import com.i.lite.entity.NewsEntity;
import com.i.lite.entity.NewsParam;
import com.i.lite.inter.OnNewsListener;
import com.i.lite.model.modelinter.NewsModel;

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

public class NewsModelImpl implements NewsModel {

    public API api;

    public NewsModelImpl() {

        api = App.getRetrofit().create(API.class);

    }

    @Override
    public void getNews(final OnNewsListener onNewsListener) {

        api.getNews(new NewsParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsEntity>() {
                    @Override
                    public void accept(@NonNull NewsEntity newsEntity) throws Exception {
                        onNewsListener.onSuccess(newsEntity);
                    }
                });
    }
}
