package com.weavedin.music.app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.weavedin.music.app.RESTServices.ITunesService;


public class FavoritesFragment extends Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackButtonClicked(v));

        return view;
    }

    public void onBackButtonClicked(View view) {
        getActivity().finish();
    }


}
