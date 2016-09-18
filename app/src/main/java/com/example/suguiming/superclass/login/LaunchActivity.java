package com.example.suguiming.superclass.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.suguiming.superclass.MainActivity;
import com.example.suguiming.superclass.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //启动页停留2秒后跳转
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              jumpToActivity();
          }
      }, 2000);

        this.getApplication();

    }

    //跳到登录页或者主页
    private void jumpToActivity(){
        if (true){//没有登录
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {//已经登录

        }
    }
}
