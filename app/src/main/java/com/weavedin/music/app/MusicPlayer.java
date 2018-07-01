package com.weavedin.music.app;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.weavedin.music.app.models.Track;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MusicPlayer {

    public final static String TAG = MusicPlayer.class.getSimpleName();


    private MediaPlayer mediaPlayer;
    private static MusicPlayer instance = null;

    private MusicPlayer() {
        this.mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setOnBufferingUpdateListener();
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public String getDuration() {

        long duration = mediaPlayer.getDuration();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        return minutes + ":" + seconds;
    }

    public void setOnBufferingUpdateListener() {
        mediaPlayer.setOnBufferingUpdateListener((mp, percent) -> Log.i(TAG, "buffering - " + percent));
    }

    public void startPlaying(Track track) {

        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            this.mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            setOnBufferingUpdateListener();
            mediaPlayer.setDataSource(track.previewUrl);
            mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
        mediaPlayer.setOnPreparedListener(listener);
    }


    public void seekTo(int progress, boolean fromUser) {
        if (mediaPlayer != null && fromUser) {
            mediaPlayer.seekTo(progress * 1000);
        }
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition() / 1000;
        } else {
            return 0;
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }
}
