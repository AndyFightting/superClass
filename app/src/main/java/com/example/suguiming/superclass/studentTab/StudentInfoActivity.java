package com.example.suguiming.superclass.studentTab;

import android.os.Bundle;
import android.view.View;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseActivity;

public class StudentInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_student_info);
        titleTv.setText("学员信息");
    }

    public void itemTaped(View view){


    }

    public void deleteStudent(View view){


    }



}
