package com.example.suguiming.superclass.utils;

import com.example.suguiming.superclass.model.StudentModel;
import com.squareup.otto.Bus;

/**
 * Created by suguiming on 16/9/18.
 */
public class OttoUtil {

    private static Bus bus;
    public static synchronized Bus getInstance() {
        synchronized (OttoUtil.class) {
            if (bus == null) {
                bus = new Bus();
            }
            return bus;
        }
    }

    //-----student model 的发通知----
    public static void studentAdd(StudentModel model){
        model.notifyType = StudentModel.ADD;
        getInstance().post(model);
    }

    public static void studentUpdate(StudentModel model){
        model.notifyType = StudentModel.UPDATE;
        getInstance().post(model);
    }

    public static void studentDelete(StudentModel model){
        model.notifyType = StudentModel.DELETE;
        getInstance().post(model);
    }

    //----class 的发通知------



    //-----me 的发通知 -------






}
