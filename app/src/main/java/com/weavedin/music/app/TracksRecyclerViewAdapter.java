package com.weavedin.music.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weavedin.music.app.TracksFragment.OnListFragmentInteractionListener;
import com.weavedin.music.app.dummy.DummyContent.DummyItem;
import com.weavedin.music.app.models.Track;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TracksRecyclerViewAdapter extends RecyclerView.Adapter<TracksRecyclerViewAdapter.ViewHolder> {

    private final List<Track> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public TracksRecyclerViewAdapter(List<Track> items, Context context, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.setData(mValues.get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("TAG", "track size - " + mValues.size());
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView albumArt;
        public final TextView trackName;
        public final TextView artistName;
        public final TextView albumName;

        //        public final TextView mContentView;
        public Track mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            albumArt = view.findViewById(R.id.albumArt);
            trackName = view.findViewById(R.id.trackName);
            artistName = view.findViewById(R.id.artistName);
            albumName = view.findViewById(R.id.albumName);
        }

        public void setData(Track track) {

            albumName.setText(track.collectionName);
            trackName.setText(track.trackName);
            artistName.setText(track.artistName);
            Glide.with(getContext())
                    .load(track.artworkUrl60)
//                    .centerCrop()
//                    .placeholder(R.drawable.loading_spinner)
                    .into(albumArt);

        }
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }

    public Context getContext() {
        return context;
    }
}
