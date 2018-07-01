package com.weavedin.music.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weavedin.music.app.RESTServices.ITunesService;
import com.weavedin.music.app.RESTServices.ItunesVolleyService;
import com.weavedin.music.app.models.Track;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TracksFragment.OnListFragmentInteractionListener, SearchBarFragment.OnFragmentInteractionListener {
    public final static String TAG = SearchActivity.class.getSimpleName();
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
        setupSlider();
//        getTracks("hello");
    }


    private SliderIndicatorView sliderLayout;

    private void setupSlider() {
        sliderLayout = findViewById(R.id.sliderIndicatorsLayout);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sliderLayout.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onListFragmentInteraction(Track item) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.TAG_TRACK, item.toString());
        for (TracksFragment fragment : tracksFragments) {
            if (fragment.adapter != null) {
                fragment.adapter.notifyDataSetChanged();
            }
        }
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(String query) {
        getTracks(query);
    }

    TracksFragment firstFragment;

    public void getTracks(String query) {

        mViewPager.setCurrentItem(0);
        firstFragment = tracksFragments.get(0);
        firstFragment.inProgress();
        ITunesService.search(query, response -> {
            if (response.isOkay) {
                List<Track> result = (List<Track>) response.data;
                if (result != null && result.size() > 0) {
                    songsCount.setText("" + result.size());
                    firstFragment.onSuccess();
                    firstFragment.setTracks(result);
                    firstFragment.getVisibleCount(visibleCount -> {
                        paginateTracks(result, visibleCount);
                    });
                }
            } else {
                if (response.data == null) {
                    firstFragment.onError();
                } else {
                    firstFragment.onError((String) response.data);
                }
            }
        });
    }

    public void removeAllFragments() {
        //skip first fragment

        for (int i = tracksFragments.size()-1; i > 0; i--) {
            mSectionsPagerAdapter.removeFragment(i, mViewPager);
        }
    }

    public void paginateTracks(List<Track> tracks, int visibleCount) {

        removeAllFragments();
        List<List<Track>> trackPages = new ArrayList<>();
        int index = 0;
        List<Track> page = null;
        for (Track track : tracks) {
            if (index == 0 || index % visibleCount == 0) {
                page = new ArrayList<>();
                trackPages.add(page);
            }
            page.add(track);
            index++;
        }


        sliderLayout.clearIndicators();
        firstFragment.setTracks(trackPages.get(0));
        trackPages.remove(0);
        for (List<Track> tracksPage : trackPages) {
            TracksFragment pageFragment = TracksFragment.newInstance(tracksPage);
            tracksFragments.add(pageFragment);
            sliderLayout.addIndicator();
        }
        sliderLayout.setSelected(0);
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
            Log.i(TAG, "fragments count --- " + mFragmentList.size());
            return mFragmentList.size();
        }


        public void removeFragment(TracksFragment fragment) {

            mFragmentList.remove(fragment);
            notifyDataSetChanged();
        }

        public void removeFragment(int position, ViewGroup container) {
            super.destroyItem(null, position, mFragmentList.get(position));
            mFragmentList.remove(position);
            notifyDataSetChanged();
        }


    }

    public Context getContext() {
        return this;
    }
}
