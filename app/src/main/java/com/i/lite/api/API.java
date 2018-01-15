package com.i.lite.api;

import com.i.lite.entity.GetListParam;
import com.i.lite.entity.GetListResult;
import com.i.lite.entity.NewsEntity;
import com.i.lite.entity.NewsParam;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by L on 2018/1/12.
 */

public interface API {

    @POST("nc/article/headline/T1348647909107/5-20.html")
    Observable<NewsEntity> getNews(@Body NewsParam param);

//    @POST("travel/drivingOrdertoApp.action?")
//    Observable<ArrayList<GetListResult>> getListWithRx(@Body GetListParam param);

    @FormUrlEncoded
    @POST("travel/drivingOrdertoApp.action?")
    Observable<ArrayList<GetListResult>> getListWithRx(@Field("method")String method,@Field("page")String page);

}
