package com.example.a10.guideapplication.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10.guideapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceSearchFragment extends Fragment {

    @BindView(R.id.searchResultRecyclerView)
    RecyclerView searchResultRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_search_fragment, container, false);
        ButterKnife.bind(this, view);
        /*RecyclerView.LayoutManager manager = new LinearLayoutManager(App.getContext());
        searchResultRecyclerView.setLayoutManager(manager);
        SearchPlaceAdapter adapter = new SearchPlaceAdapter();
        searchResultRecyclerView.setAdapter(adapter);*/
        return view;
    }
}
