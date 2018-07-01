package com.weavedin.music.app.RESTServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class VolleyModel {
    public Context context;
    public static String TAG = VolleyModel.class.getSimpleName();
    public static final String SERVER_PATH = "http://itunes.apple.com/";
    public static final String SEARCH = "search";




    private static RequestQueue requestQueue;

    public static RequestQueue getInstanceRequestQueue(Context context) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public Context getContext() {
        if (context == null) {
            throw new RuntimeException("Context is null, send context before getting it.");
            //            return CrafterApplication.getContext();
        }
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    protected String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    protected String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }
    public static void cancelPreviousAndAddRequestToQueue(Context context, Request request, String TAG) {
        getInstanceRequestQueue(context).cancelAll(TAG);
        request.setTag(TAG);
        getInstanceRequestQueue(context).add(request);
    }

}
