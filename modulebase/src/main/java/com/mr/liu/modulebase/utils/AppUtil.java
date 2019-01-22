package com.mr.liu.modulebase.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;

import com.mr.liu.modulebase.base.LibApplication;

import java.util.List;
import java.util.UUID;

/**
 * Created by mrliu on 2018/12/27.
 * 此类用于: app的工具类
 */

public class AppUtil {

    /**
     * 检测手机是否安装了微信客户端
     *
     * @param context
     * @return
     */
    public boolean isWeixinAvilible(Context context) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public boolean isQQClientAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否有新浪客户端
     * @return
     */
    public boolean isSinaWeiboAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取版本名称
     * @return
     */
    public String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return " ";
    }

    /**
     * 获取版本号
     * @return
     */
    public int getVersionCode(Context context){
        PackageManager packageManager =  context.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo( context.getPackageName(),0);
            int version = packInfo.versionCode;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 100;
    }

    /**
     * 判断是否是主线程
     * @return
     */
    public static boolean isMainThread(){
        if(Thread.currentThread() == Looper.getMainLooper().getThread()){
            return true;
        }else {
            return false;
        }
    }


    /**
     * 获取UUID
     * @return
     */
    public String getUUID32(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }




}
