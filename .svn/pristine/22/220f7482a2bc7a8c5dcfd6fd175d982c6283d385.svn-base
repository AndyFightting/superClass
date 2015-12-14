package com.example.suguiming.superclass.basic;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by suguiming on 15/11/2.
 */
public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mainQueqe;
    private ImageLoader imageLoader;
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    public RequestQueue getMainQueue() {
        if (mainQueqe == null) {
            mainQueqe = Volley.newRequestQueue(getApplicationContext(),new MultiPartStack());
        }
        return mainQueqe;
    }

    public ImageLoader getImageLoader() {
        getMainQueue();
        if (imageLoader == null) {
            imageLoader = new ImageLoader(this.mainQueqe,
                    new MyBitmapCache());
        }
        return this.imageLoader;
    }

    public <T> void addToMainQueueWithTag(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getMainQueue().add(req);
    }

    public <T> void addToMainQueue(Request<T> req) {
        req.setTag(TAG);
        getMainQueue().add(req);
    }

    public void cancelMainRequests(Object tag) {
        if (mainQueqe != null) {
            mainQueqe.cancelAll(tag);
        }
    }
}
