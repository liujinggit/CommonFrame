package com.mr.liu.modulebase.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by mrliu on 2018/12/28.
 * 此类用于:将崩溃日志存储到手机目录
 */

public class LogUtils {
    private static File sdcard = Environment.getExternalStorageDirectory();

    private static final String ROOT = "/3tlive/";
    public static void printStackTrace(Throwable e){
        if (true) {
            try {
                //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                createFolder(ROOT);
                FileWriter writer = new FileWriter(sdcard.getAbsolutePath() + ROOT + "log.txt", true);
                writer.write(DateUtil.formatDateTime(new java.util.Date()) +  "系统崩溃： \n");
                e.printStackTrace(new PrintWriter(writer));
                writer.close();
            } catch (Exception e2) {
            }
        }

    }
    /**
     * create folder
     *
     * @param folder folder
     * @return successful
     */
    public static File createFolder(String folder) {
        File directory = null;
        String f = folder;
        if (!folder.startsWith("/")) {
            f = "/" + folder;
        }
        if (!folder.endsWith("/")) {
            f = f + "/";
        }
        if (isCardMounted()) {
            try {
                directory = new File(sdcard.getAbsolutePath() + f);
                if (!directory.exists() && directory.mkdirs()) {
                    return directory;
                }
            } catch (Exception ex) {
            }
        } else {
        }
        return directory;
    }
    /**
     * @return sd卡是否已经加载
     */
    public static boolean isCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
