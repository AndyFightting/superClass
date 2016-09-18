package com.example.suguiming.superclass.meTab;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.customView.CircleImageView;

public class BasicInfoActivity extends BaseSwipeActivity {

    private CircleImageView headImage;
    private TextView nichengTv;
    private TextView shengriTv;
    private TextView xingbieTv;
    private TextView qianmingTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

        headImage = (CircleImageView)findViewById(R.id.header_image);
        nichengTv = (TextView)findViewById(R.id.nicheng_tv);
        shengriTv = (TextView)findViewById(R.id.shengri_tv);
        xingbieTv = (TextView)findViewById(R.id.xingbie_tv);
        qianmingTv = (TextView)findViewById(R.id.qianming_tv);

    }

    public void itemTaped(View view){


    }

}
