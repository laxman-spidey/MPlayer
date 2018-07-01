package com.weavedin.music.app;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weavedin.music.app.models.Track;
import com.weavedin.music.app.sqliteModels.FavoritesService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TracksFragment extends BaseFragment {

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
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_track_list, container, false);

        setSuccessView(view);
        setInitViewText("Search for your Favorite Songs");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new TracksRecyclerViewAdapter(tracks, getContext(), mListener);
            recyclerView.setAdapter(adapter);
            if (tracks.size() > 0) {
                onSuccess();
            }
        }
        return rootView;
    }


    public void setSwipeToDelete() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
                int position = viewHolder.getAdapterPosition();
                FavoritesService.getInstance(getContext()).delete(tracks.get(position));
                tracks.remove(position);
                adapter.notifyDataSetChanged();
                if (tracks.size() == 0) {
                    onNoDataFound();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }
    public void setTracks(List<Track> tracks) {
        this.tracks.clear();
        this.tracks.addAll(tracks);
        this.adapter.notifyDataSetChanged();
        if (tracks.size() > 0) {
            onSuccess();
        } else {
            onNoDataFound();
        }

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
