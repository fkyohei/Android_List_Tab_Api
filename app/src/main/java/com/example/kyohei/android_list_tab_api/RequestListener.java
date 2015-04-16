package com.example.kyohei.android_list_tab_api;

import com.android.volley.VolleyError;

/**
 * Created by kyohei on 15/04/08.
 */
public class RequestListener {

    public interface SuccesseListener<T> {
        public void onResponse(T response);
    }

    public interface  FailureListener {
        public void onErrorResponse(VolleyError error);
    }
}
