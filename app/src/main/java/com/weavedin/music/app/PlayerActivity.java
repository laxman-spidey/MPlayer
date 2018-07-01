package com.weavedin.music.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.weavedin.music.app.models.Track;
import com.weavedin.music.app.sqliteModels.FavoritesService;

public class PlayerActivity extends AppCompatActivity {

    public final static String TAG = PlayerActivity.class.getSimpleName();
    public final static String TAG_TRACK = "TRACK";

    Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getTrackFromIntent();
        setupToolbar();
        setAlbumArt();
        setTrackInfo();
        setupFavoritesButton();
        setupMediaPlayer();
        setupPlayerControls();
        setupPlaylistButton();
        setupSeekBar();


    }

    private void getTrackFromIntent() {
        String trackJson = getIntent().getStringExtra(TAG_TRACK);
        track = new Gson().fromJson(trackJson, Track.class);
        Track.selectedTrack = track;
    }


    private void setupMediaPlayer() {
        MusicPlayer.getInstance().startPlaying(track);
        MusicPlayer.getInstance().setOnPreparedListener(mp -> {
            MusicPlayer.getInstance().start();
            trackEndTime.setText(MusicPlayer.getInstance().getDuration());
        });
    }

    SeekBar seekBar;

    private void setupSeekBar() {
        seekBar = findViewById(R.id.seekBar);
        Handler mHandler = new Handler();
        //Make sure you update Seekbar on UI thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(MusicPlayer.getInstance().getCurrentPosition());
                trackEndTime.setText(MusicPlayer.getInstance().getRemainingDuration());
                mHandler.postDelayed(this, 1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MusicPlayer.getInstance().seekTo(progress, fromUser);
            }
        });
    }


    ImageView albumArt;

    private void setAlbumArt() {
        albumArt = findViewById(R.id.albumArt);

        Glide.with(getContext())
                .load(track.artworkUrl100)
                .apply(new RequestOptions().centerCrop())
                .thumbnail(Glide.with(getContext()).load(track.artworkUrl30))
                .into(albumArt);
    }


    TextView trackTitle;
    TextView trackAlbum;
    TextView trackArtist;
    TextView trackEndTime;

    private void setTrackInfo() {
        trackTitle = findViewById(R.id.trackTitle);
        trackTitle.setText(track.trackName);

        trackAlbum = findViewById(R.id.trackAlbum);
        trackAlbum.setText(track.collectionName);

        trackArtist = findViewById(R.id.trackArtist);
        trackArtist.setText(track.artistName);

        trackEndTime = findViewById(R.id.trackEndTime);

    }

    private TextView activityTitle;
    private ImageButton backButton;
    private ImageView favoritesButton;

    private void setupToolbar() {
        activityTitle = findViewById(R.id.title);
        activityTitle.setText(track.trackName);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        favoritesButton = findViewById(R.id.favoritesIcon);
        favoritesButton.setOnClickListener(v -> startActivity(new Intent(getContext(), FavoritesActivity.class)));
    }

    private ImageButton playPauseButton;

    private void setupPlayerControls() {
        playPauseButton = findViewById(R.id.playPauseButton);
        playPauseButton.setOnClickListener(v -> {
            if (MusicPlayer.getInstance().isPlaying()) {
                MusicPlayer.getInstance().pause();
                playPauseButton.setImageResource(R.mipmap.play);
            } else {
                MusicPlayer.getInstance().start();
                playPauseButton.setImageResource(R.mipmap.pause);
            }
        });

    }

    private boolean isFavorite;
    private ImageButton toggleFavouriteButton;
    private void setupFavoritesButton() {
        toggleFavouriteButton = findViewById(R.id.toggleFavouriteButton);
        isFavorite = FavoritesService.getInstance(getContext()).isFavorite(track);

        setFavoriteImage(isFavorite);
        toggleFavouriteButton.setOnClickListener((view) -> {
            isFavorite = !isFavorite;
            if (isFavorite) {
                FavoritesService.getInstance(getContext()).insert(track);
            } else {
                FavoritesService.getInstance(getContext()).delete(track);
            }
            setFavoriteImage(isFavorite);
        });
    }

    private void setFavoriteImage(boolean isFavorite) {
        if (isFavorite) {
            toggleFavouriteButton.setImageResource(R.drawable.ic_favorite_selected);
        } else {
            toggleFavouriteButton.setImageResource(R.drawable.ic_favorite_unselected);
        }
    }

    private void setupPlaylistButton() {
        ImageButton playlistButton = findViewById(R.id.playListButton);
        playlistButton.setOnClickListener(v-> {
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isFavorite = FavoritesService.getInstance(getContext()).isFavorite(track);
        setFavoriteImage(isFavorite);
    }

    private Context getContext() {
        return this;
    }
}
