package com.weavedin.music.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.weavedin.music.app.RESTServices.ITunesService;
import com.weavedin.music.app.dummy.DummyContent;
import com.weavedin.music.app.models.Track;

public class ItunesSearchActivity extends AppCompatActivity implements TracksFragment.OnListFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itunes_search);

    }


    @Override
    public void onListFragmentInteraction(Track item) {

    }
}
