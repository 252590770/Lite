package com.i.lite.entity;

import com.google.gson.Gson;

/**
 * Created by L on 2018/1/11.
 */

public class DrivigOrderResult {

    private String start_name;
    private String dest_name;



    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getDest_name() {
        return dest_name;
    }

    public void setDest_name(String dest_name) {
        this.dest_name = dest_name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
