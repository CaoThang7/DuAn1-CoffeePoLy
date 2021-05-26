package com.example.duan1_coffee.fragment.analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.AnalysisFragment;
import com.example.duan1_coffee.fragment.Analytic_Fragment;
import com.example.duan1_coffee.function.Function;


public class AnalysisReviewFragment extends Fragment {


    public AnalysisReviewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_analysis_review, container, false);

        view.findViewById(R.id.tvBackToAnalysis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new AnalysisFragment(), R.id.frameLayout, view);
            }
        });

        view.findViewById(R.id.tvChatWithUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Function().loadFragment(new Analytic_Fragment(), R.id.frameLayout, view);
            }
        });

        return view;
    }
}