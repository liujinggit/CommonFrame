package com.mr.liu.modulebase.helper;

/**
 * Created by mrliu on 2018/12/28.
 * 此类用于:
 */

public class DataManager {

    private static DataManager instance;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new DataManager();
                }
            }
        }
        return instance;
    }
}
