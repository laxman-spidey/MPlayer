package com.weavedin.music.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.weavedin.music.app.RESTServices.ITunesService;
import com.weavedin.music.app.models.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItunesSearchActivityFragment extends Fragment {

    AppCompatAutoCompleteTextView searchBox;
    OnFragmentInteractionListener mListener;

    public ItunesSearchActivityFragment() {
        dummyHistory.add("Silicon Valley");
        dummyHistory.add("Evil morty");
        dummyHistory.add("Zombie");

    }

    List<String> dummyHistory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itunes_search, container, false);
        searchBox = view.findViewById(R.id.searchEditText);
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(getContext(),R.layout.history_item_view);
        historyAdapter.addAll(dummyHistory);
        searchBox.setAdapter(historyAdapter);
//        searchBox.showDropDown();
        searchBox.setOnClickListener(v->{
            searchBox.showDropDown();
        });
        ImageButton favoritesButton = view.findViewById(R.id.favoritesIcon);
        favoritesButton.setOnClickListener(v -> onFavoritesIconClicked(v));
        searchBox.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                searchItunesTracks(searchBox.getText().toString());
            }
            return false;
        });
        return view;
    }

    public void onFavoritesIconClicked(View view) {
        Intent intent = new Intent(getContext(), FavoritesActivity.class);
        startActivity(intent);
    }


    public void searchItunesTracks(String query) {
        mListener.onFragmentInteraction(query);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TracksFragment.OnListFragmentInteractionListener) {
            mListener = (ItunesSearchActivityFragment.OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String query);
    }

}
