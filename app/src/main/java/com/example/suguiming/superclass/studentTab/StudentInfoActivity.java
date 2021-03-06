package com.example.suguiming.superclass.studentTab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.basic.baseSheet.ItemTapListener;
import com.example.suguiming.superclass.utils.alert.AlertUtil;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.studentTab.info.EditNameSheet;
import com.example.suguiming.superclass.studentTab.info.GenderSheet;
import com.example.suguiming.superclass.studentTab.info.PhoneSheet;
import com.example.suguiming.superclass.studentTab.info.RemainCountSheet;
import com.example.suguiming.superclass.studentTab.info.StudentFinishedCourseActivity;
import com.example.suguiming.superclass.studentTab.info.StudentPhotoRecordActivity;
import com.example.suguiming.superclass.studentTab.info.StudentRemarkActivity;
import com.example.suguiming.superclass.utils.alert.AlertListener;
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
            case R.id.photo_layout:
                StudentPhotoRecordActivity.startActivity(this, model.getIdString());
                break;
            case R.id.remark_layout:
                StudentRemarkActivity.startActivity(this, model.getIdString());
                break;
            case R.id.remain_layout:
                remainCountTap();
                break;
            case R.id.finish_layout:
                StudentFinishedCourseActivity.startActivity(this, model.getIdString());
                break;
        }
    }

    private void nameTaped() {
        EditNameSheet.show(this, EditNameSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {//提交修改才调用了该方法
                switch (view.getId()){
                    case R.id.sheet_yes:
                        model.setNameString(result);
                        StudentModel.updateStudent(model, true);
                        refreshViews();
                        break;
                }
            }
        });
        EditNameSheet.setPreName(model.getNameString());
    }

    private void genderTap() {
        GenderSheet.show(this, GenderSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                switch (view.getId()) {
                    case R.id.nan_layout:
                        model.setGender(1);
                        StudentModel.updateStudent(model, false);
                        refreshViews();
                        break;
                    case R.id.nv_layout:
                        model.setGender(2);
                        StudentModel.updateStudent(model, false);
                        refreshViews();
                        break;
                }
            }
        });
        GenderSheet.refreshView(model.getGender());
    }

    private void phoneTap() {
        if (CommonUtil.isEmpty(model.getPhoneString())){
            showEditPhone();
        }else {
            AlertUtil.resetButtonNames("打电话","修改电话");
            AlertUtil.showAlert(this, "您要打电话, 还是要修改电话?", new AlertListener() {
                @Override
                public void sureTap() {
                    showEditPhone();
                }

                @Override
                public void cancelTap() {//拨打电话
                  CommonUtil.makePhoneCall(StudentInfoActivity.this,model.getPhoneString());
                }
            });
        }
    }

    private void showEditPhone(){
        PhoneSheet.show(this, PhoneSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                switch (view.getId()){
                    case R.id.sheet_yes:
                        model.setPhoneString(result);
                        StudentModel.updateStudent(model, false);
                        refreshViews();
                        break;
                }
            }
        });
        PhoneSheet.setPrePhone(model.getPhoneString());
    }

    private void remainCountTap() {
        RemainCountSheet.show(this, RemainCountSheet.class, new ItemTapListener() {
            @Override
            public void itemTap(View view, String result) {
                switch (view.getId()){
                    case R.id.sheet_yes:
                        int count = Integer.parseInt(result);
                        model.setRemainCount(count);
                        StudentModel.updateStudent(model, true);
                        refreshViews();
                        break;
                }
            }
        });
        RemainCountSheet.refreshPK(model.getRemainCount());

    }

    public void deleteStudent(View view) {
        AlertUtil.showAlert(this, "若删除该学员,则与该学员有关的课程信息都将被删除 !\n\n您确定要删除该学员吗 ?", new AlertListener() {
            @Override
            public void sureTap() {
                StudentModel.deleteStudent(model.getIdString(), true);
                CommonUtil.showToast("删除成功");
                finish();
            }

            @Override
            public void cancelTap() {
            }
        });
    }

}
