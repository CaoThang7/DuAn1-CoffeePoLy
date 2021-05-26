package com.example.duan1_coffee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.analysis.AnalysisBillFragment;
import com.example.duan1_coffee.fragment.analysis.AnalysisProductFragment;
import com.example.duan1_coffee.fragment.analysis.AnalysisReviewFragment;
import com.example.duan1_coffee.fragment.analysis.AnalysisTimeFragment;
import com.example.duan1_coffee.function.Function;


public class AnalysisFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        view.findViewById(R.id.tvAllBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AnalysisBillFragment(), R.id.frameLayout, view);
            }
        });

        view.findViewById(R.id.tvAnalysisForProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AnalysisProductFragment(), R.id.frameLayout, view);
            }
        });

        view.findViewById(R.id.tvAnalysisForTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AnalysisTimeFragment(), R.id.frameLayout, view);

            }
        });

        view.findViewById(R.id.tvAnalysisForReview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AnalysisReviewFragment(), R.id.frameLayout, view);

            }
        });

        return view;
    }
}