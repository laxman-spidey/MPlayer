package com.weavedin.music.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.weavedin.music.app.RESTServices.ITunesService;
import com.weavedin.music.app.models.Track;

import org.w3c.dom.Text;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements TracksFragment.OnListFragmentInteractionListener, SearchBarFragment.OnFragmentInteractionListener {


    TracksFragment tracksFragment;
    TextView songsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        tracksFragment = (TracksFragment) getSupportFragmentManager().findFragmentById(R.id.trackListFragment);
        songsCount = findViewById(R.id.allSongsCount);
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
                    songsCount.setText(""+result.size());
                    tracksFragment.setTracks(result);
                }
            }

        });
    }
}
