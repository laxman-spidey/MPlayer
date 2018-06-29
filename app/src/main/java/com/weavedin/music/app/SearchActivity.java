package com.weavedin.music.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.weavedin.music.app.RESTServices.ITunesService;
import com.weavedin.music.app.models.Track;
import com.weavedin.music.app.sqliteModels.FavoritesService;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TracksFragment.OnListFragmentInteractionListener, SearchBarFragment.OnFragmentInteractionListener {
    public final static  String TAG = SearchActivity.class.getSimpleName();
    TextView songsCount;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private List<TracksFragment> tracksFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        tracksFragment = (TracksFragment) getSupportFragmentManager().findFragmentById(R.id.trackListFragment);
        songsCount = findViewById(R.id.allSongsCount);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), tracksFragments);
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tracksFragments.add(new TracksFragment());
        mSectionsPagerAdapter.notifyDataSetChanged();
        getTracks("hello");
    }


    @Override
    public void onListFragmentInteraction(Track item) {
//        FavoritesService.getInstance(getContext()).insert(item);
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.TAG_TRACK, item.toString());
        startActivity(intent);
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
                    songsCount.setText("" + result.size());
                    tracksFragments.get(0).setTracks(result);
                    tracksFragments.get(0).getVisibleCount(visibleCount -> {
                        paginateTracks(result, visibleCount);
                    });

                }
            }
        });
    }


    public void paginateTracks(List<Track> tracks, int visibleCount) {
        Log.i(TAG, "Paginating tracks");
        tracksFragments.clear();
        List<List<Track>> trackPages = new ArrayList<>();
        int index = 0;
        List<Track> page = null;
        for (Track  track : tracks) {
            if (index == 0 ||  index % visibleCount== 0) {
                page = new ArrayList<>();
                trackPages.add(page);
            }
            page.add(track);
            index++;
        }

        for (List<Track> tracksPage : trackPages) {
            TracksFragment pageFragment = TracksFragment.newInstance(tracksPage);
            tracksFragments.add(pageFragment);
        }
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm, List<TracksFragment> fragments) {
            super(fm);
            mFragmentList = fragments;
        }


        private final List<TracksFragment> mFragmentList;


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(TracksFragment fragment) {
            mFragmentList.add(fragment);
        }
    }

    public Context getContext() {
        return this;
    }
}
