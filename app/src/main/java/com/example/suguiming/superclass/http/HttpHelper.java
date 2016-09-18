package com.example.suguiming.superclass.http;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.MyApplication;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Created by suguiming on 15/10/30.
 */
public class HttpHelper {

    public static String TAG = "myLog";
    public static Dialog dialog;

    public static void doPostRequest(String url, final Map<String, Object> params, final RequestListener requestListener) {
        doRequest(Request.Method.POST, url, params, requestListener);
    }

    public static void doGetRequest(String url, final RequestListener requestListener) {
        doRequest(Request.Method.GET, url, null, requestListener);
    }

    private static void doRequest(int method, String url, final Map<String, Object> params, final RequestListener requestListener) {

        MultiPartStringRequest multiPartStringRequest = new MultiPartStringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "------请求成功-----:" + response);
                        hideHud();

                        if (requestListener != null) {
                            requestListener.requestSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideHud();

                        int code = 0;
                        String msg = null;
                        if (error != null && error.networkResponse != null) {
                            code = error.networkResponse.statusCode;
                            msg = new String(error.networkResponse.data);
                        }

                        Log.i(TAG, "-----请求失败 code:" + code + "  信息:" + msg, error);
                        if (requestListener != null) {
                            requestListener.requestFailed(error, code, msg);
                            if (code == 400) {//说明有错误的 message
                                try {
                                    JSONObject jsonObject = new JSONObject(msg);
                                    showToast(jsonObject.getString("msg"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showToast("请检查网络");
                            }
                        }
                    }
                });

        if (params != null) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                Object obj = params.get(key);
                if (obj instanceof File){
                    multiPartStringRequest.addFileUpload(key,(File)obj);
                }else {
                    multiPartStringRequest.addStringUpload(key,obj.toString());
                }
            }
        }

        MyApplication.getInstance().addToMainQueue(multiPartStringRequest);//单例的mainQueue
    }


    public static void showToast(String msg) {
        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showHud(Context context, final String msg) {
        dialog = new Dialog(context, R.style.dialog) {
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.hud_load);
                TextView title = (TextView) findViewById(R.id.load_tv);
                if (msg == null || msg.equals("")) {
                    title.setVisibility(View.GONE);
                } else {
                    title.setText(msg);
                }
            }
        };
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void hideHud() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
