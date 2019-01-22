package com.mr.liu.modulebase.base.request;

import java.io.Serializable;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:
 */

public class BaseParam <T> implements Serializable {
    private T data;
    private int pageNo;
    private int pageSize;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
