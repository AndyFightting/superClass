package com.example.suguiming.superclass.studentTab.info;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.baseSheet.BaseSheetActivity;
import com.example.suguiming.superclass.customView.CustomNumberPicker;

public class RemainCountSheet extends BaseSheetActivity {

    public static CustomNumberPicker countPicker;
    public static int currentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remain_count_sheet);
        TextView titleTv = (TextView) findViewById(R.id.sheet_title_tv);
        titleTv.setText("修改剩余课程数");
        
        initPK();
        refreshPK(currentCount);
    }

    @Override
    protected void onDestroy() {
        //---类数据还原----
        currentCount = 0;
        super.onDestroy();
    }

    private void initPK(){
        countPicker = (CustomNumberPicker) findViewById(R.id.count_pk);
        countPicker.setMinValue(0);
        countPicker.setMaxValue(100);

        countPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value+"";
            }
        });

        countPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentCount = newVal;
            }
        });
    }

    public static void refreshPK(int count){
        currentCount = count;
        if (countPicker != null){
             countPicker.setValue(count);
        }
    }

    public void imageTaped(View v){
        switch (v.getId()) {
            case R.id.sheet_yes:
                itemTapListener.itemTap(v,currentCount+"");
                dismiss();
                break;
            case R.id.sheet_close:
                dismiss();
                break;
        }


    }
}
