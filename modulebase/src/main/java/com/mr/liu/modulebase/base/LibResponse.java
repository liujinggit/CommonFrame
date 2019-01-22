package com.mr.liu.modulebase.base;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:数据返回的基础类
 */

public class LibResponse <DataType> {

    /**
     * 通用返回值属性
     */
    private int code;
    /**
     * 通用返回信息。
     */
    private String message;
    /**
     * 具体的内容。
     */
    private DataType data;

    private int totalProperty;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }

    public int getTotalProperty() {
        return totalProperty;
    }

    public void setTotalProperty(int totalProperty) {
        this.totalProperty = totalProperty;
    }

    public boolean isOk() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "LibResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", totalProperty=" + totalProperty +
                '}';
    }
}
