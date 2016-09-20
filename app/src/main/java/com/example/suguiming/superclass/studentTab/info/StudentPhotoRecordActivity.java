package com.example.suguiming.superclass.studentTab.info;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.basic.baseSheet.ItemTapListener;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.utils.CommonUtil;

public class StudentPhotoRecordActivity extends BaseSwipeActivity implements View.OnClickListener {

    private StudentModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_student_photo_record);

        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("相册记录");

        ImageView titleRightImage = (ImageView) findViewById(R.id.title_right_image);
        titleRightImage.setImageResource(R.mipmap.add);
        TextView titleRightTv = (TextView) findViewById(R.id.title_right_tap_tv);
        titleRightTv.setOnClickListener(this);

        model = StudentModel.getStudent(getIntent().getStringExtra("idString"));


    }

    public static void startActivity(Activity parent, String idString) {
        Intent intent = new Intent(parent, StudentPhotoRecordActivity.class);
        intent.putExtra("idString", idString);
        parent.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_right_tap_tv:
                titleRightTap();
                break;
        }
    }

    private void titleRightTap() {
        PhotoSheet.show(this, PhotoSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                switch (view.getId()){
                    case R.id.camera_tv:
                        break;
                    case R.id.phone_tv:
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
