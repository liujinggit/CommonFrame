package com.mr.liu.modulebase.utils;

import android.hardware.Camera;
import android.os.Build;

/**
 * Created by mrliu on 2018/12/28.
 * 此类用于: 判断摄像头的工具类
 */

public class CheckCameraUtils {

    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }
    /**判断是否有后置摄像头
     public static boolean hasBackFacingCamera() {
     final int CAMERA_FACING_BACK = 0;
     return checkCameraFacing(CAMERA_FACING_BACK);
     }*/

    /**判断是否有前置摄像头*/
    public static boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }
}
