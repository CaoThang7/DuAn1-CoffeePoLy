package com.example.duan1_coffee.fragment.analysis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.adapter.analysis.AnalysisBillAdapter;
import com.example.duan1_coffee.fragment.AnalysisFragment;
import com.example.duan1_coffee.fragment.Analytic_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.Bill;
import com.example.duan1_coffee.model.OrderState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AnalysisBillFragment extends Fragment {

    DatabaseReference referenceBill;
    RecyclerView recyclerView;
    AnalysisBillAdapter adapter;
    List<Bill> bills;
    public AnalysisBillFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_analysis_bill, container, false);

        referenceBill = FirebaseDatabase.getInstance().getReference("Bill");
        bills = new ArrayList<>();
        adapter = new AnalysisBillAdapter(getActivity(), bills);
        recyclerView = view.findViewById(R.id.rcv);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        referenceBill.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bills.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill bill = postSnapshot.getValue(Bill.class);
                    assert bill != null;
                    bills.add(bill);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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