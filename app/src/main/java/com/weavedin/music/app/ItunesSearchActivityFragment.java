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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItunesSearchActivityFragment extends Fragment {

    AppCompatAutoCompleteTextView searchBox;
    ArrayAdapter<String> historyAdapter;
    OnFragmentInteractionListener mListener;

    public ItunesSearchActivityFragment() {

    }

    List<String> searchHistory = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itunes_search, container, false);
        searchBox = view.findViewById(R.id.searchEditText);

        initializeHistory();

        ImageButton favoritesButton = view.findViewById(R.id.favoritesIcon);
        favoritesButton.setOnClickListener(v -> onFavoritesIconClicked(v));

        //When user presses enter in SearchBox, send query to activity
        // and store it using history service
        searchBox.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                String query = searchBox.getText().toString();
                searchItunesTracks(query);
                historyAdapter.add(query);
                HistoryService.getInstance(getContext()).insert(query);
            }
            return false;
        });
        return view;
    }

    public void onFavoritesIconClicked(View view) {
        Intent intent = new Intent(getContext(), FavoritesActivity.class);
        startActivity(intent);
    }

    public void initializeHistory() {

        historyAdapter = new ArrayAdapter<>(getContext(), R.layout.history_item_view);
        historyAdapter.addAll(HistoryService.getInstance(getContext()).getHistory());
        historyAdapter.addAll(searchHistory);
        searchBox.setAdapter(historyAdapter);
        searchBox.setOnClickListener(v-> searchBox.showDropDown());
        searchBox.setOnItemClickListener((parent, view, position, id) -> {
            String query = (String)parent.getItemAtPosition(position);
            searchItunesTracks(query);
        });
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
