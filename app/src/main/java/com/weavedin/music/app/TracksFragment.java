package com.weavedin.music.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weavedin.music.app.models.Track;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TracksFragment extends Fragment {

    public final static String TAG = TracksFragment.class.getSimpleName();



    private static final String ARG_TRACKS = "tracks";
    private OnListFragmentInteractionListener mListener;

    private List<Track> tracks = new ArrayList<>();
    RecyclerView recyclerView;
    TracksRecyclerViewAdapter adapter;

    public TracksFragment() {

    }

    public static TracksFragment newInstance(List<Track> tracks) {
        TracksFragment fragment = new TracksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRACKS, new Gson().toJson(tracks));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String tracksString = getArguments().getString(ARG_TRACKS);
            Type type = new TypeToken<List<Track>>() {
            }.getType();
            tracks = new Gson().fromJson(tracksString, type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }

            adapter = new TracksRecyclerViewAdapter(tracks, getContext(), mListener);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks.clear();
        this.tracks.addAll(tracks);
        this.adapter.notifyDataSetChanged();

    }

    public void getVisibleCount(onListRenderCompleted listener) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                int visibleCount = layoutManager.findLastVisibleItemPosition();
                Log.i(TAG, "visibleCount= " + visibleCount);
                listener.getVisibleItemCount(visibleCount);
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Track item);
    }

    public interface onListRenderCompleted {
        void getVisibleItemCount(int visibleCount);
    }
}
