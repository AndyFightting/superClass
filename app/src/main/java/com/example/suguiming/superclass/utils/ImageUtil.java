package com.example.suguiming.superclass.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by suguiming on 16/9/18.
 */
public class ImageUtil {

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
}
