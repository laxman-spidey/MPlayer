package com.weavedin.music.app.models;

import com.google.gson.Gson;

public class Track {
    public String trackId;
    public String kind;
    public String trackName;
    public String artistName;
    public String artworkUrl30;
    public String artworkUrl60;
    public String artworkUrl100;
    public String collectionName;
    public String trackTimeMillis;
    public String previewUrl;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static Track selectedTrack;
}
