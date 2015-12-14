package com.example.suguiming.superclass.basic;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.suguiming.superclass.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BaseActivity extends FragmentActivity {

    public RelativeLayout fatherView;
    public RelativeLayout titleView;
    public LinearLayout mainView;

    public TextView titleTv;
    public ImageView backImg;

    public Uri photoUri = Uri.parse(Task.PHOTO_LOCATION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setMainView(int viewId){
        setContentView(R.layout.activity_basic);

        fatherView = (RelativeLayout)findViewById(R.id.father_view);
        mainView = (LinearLayout)findViewById(R.id.main_view);
        titleView = (RelativeLayout)findViewById(R.id.title_layout);

        titleTv = (TextView)findViewById(R.id.title_tv);
        backImg = (ImageView)findViewById(R.id.back_img);

        LayoutInflater inflater = LayoutInflater.from(this);
        View tmpView = inflater.inflate(viewId, null);
        mainView.addView(tmpView);

    }

    public void backTaped(View view){
        finish();
    }

    //点击背景隐藏键盘
    public void backViewTaped(View view){
        try{
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
             imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showAlert(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setNegativeButton("确定",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public boolean isPhoneNum(String phoneNum){
        if (phoneNum == null || phoneNum.equals("")|| phoneNum.length() != 11){//手机只能11位数
            return false;
        }
        return true;
    }

    public void log(String message){
        Log.i("myLog",message);
    }

    public void hideBackBt(){
        backImg.setVisibility(View.GONE);
    }

    public View viewWithId(int resource){
       return LayoutInflater.from(this).inflate(resource, null);
    }

    public Bitmap getBitmap(Uri uri){
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public boolean isEmpty(TextView textView){
        if (textView == null|| textView.getText() == null ||textView.getText().toString().trim().equals("")){
            return true;
        }else {
            return false;
        }
    }

    //保存图片
    public void saveImage(Bitmap bmp, String imgName)
            throws FileNotFoundException {
        String filePath = getFilesDir() + "/"+imgName+".jpg";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        OutputStream os = new FileOutputStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, os);
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取图片路径
    public String getImgPath(String imgName){
        return getFilesDir() + "/" +imgName+ ".jpg";
    }

}


