package com.weavedin.music.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.weavedin.music.app.models.Track;
import com.weavedin.music.app.sqliteModels.FavoritesService;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements TracksFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackButtonClicked(v));
        getFavorites();
    }

    public void onBackButtonClicked(View view) {
        finish();
    }

    public void getFavorites() {

        List<Track> tracks = FavoritesService.getInstance(getContext()).getFavorites();
        TracksFragment fragment = (TracksFragment) getSupportFragmentManager().findFragmentById(R.id.favoritesListFragment);
        fragment.setTracks(tracks);
        fragment.setSwipeToDelete();
    }

    private Context getContext() {
        return this;
    }

    @Override
    public void onListFragmentInteraction(Track item) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.TAG_TRACK, item.toString());
        startActivity(intent);

    }
}

