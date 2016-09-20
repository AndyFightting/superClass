package com.example.suguiming.superclass.studentTab.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.model.StudentModel;

public class StudentFinishedCourseActivity extends BaseSwipeActivity {

    private StudentModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_student_finished_course);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("已完成课程");

        model = StudentModel.getStudent(getIntent().getStringExtra("idString"));
    }


    public static void startActivity(Activity parent, String idString) {
        Intent intent = new Intent(parent, StudentFinishedCourseActivity.class);
        intent.putExtra("idString", idString);
        parent.startActivity(intent);
    }


}
