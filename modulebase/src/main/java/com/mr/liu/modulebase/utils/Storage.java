package com.mr.liu.modulebase.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ckb on 18/1/9.
 */

public final class Storage {

    private final static String APP_ROOT = "DCIM";

    public static File getAppRootDir() {
        File rootDir = null;
        boolean hasExternalStorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasExternalStorage) {
            try {
                File externalDir = Environment.getExternalStorageDirectory();
                rootDir = new File(externalDir, APP_ROOT);
                if (!rootDir.exists()) {
                    rootDir.mkdirs();
                }
            } catch (Exception e) {
                Log.e("Storage", "root error", e);
            }
        }
        return rootDir;
    }

    public static File getVideoDir(Context context) {
        File rootDir = getAppRootDir();
        if (rootDir != null && rootDir.isDirectory()) {
            File videoDir = new File(rootDir, "video");
            if (!videoDir.exists())
                videoDir.mkdirs();
            return videoDir;
        }
        return context.getFilesDir();
    }

    public static boolean moveEncryptedToStorage(AssetManager assetsManager) {
        AssetManager am = assetsManager;
        try {
            String[] files = am.list("");
            File encryptedFile = getEncryptedFile();
            for (String filename : files) {
                if (TextUtils.equals(filename, encryptedFile.getName())) {
                    InputStream is = am.open(filename);
                    OutputStream os = new FileOutputStream(encryptedFile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) != -1) {
                        os.write(buffer, 0, read);
                    }
                    is.close();
                    os.close();
                    return true;
                }
            }

        } catch (IOException e) {
            Log.e("Storage", "Error copying asset files ", e);
        }
        return false;
    }


    public static File getEncryptedFile() {
        return new File(Storage.getAppRootDir(), "encryptedApp.dat");
    }

}
