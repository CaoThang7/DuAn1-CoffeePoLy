package com.example.duan1_coffee.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.example.duan1_coffee.R;

public class ViewFragment extends Fragment {
    WebView webview;
    public String link;

    public ViewFragment() {
        // Required empty public constructor
    }
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflatethe layou in fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);

        webview =view.findViewById(R.id.webviewTinTuc);

        String link = getArguments().getString("link");
        webview.loadUrl(link);
        webview.setWebViewClient(new WebViewClient());
        return view;



    }
}