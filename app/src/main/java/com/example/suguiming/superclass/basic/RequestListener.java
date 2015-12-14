package com.example.suguiming.superclass.basic;

import com.android.volley.VolleyError;

/**
 * Created by suguiming on 15/10/30.
 */
public interface RequestListener {
    void requestSuccess(String response);
    void requestFailed(VolleyError error,int code,String message);
}
