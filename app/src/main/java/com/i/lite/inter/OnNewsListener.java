package com.i.lite.inter;

import com.i.lite.entity.NewsEntity;

/**
 * Created by L on 2018/1/12.
 */

public interface OnNewsListener {

    void onSuccess(NewsEntity newsEntity);
    void onError(OnNewsListener listener);
}
