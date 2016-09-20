package com.example.suguiming.superclass.studentTab.info;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.baseSheet.BaseSheetActivity;

public class GenderSheet extends BaseSheetActivity {

    private static ImageView nanImage;
    private static ImageView nvImage;
    private static int currentGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_sheet);

        nanImage = (ImageView) findViewById(R.id.nan_image);
        nvImage = (ImageView) findViewById(R.id.nv_image);

        refreshView(currentGender);
    }

    @Override
    protected void onDestroy() {
        //---类数据还原----
        currentGender = 0;
        super.onDestroy();
    }

    public static void refreshView(int gender){//0默认,1男,2女
        currentGender = gender;
        if (nanImage==null || nvImage==null){
            return;
        }
        if (currentGender == 0){
            nanImage.setVisibility(View.GONE);
            nvImage.setVisibility(View.GONE);
        }else if(currentGender == 1){
            nanImage.setVisibility(View.VISIBLE);
            nvImage.setVisibility(View.GONE);
        }else if (currentGender == 2){
            nanImage.setVisibility(View.GONE);
            nvImage.setVisibility(View.VISIBLE);
        }
    }

    public void imageTaped(View v) {
        itemTapListener.itemTap(v,"");
        dismiss();

        switch (v.getId()) {
            case R.id.nan_layout:
                refreshView(1);
                break;
            case R.id.nv_layout:
                refreshView(2);
                break;
        }
    }
}
