package com.weavedin.music.app.sqliteModels;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TracksDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Tracks.db";

    public static final String TABLE_NAME  = "favorites";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + TrackModel.COLUMN_TRACK_ID + " TEXT PRIMARY KEY,"
                    + TrackModel.COLUMN_KIND + " TEXT,"
                    + TrackModel.COLUMN_TRACK_NAME + " TEXT,"
                    + TrackModel.COLUMN_ARTIST_NAME + " TEXT,"
                    + TrackModel.COLUMN_ARTWORK_URL_30 + " TEXT,"
                    + TrackModel.COLUMN_ARTWORK_URL_60 + " TEXT,"
                    + TrackModel.COLUMN_ARTWORK_URL_100 + " TEXT,"
                    + TrackModel.COLUMN_COLLECTION_NAME + " TEXT,"
                    + TrackModel.COLUMN_TRACK_TIME_MILLIS + " TEXT,"
                    + TrackModel.COLUMN_PREVIEW_URL + " TEXT"
                    + ")";

    public TracksDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
