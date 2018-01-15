package com.i.lite.model.modelinter;

import com.i.lite.entity.GetListParam;
import com.i.lite.inter.OnGetListInfoListener;
import com.i.lite.inter.OnNewsListener;

/**
 * Created by L on 2018/1/12.
 */

public interface InfoListModel {

    void getInfoList(GetListParam param ,OnGetListInfoListener getListInfoListener);

}
