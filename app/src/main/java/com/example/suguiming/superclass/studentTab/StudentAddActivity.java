package com.example.suguiming.superclass.studentTab;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.utils.CommonUtil;
import com.example.suguiming.superclass.utils.OttoUtil;

public class StudentAddActivity extends BaseSwipeActivity {

    private EditText nameEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_student_add);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("添加学员");

        nameEv = (EditText) findViewById(R.id.name_ev);
    }

    public void submitTap(View v) {
        String name = nameEv.getText().toString();

        if (CommonUtil.isEmpty(name)) {
            CommonUtil.showToast("请输入学员名称");
            return;
        }

        if (StudentModel.isIn(name)){
            CommonUtil.showToast("该学员已存在");
            return;
        }

        StudentModel newStudent = new StudentModel();
        newStudent.nameString = name;
        StudentModel.addStudent(newStudent);

        OttoUtil.studentAdd(newStudent);

        nameEv.setText("");
        nameEv.setHint("继续添加");
        CommonUtil.showToast("添加成功");
    }

    public void backImageTap(View v) {
        finish();
    }

    @Override
    public void backViewTap(View v) {
        CommonUtil.hideKeyboard(this);
    }
}
