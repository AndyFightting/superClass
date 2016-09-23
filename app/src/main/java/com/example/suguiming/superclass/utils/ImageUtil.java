package com.example.suguiming.superclass.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.suguiming.superclass.basic.MyApplication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by suguiming on 16/9/18.
 */

// Thumbnails  ThumbnailUtils
public class ImageUtil {

    public static final MyApplication application = MyApplication.getInstance();
//    //保存图片
//    public void saveImage(Bitmap bmp, String imgName)
//            throws FileNotFoundException {
//        String filePath = getFilesDir() + "/"+imgName+".jpg";
//        File file = new File(filePath);
//        if (file.exists()) {
//            file.delete();
//        }
//        OutputStream os = new FileOutputStream(file);
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
//        try {
//            os.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //获取图片路径
//    public String getImgPath(String imgName){
//        return getFilesDir() + "/" +imgName+ ".jpg";
//    }
//
//    public void saveBitmapFile(Bitmap bitmap){
//        File file=new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
//        try {
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            bos.flush();
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




}
