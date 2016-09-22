package com.example.suguiming.superclass.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by suguiming on 16/9/18.
 */

// Thumbnails  ThumbnailUtils
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


    public static ArrayList<HashMap<String,String>> getLocalImages(Context context) {
        ArrayList<HashMap<String,String>> mapArrayList = new ArrayList<>();
        HashMap<String,String> pictureMap;

        ContentResolver cr = context.getContentResolver();
        //先得到缩略图的URL和对应的图片id
        Cursor cursor = cr.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.Thumbnails.IMAGE_ID,
                        MediaStore.Images.Thumbnails.DATA
                },
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                pictureMap = new HashMap<>();
                pictureMap.put("image_id_path",cursor.getInt(0)+"");
                pictureMap.put("thumbnail_path",cursor.getString(1));
                mapArrayList.add(pictureMap);
            } while (cursor.moveToNext());
            cursor.close();
        }
        //再得到正常图片的path
        for (int i = 0;i<mapArrayList.size();i++) {
            pictureMap = mapArrayList.get(i);
            String media_id = pictureMap.get("image_id_path");
            cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            MediaStore.Images.Media.DATA
                    },
                    MediaStore.Audio.Media._ID+"="+media_id,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                do {
                    pictureMap.put("image_id",cursor.getString(0));
                    mapArrayList.set(i,pictureMap);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return mapArrayList;
    }

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
