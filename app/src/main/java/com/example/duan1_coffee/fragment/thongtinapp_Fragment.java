package com.example.duan1_coffee.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_coffee.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class thongtinapp_Fragment extends androidx.fragment.app.Fragment {
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_thongtinapp, container, false);
        collapsingToolbarLayout=view.findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.DKGRAY);




        return view;
    }
}
