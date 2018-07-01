package com.weavedin.music.app;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

    public final static String TAG = TracksRecyclerViewAdapter.class.getSimpleName();

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

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CardView cardView;
        public final ImageView albumArt;
        public final TextView trackName;
        public final TextView artistName;
        public final TextView albumName;

        //        public final TextView mContentView;
        public Track mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            cardView = view.findViewById(R.id.searchCard);
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
                    .apply(new RequestOptions().centerCrop())
                    .into(albumArt);
            cardView.setOnClickListener(v -> {
                mListener.onListFragmentInteraction(track);
                Track.selectedTrack = track;
                notifyDataSetChanged();
            });
            setSelected(track);
        }

        public void setSelected(Track track) {
            if (Track.selectedTrack != null) {
                Log.i(TAG, "selected id - " + Track.selectedTrack.trackId + " = " + track.trackId);
                if (Track.selectedTrack.trackId.equals(track.trackId)) {
                    Log.i(TAG, "Matched ---- selected id - " + Track.selectedTrack.trackId + " = " + track.trackId);
                    cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
                } else {
                    cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
                }
            }
        }
    }

    public Context getContext() {
        return context;
    }
}
