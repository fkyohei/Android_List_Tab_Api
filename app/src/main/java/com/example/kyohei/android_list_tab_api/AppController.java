package com.example.kyohei.android_list_tab_api;

import android.app.Application;
import android.text.TextUtils;
//import com.activeandroid.ActiveAndroid;
//import android.activeandroid.Configuration;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Objects;

/**
 * Created by kyohei on 15/04/08.
 */
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestqueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        Configuration.Builder builder = new Configuration.Builder(getBaseContext());
//        builder.setCacheSize(1024*1024*4);
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if( mRequestqueue == null) {
            mRequestqueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestqueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if( mImageLoader == null) {
            mImageLoader =new ImageLoader(this.mRequestqueue, new LruBitmapCache());
        }
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void cancelPendingRequests(Objects tag) {
        if( mRequestqueue != null) {
            mRequestqueue.cancelAll(tag);
        }
    }


}
