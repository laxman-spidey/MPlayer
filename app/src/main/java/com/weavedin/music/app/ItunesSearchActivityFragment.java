package com.weavedin.music.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItunesSearchActivityFragment extends Fragment {

    AppCompatAutoCompleteTextView searchBox;

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
        return view;
    }
}
