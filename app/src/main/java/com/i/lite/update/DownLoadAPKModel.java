package com.i.lite.update;

import android.widget.Toast;

import com.i.lite.app.App;
import com.i.lite.utils.NetworkUtil;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by L on 2018/1/12.
 */

public class DownLoadAPKModel   {


    public void downLoad(FileDownLoadSubscriber subscriber, String url) {


        if (!NetworkUtil.isNetworkAvailable(App.getContext())) {
            Toast.makeText(App.getContext(), "没有网络O", Toast.LENGTH_SHORT).show();
            return;
        }

        //http://ps.tupppai.com/wefun_v1.0.1.apk
        //http://220.174.161.163:8088/apk/20180308.apk
        //http://111.26.200.85:8088/apk/20180308.apk
        App.getAPI().downLoad("apk/20180308.apk").
                compose(ioMainDownload()).
                subscribeWith(subscriber);
    }


    private static FlowableTransformer<ResponseBody, ResponseBody> ioMainDownload() {
        return new FlowableTransformer<ResponseBody, ResponseBody>() {
            @Override
            public Publisher<ResponseBody> apply(Flowable<ResponseBody> upstream) {
                return upstream.subscribeOn(Schedulers.io()).
                        observeOn(Schedulers.io()).
                        observeOn(Schedulers.computation()).
                        map(new Function<ResponseBody, ResponseBody>() {
                            @Override
                            public ResponseBody apply(ResponseBody responseBody) throws Exception {
                                return responseBody;
                            }
                        }).
                        observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
