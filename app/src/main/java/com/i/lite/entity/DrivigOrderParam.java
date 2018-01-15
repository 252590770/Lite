package com.i.lite.entity;

/**
 * Created by L on 2018/1/11.
 */

public class DrivigOrderParam {

    private String method;
    private String order_id;

    public DrivigOrderParam(String method, String order_id) {
        this.method = method;
        this.order_id = order_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
