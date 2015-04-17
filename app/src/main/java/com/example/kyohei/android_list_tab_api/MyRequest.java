package com.example.kyohei.android_list_tab_api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by kyohei on 15/04/08.
 */
public class MyRequest {

    public static final String TAG = MyRequest.class.getSimpleName();

    private static MyRequest sInstance;

    public static MyRequest getsInstance() {
        if( sInstance == null) {
            sInstance = new MyRequest();
        }
        return sInstance;
    }

    public JsonObjectRequest MyJsonObjectRequest(final String category, int page,
                                                 final RequestListener.SuccesseListener successeListener,
                                                 final RequestListener.FailureListener failureListener) {
        return new JsonObjectRequest(
            // HTTPメソッド名
            Request.Method.GET,
            // リクエスト先のURL
            "https://api.dribbble.com/shots/" + category + "?page=" + page,
            // リクエストパラメータ
            null,
            // 通信成功時のリスナー
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // 通信成功時の処理
//                    Log.d("MyLog", response.toString());
                    ShotsParser.parseShotsList(response, category);
                    successeListener.onResponse(response);
                }
            },
            // 通信失敗時のリスナー
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // 通信失敗時の処理
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    failureListener.onErrorResponse(error);
                }
            }
        );
    }


}
