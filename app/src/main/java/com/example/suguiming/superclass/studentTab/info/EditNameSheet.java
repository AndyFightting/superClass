package com.example.suguiming.superclass.studentTab.info;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.baseSheet.BaseSheetActivity;
import com.example.suguiming.superclass.model.StudentModel;
import com.example.suguiming.superclass.utils.CommonUtil;

public class EditNameSheet extends BaseSheetActivity {

    private EditText nameEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name_sheet);
        TextView titleTv = (TextView) findViewById(R.id.sheet_title_tv);
        titleTv.setText("修改名称");

        nameEv = (EditText) findViewById(R.id.name_ev);
        CommonUtil.showKeyboard(nameEv);
    }

    public void imageTaped(View v) {
        switch (v.getId()) {

            case R.id.sheet_yes:

                String name = nameEv.getText().toString();
                if (CommonUtil.isEmpty(name)) {
                    CommonUtil.showToast("请输入学员名称");
                    return;
                }

                if (StudentModel.isIn(name)) {
                    CommonUtil.showToast("该学员已存在");
                    return;
                }

                itemTapListener.itemTap(v, name);
                dismiss();

                break;

            case R.id.sheet_close:
                dismiss();
                break;
        }
    }
}