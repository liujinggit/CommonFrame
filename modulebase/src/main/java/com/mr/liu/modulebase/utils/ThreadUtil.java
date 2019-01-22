package com.mr.liu.modulebase.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by mrliu on 2018/12/28.
 * 此类用于: 线程工具类
 */

public class ThreadUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private static Executor sExecutor = Executors.newSingleThreadExecutor();

    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    public static void runOnMainThread(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void runOnMainThreadDelayed(Runnable runnable, long delayedMS) {
        sHandler.postDelayed(runnable, delayedMS);
    }
}
