package com.example.duan1_coffee.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_coffee.R;

public class Info_Fragment extends androidx.fragment.app.Fragment {
    ImageView ggMap,callphone,chat,followme;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info, container, false);
        ggMap=view.findViewById(R.id.ggMap);
        callphone=view.findViewById(R.id.callphone);
        chat=view.findViewById(R.id.chat);
        followme=view.findViewById(R.id.followme);



        ggMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GoogleMap_Fragment llf = new GoogleMap_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });

        callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Callphone_Fragment llf = new Callphone_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                chat_Fragment llf = new chat_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });
        followme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Followme_Fragment llf = new Followme_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });




        return view;
    }
}
