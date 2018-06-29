package com.weavedin.music.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        EditText searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.setFocusable(false);
        CardView searchCard = view.findViewById(R.id.searchCard);
        searchEditText.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });
        searchCard.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
