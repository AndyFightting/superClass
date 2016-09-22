package com.example.suguiming.superclass.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.example.suguiming.superclass.basic.MyApplication;
import com.example.suguiming.superclass.utils.alert.AlertSingleListener;

import java.util.Random;

/**
 * Created by suguiming on 16/9/18.
 */
public class CommonUtil {

    public static final MyApplication application = MyApplication.getInstance();

    public static boolean isEmpty(String string){
        if (string == null|| string.trim().equals("") || string.length()==0 || string.equals("null") || string.equals("<null>")){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isPhoneNum(String string){
        if (!isEmpty(string) && string.length() == 11){
            String first = string.substring(0,1);
            if (first.equals("1")){
                return true;
            }
        }
        return false;
    }

    public static void makePhoneCall(Activity activity,String phoneString){
        if (CommonUtil.isPhoneNum(phoneString)){
            Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneString));
            activity.startActivity(phoneIntent);
        }else {
            CommonUtil.showToast("手机号有误");
        }
    }

    public static void showToast(String msg){
        Toast.makeText(application,msg,Toast.LENGTH_SHORT).show();
    }

    public static String getUniqueString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static void hideKeyboard(Activity activity){
        try{
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showKeyboard(final EditText editText){
        try{
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputMethodManager=(InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    inputMethodManager.showSoftInput(editText,0);
                }
            }, 100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getGenderString(int gender){
        if (gender == 1){
            return "男";
        }else if(gender == 2){
            return "女";
        }else {
            return "";
        }
    }

}
