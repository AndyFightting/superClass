package com.example.suguiming.superclass.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseActivity;
import com.example.suguiming.superclass.basic.HttpHelper;
import com.example.suguiming.superclass.basic.MyApplication;
import com.example.suguiming.superclass.basic.RequestListener;
import com.example.suguiming.superclass.basic.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {


    private TextView phoneTv;
    private TextView passwordTv;
    private ImageView headImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneTv = (TextView)findViewById(R.id.login_phone_et);
        passwordTv = (TextView)findViewById(R.id.login_pw_et);
        headImg = (ImageView)findViewById(R.id.login_head);

    }

    public void loginTaped(View view) {
        Picasso.with(this)
                .load("http://i.imgur.com/DvpvklR.png")
                .placeholder(R.mipmap.radius_head)
                .into(headImg);


        if (isEmpty(phoneTv)){
            showToast("手机号不能为空");
            return;
        }
        if (!isPhoneNum(phoneTv.getText().toString())){
            showToast("请输入手机号码");
            return;
        }
        if (isEmpty(phoneTv)){
            showToast("请输入密码");
            return;
        }

        Map<String ,Object>params = new HashMap<>();
        params.put("phone", phoneTv.getText().toString());
        params.put("password", passwordTv.getText().toString());
        params.put("deviceType", "1");//Android
        params.put("deviceToken", "token");

        HttpHelper.showHud(this, "登录中...");
        HttpHelper.doPostRequest(Task.HOST + "user/login", params, new RequestListener() {
            @Override
            public void requestSuccess(String response) {
                doLoginSuccess(response);
            }
            @Override
            public void requestFailed(VolleyError error,int code,String message) {

            }
        });

    }

    private void doLoginSuccess(String response){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void registTaped(View view){
        Intent intent = new Intent(this,RegistActivity.class);
        startActivity(intent);

    }

    public void findPwTaped(View view){
         Intent intent = new Intent(this,FindPasswordActivity.class);
         startActivity(intent);
    }

    public void thirdClicked(View view){


    }

}
