package com.example.suguiming.superclass.studentTab.info;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.baseSheet.BaseSheetActivity;
import com.example.suguiming.superclass.utils.CommonUtil;

public class PhoneSheet extends BaseSheetActivity {

    private EditText phoneEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_sheet);

        TextView titleTv = (TextView) findViewById(R.id.sheet_title_tv);
        titleTv.setText("修改手机号");

        phoneEv = (EditText) findViewById(R.id.phone_ev);
        CommonUtil.showKeyboard(phoneEv);
    }

    public void imageTaped(View v) {
        switch (v.getId()) {

            case R.id.sheet_yes:
                String phone = phoneEv.getText().toString();
                if (CommonUtil.isEmpty(phone)) {
                    CommonUtil.showToast("请输入手机号");
                    return;
                }

                if (!CommonUtil.isPhoneNum(phone)){
                    CommonUtil.showToast("手机号不正确");
                    return;
                }

                itemTapListener.itemTap(v, phone);
                dismiss();
                break;

            case R.id.sheet_close:
                dismiss();
                break;
        }
    }
}
