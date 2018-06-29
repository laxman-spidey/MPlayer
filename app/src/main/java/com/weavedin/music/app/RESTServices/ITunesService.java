package com.weavedin.music.app.RESTServices;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.weavedin.music.app.models.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public class ITunesService {


    public static void search(String query, ResponseListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ITunesRetrofitService.ITUNES)
                .build();
        ITunesRetrofitService service = retrofit.create(ITunesRetrofitService.class);
        Map<String, String> data = new HashMap<>();
        data.put("term", query);
        data.put("limit", String.valueOf(4));
        Call<ResponseBody> tracks = service.search(data);
        tracks.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        List<Track> tracks = new ArrayList<>();
                        String responseString = response.body().string().trim();
                        JSONObject object = new JSONObject(responseString);
                        JSONArray array =   object.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {
                            Track track = new Gson().fromJson(array.getString(i), Track.class);
                            tracks.add(track);
                        }
                        listener.onResponseRecieved(new ResponseListener.Response(true, tracks));
                    } catch (Exception e) {
                        listener.onResponseRecieved(new ResponseListener.Response(true, null));
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onResponseRecieved(new ResponseListener.Response(false, null));
            }
        });
    }

    public interface ITunesRetrofitService {
        String ITUNES = "http://itunes.apple.com/";

        @GET("search")
        Call<ResponseBody> search(@QueryMap Map<String, String> options);
    }
}
