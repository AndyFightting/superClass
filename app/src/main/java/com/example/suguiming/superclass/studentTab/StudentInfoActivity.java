package com.example.suguiming.superclass.studentTab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.basic.baseSheet.ItemTapListener;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.studentTab.info.EditNameSheet;
import com.example.suguiming.superclass.studentTab.info.GenderSheet;
import com.example.suguiming.superclass.studentTab.info.PhoneSheet;
import com.example.suguiming.superclass.studentTab.info.RemainCountSheet;
import com.example.suguiming.superclass.utils.CommonUtil;

public class StudentInfoActivity extends BaseSwipeActivity {

    private StudentModel model;

    private TextView nameTv;
    private TextView genderTv;
    private TextView phoneTv;
    private TextView remainTv;
    private TextView finishTv;
    private TextView totalTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_student_info);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("学员信息");

        model = StudentModel.getStudent(getIntent().getStringExtra("idString"));
        initViews();
        refreshViews();
    }

    public static void startActivity(Activity parent, String idString) {
        Intent intent = new Intent(parent, StudentInfoActivity.class);
        intent.putExtra("idString", idString);
        parent.startActivity(intent);
    }

    private void initViews() {
        nameTv = (TextView) findViewById(R.id.name_tv);
        genderTv = (TextView) findViewById(R.id.gender_tv);
        phoneTv = (TextView) findViewById(R.id.phone_tv);
        remainTv = (TextView) findViewById(R.id.remain_tv);
        finishTv = (TextView) findViewById(R.id.finish_tv);
        totalTv = (TextView) findViewById(R.id.total_tv);
    }

    private void refreshViews() {
        if (model != null) {
            int remainCount = model.getRemainCount();
            int finishCount = model.getFinishCount();

            nameTv.setText(model.getNameString());
            genderTv.setText(CommonUtil.getGenderString(model.getGender()));
            phoneTv.setText(model.getPhoneString());
            remainTv.setText(remainCount + "节");
            finishTv.setText(finishCount + "节");
            totalTv.setText(remainCount + finishCount + "节");
        }
    }

    public void itemTaped(View view) {
        switch (view.getId()) {
            case R.id.name_layout:
                nameTaped();
                break;
            case R.id.gender_layout:
                genderTap();
                break;
            case R.id.phone_layout:
                phoneTap();
                break;
            case R.id.remain_layout:
                remainCountTap();
                break;
        }
    }

    private void nameTaped() {
        EditNameSheet.show(this, EditNameSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {//提交修改才调用了该方法
                model.setNameString(result);
                StudentModel.updateStudent(model, true);
                refreshViews();
            }
        });
    }

    private void genderTap() {
        GenderSheet.show(this, GenderSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                switch (view.getId()) {
                    case R.id.nan_layout:
                        model.setGender(1);
                        break;
                    case R.id.nv_layout:
                        model.setGender(2);
                        break;
                }
                StudentModel.updateStudent(model, false);
                refreshViews();
            }
        });
        GenderSheet.refreshView(model.getGender());
    }

    private void phoneTap() {
        PhoneSheet.show(this, PhoneSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                model.setPhoneString(result);
                StudentModel.updateStudent(model, false);
                refreshViews();
            }
        });
    }

    private void remainCountTap() {
        RemainCountSheet.show(this, RemainCountSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                int count = Integer.parseInt(result);
                model.setRemainCount(count);
                StudentModel.updateStudent(model,true);

                refreshViews();
            }
        });
        RemainCountSheet.refreshPK(model.getRemainCount());

    }

    public void deleteStudent(View view) {


    }


}
