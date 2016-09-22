package com.example.suguiming.superclass.studentTab.info;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.studentTab.info.photoSheet.PhotoResultListener;
import com.example.suguiming.superclass.studentTab.info.photoSheet.PhotoType;
import com.example.suguiming.superclass.studentTab.info.photoSheet.SelectPhotoSheet;
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
       SelectPhotoSheet.show(this, PhotoType.FULL_IMAGE, new PhotoResultListener() {
           @Override
           public void complete(Bitmap bitmap) {
               if (bitmap!=null){
                   CommonUtil.showToast("获取成功");
               }
           }
       });
    }
}
