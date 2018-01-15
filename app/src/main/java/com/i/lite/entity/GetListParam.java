package com.i.lite.entity;

/**
 * Created by L on 2018/1/11.
 */

public class GetListParam {

    public String method;
    public String order_id;

    public GetListParam() {}

    public GetListParam(String method, String order_id) {
        this.method = method;
        this.order_id = order_id;
    }
}
