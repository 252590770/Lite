package com.i.lite.inter;

import com.i.lite.entity.DrivigOrderResult;
import com.i.lite.entity.GetListResult;
import com.i.lite.entity.NewsEntity;

import java.util.ArrayList;

/**
 * Created by L on 2018/1/12.
 */

public interface OnGetListInfoListener {
    void onSuccess(ArrayList<GetListResult> listResults);
    void onError(OnGetListInfoListener listener);
}
