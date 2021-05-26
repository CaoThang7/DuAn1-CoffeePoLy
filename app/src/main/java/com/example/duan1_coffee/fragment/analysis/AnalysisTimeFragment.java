package com.example.duan1_coffee.fragment.analysis;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1_coffee.R;
import com.example.duan1_coffee.fragment.AnalysisFragment;
import com.example.duan1_coffee.fragment.Analytic_Fragment;
import com.example.duan1_coffee.function.Function;
import com.example.duan1_coffee.model.BillDetail;
import com.example.duan1_coffee.model.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AnalysisTimeFragment extends Fragment {
    DatePickerDialog dateFrom, dateTo;
    List<Cart> carts, carts2, carts3;


    public AnalysisTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_analysis_time, container, false);

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BillDetail");
        final Button btnFrom = (Button) view.findViewById(R.id.btnFrom);
        final Button btnTo = (Button) view.findViewById(R.id.btnTo);
        final TextView tvFrom = (TextView) view.findViewById(R.id.tvFrom);
        final TextView tvTo = (TextView) view.findViewById(R.id.tvTo);
        final TextView tvAllTotal = (TextView) view.findViewById(R.id.tvAllTotal);
        final TextView tvsumAmount = (TextView) view.findViewById(R.id.tvsumAmount);
        final TextView tvsumTotal = (TextView) view.findViewById(R.id.tvsumTotal);

        carts = new ArrayList<Cart>();
        carts2 = new ArrayList<Cart>();
        carts3 = new ArrayList<Cart>();

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                dateFrom = new DatePickerDialog(getActivity(), R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = format.format(calendar.getTime());
                                tvFrom.setText(strDate);

                            }
                        }, mYear, mMonth, mDay);
                dateFrom.show();

            }
        });

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                dateTo = new DatePickerDialog(getActivity(), R.style.TimePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = format.format(calendar.getTime());
                                tvTo.setText(strDate);

                            }
                        }, mYear, mMonth, mDay);
                dateTo.show();

            }
        });

        view.findViewById(R.id.tvAnalysisDate);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Analysis Date", Toast.LENGTH_SHORT).show();
                final String dateFromString = tvFrom.getText().toString();
                final String dateToString = tvTo.getText().toString();
                Toast.makeText(getActivity(), dateFromString + dateToString, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(dateFromString) && !TextUtils.isEmpty(dateToString)) {
                    reference.orderByChild("date").startAt(dateFromString).endAt(dateToString + " 23:59:59").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            float sum = 0;
                            carts2.clear();
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                BillDetail billDetail = postSnapshot.getValue(BillDetail.class);
                                carts = billDetail.getCarts();
                                for (int i = 0; i < carts.size(); i++) {
                                    Cart cart = carts.get(i);
                                    carts2.add(cart);
                                }
                                sum += billDetail.getTotal();
                            }
                            tvAllTotal.setText(sum + " VND");
                            Map<String, Integer> sumAmount = carts2.stream()
                                    .collect(Collectors.groupingBy(Cart::getProductName, Collectors.summingInt(Cart::getProductAmount)));

                            Map<String, Double> sumTotal = carts2.stream()
                                    .collect(Collectors.groupingBy(Cart::getProductName, Collectors.summingDouble(Cart::getTotal)));

                            tvsumAmount.setText("" + sumAmount);
                            tvsumTotal.setText("" + sumTotal);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                            // ...
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Invalid value!", Toast.LENGTH_SHORT).show();
                }
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
//                                reference.child(key).addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//                                        Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
//                                    }
//                                });

}