package com.example.suguiming.superclass.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by suguiming on 16/9/20.
 */
public class FileUtil {

    public static String DIR_NAME = "/superClass";
    public static String PATH =  Environment.getExternalStorageDirectory().getPath();

    //创建文件夹    "/hello" ,只能一级一级的建
    public static void createDirIfNotExist(String dirName) {
        try {
            File outputImage = new File(PATH+dirName);
            if (!outputImage.exists()) {
                outputImage.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getTotalMB() {
        StatFs stat = new StatFs(PATH);
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getBlockCountLong();
        return (availableBlocks * blockSize) / 1024 / 1024;
    }

    public static long getAvailableMB() {
        StatFs stat = new StatFs(PATH);
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return (availableBlocks * blockSize) / 1024 / 1024;
    }


}





