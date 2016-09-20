package com.example.suguiming.superclass.studentTab.info;

import android.os.Bundle;
import android.view.View;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.baseSheet.BaseSheetActivity;

/**
 * Created by suguiming on 15/11/28.
 */
public class PhotoSheet extends BaseSheetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_photo_sheet);

    }

    public void itemTap(View view){
        if (itemTapListener != null){
            itemTapListener.itemTap(view,"");
        }
        dismiss();
    }

}
