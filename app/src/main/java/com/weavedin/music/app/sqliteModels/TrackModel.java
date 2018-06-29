package com.weavedin.music.app.sqliteModels;

import android.content.ContentValues;
import android.database.Cursor;

import com.weavedin.music.app.models.Track;

public class TrackModel {
    public static final String COLUMN_TRACK_ID = "trackId";
    public static final String COLUMN_KIND = "kind";
    public static final String COLUMN_TRACK_NAME = "trackName";
    public static final String COLUMN_ARTIST_NAME = "artistName";
    public static final String COLUMN_ARTWORK_URL_30 = "artworkUrl30";
    public static final String COLUMN_ARTWORK_URL_60 = "artworkUrl60";
    public static final String COLUMN_ARTWORK_URL_100 = "artworkUrl100";
    public static final String COLUMN_COLLECTION_NAME = "collectionName";
    public static final String COLUMN_TRACK_TIME_MILLIS = "trackTimeMillis";

    public static ContentValues getInsertable(Track track) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRACK_ID, track.trackId);
        values.put(COLUMN_KIND, track.kind);
        values.put(COLUMN_TRACK_NAME, track.trackName);
        values.put(COLUMN_ARTIST_NAME, track.artistName);
        values.put(COLUMN_ARTWORK_URL_30, track.artworkUrl30);
        values.put(COLUMN_ARTWORK_URL_60, track.artworkUrl60);
        values.put(COLUMN_ARTWORK_URL_100, track.artworkUrl100);
        values.put(COLUMN_COLLECTION_NAME, track.collectionName);
        values.put(COLUMN_TRACK_TIME_MILLIS, track.trackTimeMillis);

        return values;
    }

    public static Track getTrack(Cursor cursor) {
        Track track = new Track();
        track.trackId = cursor.getString(cursor.getColumnIndex(COLUMN_TRACK_ID));
        track.kind = cursor.getString(cursor.getColumnIndex(COLUMN_KIND));
        track.trackName = cursor.getString(cursor.getColumnIndex(COLUMN_TRACK_NAME));
        track.artistName = cursor.getString(cursor.getColumnIndex(COLUMN_ARTIST_NAME));
        track.artworkUrl30 = cursor.getString(cursor.getColumnIndex(COLUMN_ARTWORK_URL_30));
        track.artworkUrl60 = cursor.getString(cursor.getColumnIndex(COLUMN_ARTWORK_URL_60));
        track.artworkUrl100 = cursor.getString(cursor.getColumnIndex(COLUMN_ARTWORK_URL_100));
        track.collectionName = cursor.getString(cursor.getColumnIndex(COLUMN_COLLECTION_NAME));
        track.trackTimeMillis = cursor.getString(cursor.getColumnIndex(COLUMN_TRACK_TIME_MILLIS));

        return track;
    }
}
