package com.example.suguiming.superclass.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseActivity;
import com.example.suguiming.superclass.basic.CircleImageView;
import com.example.suguiming.superclass.basic.HttpHelper;
import com.example.suguiming.superclass.basic.RequestListener;
import com.example.suguiming.superclass.basic.Task;
import com.example.suguiming.superclass.bean.User;
import com.example.suguiming.superclass.database.UserDao;
import com.example.suguiming.superclass.tool.SelectPhotoView;
import com.example.suguiming.superclass.tool.Tool;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UpHeadActivity extends BaseActivity {

    private TextView nameTv;
    private CircleImageView headImg;
    private SelectPhotoView selectView;
    private Bitmap selectBitmap;
    private String headImgName = "regist_head";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_up_head);
        titleTv.setText("上传头像");

        headImg = (CircleImageView)findViewById(R.id.upheader_img);
        nameTv = (TextView)findViewById(R.id.name_tv);
    }

    public void headImgTap(View view){
          selectView =new SelectPhotoView(this,new SelectPhotoView.PhotoItemClickListener() {
            @Override
            public void cameraTap() {
                fatherView.removeView(selectView);
                //调用相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, Task.BIG_PICTURE);
            }

            @Override
            public void photoTap() {
                fatherView.removeView(selectView);
                //调用相册
                Intent intent = Tool.getAlbumCropIntent(photoUri);
                startActivityForResult(intent, Task.CROP_PICTURE);
            }
            @Override
            public void cancelTap() {
                fatherView.removeView(selectView);
            }
        });
        fatherView.addView(selectView);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
         switch (requestCode){
             case Task.BIG_PICTURE:
                   if (resultCode == RESULT_OK){
                       Intent cropIntent = Tool.getCropImageIntent(photoUri);
                       startActivityForResult(cropIntent,Task.CROP_PICTURE);
                   }
                 break;
             case Task.CROP_PICTURE:
                 if (resultCode == RESULT_OK){
                     selectBitmap = null;
                     selectBitmap = getBitmap(photoUri);
                     headImg.setImageBitmap(selectBitmap);
                     try {
                         saveImage(selectBitmap,headImgName);
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
                 break;
          }
    }

    public void finishTap(View view){
        if (selectBitmap == null){
            showToast("请选择头像");
            return;
        }
        if (isEmpty(nameTv)){
            showToast("请输入昵称");
            return;
        }

        //请求注册-------
        Map<String,Object> paramter = new HashMap<>();
        paramter.put("phone",getIntent().getStringExtra("phone"));
        paramter.put("password",getIntent().getStringExtra("password"));
        paramter.put("deviceType","1");
        paramter.put("deviceToken","tmp_token");//----------现在不处理token
        paramter.put("userType","0");
        paramter.put("userName",nameTv.getText().toString());
        paramter.put("file",new File(getImgPath(headImgName)));

        HttpHelper.showHud(this, "提交中...");
        String url = Task.HOST+"user/register/1.4";
        HttpHelper.doPostRequest(url, paramter, new RequestListener() {
            @Override
            public void requestSuccess(String response) {
                log("注册成功");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    Type type = new TypeToken<User>(){}.getType();
                    User user = gson.fromJson(jsonObject.toString(), type);

                    UserDao userDao = new UserDao(UpHeadActivity.this);
                    userDao.createUser(user);

                    Intent intent = new Intent(UpHeadActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed(VolleyError error, int code, String message) {
                log("注册失败");
            }
        });
    }
}
