package com.weavedin.music.app.sqliteModels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.weavedin.music.app.FavoritesFragment;
import com.weavedin.music.app.TracksFragment;
import com.weavedin.music.app.models.Track;

import java.util.ArrayList;
import java.util.List;

public class FavoritesService {

    public final static String TAG = FavoritesService.class.getSimpleName();

    private static FavoritesService instance;
    private Context context;
    private TracksDBHelper helper;
    private FavoritesService(Context context) {
        this.context = context;
        helper = new TracksDBHelper(context);
    }
    public static FavoritesService getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritesService(context);
        }
        return instance;
    }
    private Context getContext() {
        return context;
    }
    public long insert(Track track) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = db.insert(TracksDBHelper.TABLE_NAME, null, TrackModel.getInsertable(track));
        db.close();
        Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
        return id;
    }

    public List<Track> getFavorites() {
        List<Track> tracks = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TracksDBHelper.TABLE_NAME ;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tracks.add(TrackModel.getTrack(cursor));
            } while (cursor.moveToNext());
        }
        Log.i(TAG, tracks.toString());
        db.close();

        return tracks;
    }

    public void delete(Track track) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = TrackModel.COLUMN_TRACK_ID +"="+track.trackId;
        db.delete(TracksDBHelper.TABLE_NAME, whereClause, null);
    }

    public boolean isFavorite(Track track) {
        boolean isFavorite = false;
        String selectQuery = "SELECT  * FROM " + TracksDBHelper.TABLE_NAME
                                + " WHERE "+ TrackModel.COLUMN_TRACK_ID +"="+track.trackId;

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0) {
            isFavorite = true;
        } else {
            isFavorite = false;
        }
        db.close();
        return isFavorite;

    }
}
