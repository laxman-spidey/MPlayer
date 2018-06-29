package com.weavedin.music.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryService {

    public final static String TAG = HistoryService.class.getSimpleName();
    public final static String HISTORY = "history.xml";
    public final static String HISTORY_LIST = "history";

    private static HistoryService instance;
    private Context context;

    public static HistoryService getInstance(Context context) {
        if (instance == null) {
            instance = new HistoryService(context);
        }
        return instance;
    }

    private HistoryService(Context context) {
        this.context = context;
    }

    private Context getContext() {
        return this.context;
    }

    public void insert(String query) {
        List<String> historyList = getHistory();
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        if (!historyList.contains(query)) {
            historyList.add(query);
            setProperty(HISTORY_LIST, new Gson().toJson(historyList));
        }
    }

    public List<String> getHistory() {
        String historyString = getProperty(HISTORY_LIST);
        List<String> historyList;
        if (historyString == "") {
            historyList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            historyList = new Gson().fromJson(historyString, type);
        }
        return historyList;
    }

    public void setProperty(String key, String value) {
        SharedPreferences sharedpreferences = getContext().getSharedPreferences(HISTORY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
        Log.i(TAG, key + " --- " + sharedpreferences.getString(key, ""));
    }

    public String getProperty(String key) {
        SharedPreferences sharedpreferences = getContext().getSharedPreferences(HISTORY, Context.MODE_PRIVATE);
        Log.i(TAG, key + " --- " + sharedpreferences.getString(key, ""));
        return sharedpreferences.getString(key, "");
    }

}
