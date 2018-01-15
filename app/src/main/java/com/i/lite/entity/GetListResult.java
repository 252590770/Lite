package com.i.lite.entity;

import com.google.gson.Gson;

/**
 * Created by L on 2018/1/11.
 */

public class GetListResult {

    private String driv_dest;
    private String phone_tell;

    public String getDriv_dest() {
        return driv_dest;
    }

    public void setDriv_dest(String driv_dest) {
        this.driv_dest = driv_dest;
    }

    public String getPhone_tell() {
        return phone_tell;
    }

    public void setPhone_tell(String phone_tell) {
        this.phone_tell = phone_tell;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
