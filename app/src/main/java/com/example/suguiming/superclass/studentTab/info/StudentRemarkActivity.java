package com.example.suguiming.superclass.studentTab.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.utils.CommonUtil;

public class StudentRemarkActivity extends BaseSwipeActivity {

    private StudentModel model;
    private EditText remarkEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_student_remark);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("备注信息");

        model = StudentModel.getStudent(getIntent().getStringExtra("idString"));

        remarkEv = (EditText)findViewById(R.id.remark_ev);
        remarkEv.setText(model.getRemarkString());
        CommonUtil.showKeyboard(remarkEv);
    }

    public static void startActivity(Activity parent, String idString) {
        Intent intent = new Intent(parent, StudentRemarkActivity.class);
        intent.putExtra("idString", idString);
        parent.startActivity(intent);
    }

    @Override
    public void backViewTap(View v) {
        CommonUtil.hideKeyboard(this);
    }

    public void saveTap(View v){
        String remarkString = remarkEv.getText().toString();
        if (CommonUtil.isEmpty(remarkString)){
            remarkString = "";
        }

        model.remarkString = remarkString;
        StudentModel.updateStudent(model,false);
        CommonUtil.showToast("保存成功");
        finish();
    }
}
