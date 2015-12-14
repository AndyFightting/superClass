package com.example.suguiming.superclass.meTab;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseActivity;
import com.example.suguiming.superclass.login.LoginActivity;

public class CountManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_count_manager);
        titleTv.setText("账号管理");


    }

    public void bindClick(View view){

    }

    public void logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
