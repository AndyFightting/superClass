package com.example.suguiming.superclass.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseActivity;
import com.example.suguiming.superclass.basic.HttpHelper;
import com.example.suguiming.superclass.basic.RequestListener;
import com.example.suguiming.superclass.basic.Task;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class RegistActivity extends BaseActivity {

    private TextView phoneEt;
    private TextView passwordEt;
    private TextView codeEt;
    private TextView secondTv;
    private TextView sendTv;

    private boolean canSendCode = true;
    private Timer timer;
    private int second = 3;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_regist);
        titleTv.setText("注册");

        phoneEt = (EditText)findViewById(R.id.regist_phone_et);
        passwordEt = (EditText)findViewById(R.id.regist_pw_et);
        codeEt = (EditText)findViewById(R.id.regist_code_et);
        secondTv = (TextView)findViewById(R.id.second_tv);
        sendTv = (TextView)findViewById(R.id.send_tv);

    }

    public void sendTaped(View view){
        if (phoneEt.getText().toString() == null ||phoneEt.getText().toString().equals("")){
            showToast("手机号不能为空");
            return;
        }
        if (!isPhoneNum(phoneEt.getText().toString())){
            showToast("请输入手机号码");
            return;
        }
        if (passwordEt.getText().toString() == null ||passwordEt.getText().toString().equals("")){
            showToast("请输入密码");
            return;
        }
        if (canSendCode){
            sendCodeRequest();
        }
    }

    private void sendCodeRequest(){
        String url = Task.HOST+"user/verify/"+phoneEt.getText().toString();

        HttpHelper.showHud(this, "发送中...");
        HttpHelper.doGetRequest(url, new RequestListener() {
            @Override
            public void requestSuccess(String response) {
                doSendCodeSuccess(response);
            }

            @Override
            public void requestFailed(VolleyError error, int code, String message) {

            }
        });
    }

    private void doSendCodeSuccess(String response){
        canSendCode = false;
        sendTv.setText("验证码已发送");
        sendTv.setTextColor(getResources().getColor(R.color.light_gray));

        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 119;
                handler.sendMessage(message);
            }
        },0,1000);

        try {
            JSONObject jsonObject = new JSONObject(response+"");
            code = Integer.parseInt(jsonObject.getString("code"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void registTaped(View view){
        if (phoneEt.getText().toString() == null ||phoneEt.getText().toString().equals("")){
            showToast("手机号不能为空");
            return;
        }
        if (!isPhoneNum(phoneEt.getText().toString())){
            showToast("请输入手机号码");
            return;
        }
        if (passwordEt.getText().toString() == null ||passwordEt.getText().toString().equals("")){
            showToast("请输入密码");
            return;
        }

        //-------------
        if (second == 0){
            showToast("请重新发送验证码");
            return;
        }

        if (codeEt.getText().toString() == null ||codeEt.getText().toString().equals("")){
            showToast("请输入验证码");
            return;
        }
        int inputCode = Integer.parseInt(codeEt.getText().toString());
        if (code == inputCode){
            //------进入上传头像页面
            Intent intent = new Intent(this,UpHeadActivity.class);
            intent.putExtra("phone",phoneEt.getText().toString());
            intent.putExtra("password",passwordEt.getText().toString());
            startActivity(intent);
        }else {
            showToast("验证码错误");
        }
    }


    final  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 119:
                    if (second > 0) {
                        second--;
                        secondTv.setText(second + "秒");
                    } else {
                        second = 3;
                        canSendCode = true;
                        secondTv.setText("");
                        sendTv.setText("重新发送");
                        sendTv.setTextColor(getResources().getColor(R.color.white));
                        timer.cancel();
                    }
                    break;
            }
        }
    };


}
