package com.weavedin.music.app.RESTServices;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.weavedin.music.app.models.Track;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItunesVolleyService extends VolleyModel {

    public static int MAX_TRACK_COUNT = 50;

    public void search(String query, ResponseListener listener) {
        Map<String, String> data = new HashMap<>();
        data.put("term", query);
        data.put("limit", String.valueOf(MAX_TRACK_COUNT));

        String url = SERVER_PATH + SEARCH + "?" + urlEncodeUTF8(data);
        StringRequest jsonObjectRequest = new StringRequest
                (Request.Method.GET, url, response -> {
                    try {
                        List<Track> tracks = new ArrayList<>();
                        String responseString = response.trim();
                        JSONObject object = new JSONObject(responseString);
                        JSONArray array = object.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            Track track = new Gson().fromJson(array.getString(i), Track.class);
                            tracks.add(track);
                        }
                        for (Track track : tracks) {
                            Log.i("tag", track.trackId);
                        }
                        listener.onResponseRecieved(new ResponseListener.Response(true, tracks));
                    } catch (Exception e) {
                        listener.onResponseRecieved(new ResponseListener.Response(true, null));
                        e.printStackTrace();
                    }
                }, error ->
                {
                    error.printStackTrace();
                    listener.onResponseRecieved(new ResponseListener.Response(false, null));
                });

        Log.i(TAG, jsonObjectRequest.toString());
        cancelPreviousAndAddRequestToQueue(getContext(), jsonObjectRequest, SEARCH);
    }

    private static ItunesVolleyService singleton = null;

    public static ItunesVolleyService getInstance(Context context) {
        if (singleton == null) {
            singleton = new ItunesVolleyService(context);
        }
        return singleton;
    }

    public static String TAG = ItunesVolleyService.class.getSimpleName();

    public ItunesVolleyService(Context context) {
        setContext(context);
    }
}
