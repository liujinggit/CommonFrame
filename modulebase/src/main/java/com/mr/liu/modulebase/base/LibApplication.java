package com.mr.liu.modulebase.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.mr.liu.modulebase.BuildConfig;
import com.mr.liu.modulebase.helper.SPUtils;
import com.mr.liu.modulebase.manager.ActivityManage;
import com.mr.liu.modulebase.utils.LogUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mrliu on 2018/12/28.
 * 此类用于: 基层application
 */

public class LibApplication extends Application {

    //Activity管理器
    private ActivityManage activityManage;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this ;
        MultiDex.install(this);
    }
    private static LibApplication instance;
    private void LibApplication(){}
    public static LibApplication getInstance() {
        if(instance == null){
            synchronized (LibApplication.class){
                if (instance == null) {
                    instance = new LibApplication();
                }
            }
        }
        return  instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        activityManage = new ActivityManage();
        SPUtils.init(getApplicationContext(),"my_demo");
        //初始化Fresco
        Set<RequestListener> listeners = new HashSet<>();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(listeners)
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this,config);
        initARouter();
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
    }

    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            LogUtils.printStackTrace(ex);
        }
    };


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    private void exitApp() {
        activityManage.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 返回Activity管理器
     */
    public ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);// 尽可能早，推荐在Application中初始化
    }

}
