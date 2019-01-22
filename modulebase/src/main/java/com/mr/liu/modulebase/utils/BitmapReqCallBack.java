package com.mr.liu.modulebase.utils;

/**
 * Created by mrliu on 2018/1/15.
 * 此类用于:图片转化为bitmap回调
 */

public interface BitmapReqCallBack<T>{

    void onSuccess(T result);

    void onFailure(Throwable throwable);

    void onCancel();
}
