package com.example.duan1_coffee.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.duan1_coffee.R;

public class Followme_Fragment extends androidx.fragment.app.Fragment {
    ImageView backinfo2;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_followme, container, false);
        //Anh xa
        backinfo2=view.findViewById(R.id.backinfo2);
        webView=view.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.facebook.com/Coffee-Poly-103282821657106/?ref=page_internal");

//
//        backinfo2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Info_Fragment llf = new Info_Fragment();
//                ft.replace(R.id.fragment_container, llf);
//                ft.commit();
//            }
//        });






        return view;
    }


}
