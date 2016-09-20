package com.example.suguiming.superclass.utils.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.utils.CommonUtil;


/**
 * Created by suguiming on 15/11/27.
 */
public class AlertUtil extends Dialog implements View.OnClickListener{

    private static AlertListener alertListener;
    private static AlertSingleListener singleListener;
    private static String contentString;
    private static boolean isSingleAlert;

    private static String cancelButtonName;
    private static String sureButtonName;

    private  TextView contentTv;
    private  LinearLayout cancelLayout;
    private  TextView sureTv;
    private  TextView cancelTv;

    public AlertUtil(Context con){
        super(con);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //背景渐暗效果
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount=0.5f;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        setContentView(R.layout.custom_dialog);

        contentTv = (TextView)findViewById(R.id.content_tv);
        cancelLayout = (LinearLayout)findViewById(R.id.alert_cancel_layout) ;
        cancelTv = (TextView)findViewById(R.id.alert_cancel_tv);
        sureTv = (TextView)findViewById(R.id.alert_sure_tv);

        contentTv.setText(contentString);

        if (isSingleAlert){
            cancelLayout.setVisibility(View.GONE);
        }
        if (!CommonUtil.isEmpty(cancelButtonName)){
            cancelTv.setText(cancelButtonName);
        }
        if (!CommonUtil.isEmpty(sureButtonName)){
            sureTv.setText(sureButtonName);
        }

        cancelButtonName = null;
        sureButtonName = null;

        cancelTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        if (alertListener!=null){
            switch (v.getId()){
                case R.id.alert_sure_tv:
                    alertListener.sureTap();
                    break;
                case R.id.alert_cancel_tv:
                    alertListener.cancelTap();
                    break;
            }
        }else if (singleListener!=null){
            switch (v.getId()){
                case R.id.alert_sure_tv:
                    singleListener.sureTap();
                    break;
                case R.id.alert_cancel_tv:
                    break;
            }
        }
    }

    public static void resetButtonNames(String cancelName,String sureName){
        cancelButtonName = cancelName;
        sureButtonName = sureName;
    }

    public static void resetSingleButtonName(String sureName){
        sureButtonName = sureName;
    }

    public static void showAlert(Activity activity,String msg,AlertListener listener){
        alertListener = listener;
        singleListener = null;
        contentString = msg;
        isSingleAlert = false;

        AlertUtil dialog = new AlertUtil(activity);
        dialog.show();
    }

    public static void showSingleAlert(Activity activity,String msg,AlertSingleListener listener){
        alertListener = null;
        singleListener = listener;
        contentString = msg;
        isSingleAlert = true;

        AlertUtil dialog = new AlertUtil(activity);
        dialog.show();
    }
}
