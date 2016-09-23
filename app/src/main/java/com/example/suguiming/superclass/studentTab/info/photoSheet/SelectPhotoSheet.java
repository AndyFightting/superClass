package com.example.suguiming.superclass.studentTab.info.photoSheet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.baseSheet.ItemTapListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

//这是一个透明activity,用来处理相机相册回调
public class SelectPhotoSheet extends AppCompatActivity {

    public static final int TAKE_PHOTO_CODE = 1;
    public static final int ALBUM_CHOOSE_CODE = 2;
    public static final int CROP_PHOTO_CODE = 3;
    public static final int ALBUM_FULL_CODE = 4;

    public static final int RESULT_OK = -1;

    public static final String CAMERA_ACTION = "android.media.action.IMAGE_CAPTURE";
    public static final String ALBUM_ACTION = "android.intent.action.GET_CONTENT";
    public static final String CROP_ACTION = "com.android.camera.action.CROP";


    public static Uri imageUri;
    public static final String imageName = "selectedTmpPhoto.jpg";
    public static PhotoResultListener resultListener;
    public static PhotoType photoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        showSheet();
    }

    public static void show(Activity activity, PhotoType type, PhotoResultListener listener) {
        resultListener = listener;
        photoType = type;

        Intent intent = new Intent(activity, SelectPhotoSheet.class);
        activity.startActivity(intent);
    }

    public String getPhotoPath() {
        return Environment.getExternalStorageDirectory() + "/" + imageName;
    }

    public void showSheet() {
        imageUri = null;
        imageUri = Uri.fromFile(new File(getPhotoPath()));

        PhotoSheet.show(this, PhotoSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                switch (view.getId()) {
                    case R.id.camera_tv:
                        cameraTap();
                        break;
                    case R.id.phone_tv:
                        photoTap();
                        break;
                    default://点击sheet背景
                        finish();
                        break;
                }
            }
        });
    }

    private void cameraTap() {
        if (photoType == PhotoType.FULL_IMAGE) {//全尺寸
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//必须
            startActivityForResult(cameraIntent, CROP_PHOTO_CODE);
        } else {//正方形图片
            Intent cameraIntent = new Intent(CAMERA_ACTION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//必须
            startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
        }
    }

    private void photoTap() {

        AlbumDirActivity.startActivity(this, new AlbumResultListener() {
            @Override
            public void complete(View tapedView) {
                switch (tapedView.getId()){
                    case R.id.back_image:
                        finish();
                        break;
                }
            }
        });




//        if (photoType == PhotoType.FULL_IMAGE) {//全尺寸
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, ALBUM_FULL_CODE);
//        } else {//正方形图片
//            Intent intent = new Intent(ALBUM_ACTION);
//            intent.setType("image/*");
//            startActivityForResult(intent, ALBUM_CHOOSE_CODE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_CODE://开相机
                    startCameraCrop();
                    break;
                case ALBUM_CHOOSE_CODE://开相册
                    startAlbumCrop(data);
                    break;
                case CROP_PHOTO_CODE://保存裁剪后的,相机或者相册裁剪
                    getResultImage(imageUri);
                    break;
                case ALBUM_FULL_CODE://保存全尺寸相册图片
                    getResultImage(data.getData());
                    break;
                default:
                    break;
            }
        } else {
            finish();
        }
    }

    private void startCameraCrop() {
        Intent intent = new Intent(CROP_ACTION);
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_PHOTO_CODE);
    }

    private void startAlbumCrop(Intent data) {
        Intent intent = new Intent(CROP_ACTION);
        intent.setDataAndType(data.getData(), "image/*"); //区别 data.getData()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_PHOTO_CODE);
    }

    private void getResultImage(Uri uri) {
        try {
            Bitmap originBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            if (originBitmap != null) {
                if (photoType.equals(PhotoType.FULL_IMAGE)) {
                    //压缩并保存到 imageUri 里
                    FileOutputStream out = new FileOutputStream(new File(imageUri.getPath()));
                    if (originBitmap.compress(Bitmap.CompressFormat.JPEG, 10, out)) {
                        out.flush();
                        out.close();
                    }
                }else {
                    //crop的就不压缩,且已经在 imageUri里了,所以最后从imageUri获取就可了
                }

                Bitmap resultBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                resultListener.complete(resultBitmap);

                if (!originBitmap.isRecycled()) {
                    originBitmap.recycle();
                }
                if (!resultBitmap.isRecycled()) {
                    resultBitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            deleteTmpFile();
            finish();
        }
    }

    private void deleteTmpFile() {
        try {
            File outputImage = new File(getPhotoPath());
            if (outputImage.exists()) {
                outputImage.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }
}
