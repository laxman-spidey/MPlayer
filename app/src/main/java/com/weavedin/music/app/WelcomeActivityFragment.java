package com.weavedin.music.app;

import android.content.Intent;
import android.os.Handler;
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
public class WelcomeActivityFragment extends Fragment {

    public WelcomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);


        EditText searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.setFocusable(false);
        CardView searchCard = view.findViewById(R.id.searchCard);
        int SPLASH_TIMEOUT_SHORT = 2000;
        new Handler().postDelayed(() -> {
            view.findViewById(R.id.searchBarInclude).setVisibility(View.VISIBLE);
            view.findViewById(R.id.welcomeText).setVisibility(View.VISIBLE);
        }, SPLASH_TIMEOUT_SHORT);

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
