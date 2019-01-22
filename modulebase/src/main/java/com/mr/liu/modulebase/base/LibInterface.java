package com.mr.liu.modulebase.base;

import android.app.Dialog;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:
 */

public interface LibInterface {

    /**
     * 数据请求发生网络异常时调用。
     */
    void showNetworkException();

    /**
     * 发生Error但又不是网络异常时调用。
     */
    void showUnknownException();

    /**
     * 数据成功返回但不是预期值时调用。
     */
    void showDataException(String msg);

    /**
     * 显示加载完成的UI(e.g. 复位 Ultra-Ptr头部或尾部)
     */
    void showLoadingComplete();

    /**
     * 显示进度条对话框。
     */
    Dialog showLoadingDialog();

    /**
     * 关闭进度条对话框。
     */
    void dismissLoadingDialog();
}
