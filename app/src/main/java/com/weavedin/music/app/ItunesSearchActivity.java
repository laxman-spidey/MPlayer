package com.weavedin.music.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weavedin.music.app.RESTServices.ITunesService;
import com.weavedin.music.app.models.Track;

import java.util.List;

public class ItunesSearchActivity extends AppCompatActivity implements TracksFragment.OnListFragmentInteractionListener, ItunesSearchActivityFragment.OnFragmentInteractionListener {


    TracksFragment tracksFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes_search);
        tracksFragment = (TracksFragment) getSupportFragmentManager().findFragmentById(R.id.trackListFragment);
    }


    @Override
    public void onListFragmentInteraction(Track item) {

    }

    @Override
    public void onFragmentInteraction(String query) {
        getTracks(query);
    }

    public void getTracks(String query) {
        ITunesService.search(query, response -> {
            if (response.isOkay) {
                List<Track> result = (List<Track>) response.data;
                if (result != null && result.size() > 0) {
                    tracksFragment.setTracks(result);
                }
            }

        });
    }
}
