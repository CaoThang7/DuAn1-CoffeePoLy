package com.example.duan1_coffee.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_coffee.R;

public class Callphone_Fragment extends androidx.fragment.app.Fragment {
    ImageView backinfo2;
    TextView tvNumber,tvNumber2,tvNumber3;
    ImageButton btn_call,btn_call2,btn_call3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_callphone, container, false);
        //Anh xa
        backinfo2 = view.findViewById(R.id.backinfo2);
        tvNumber = view.findViewById(R.id.tv_number);
        btn_call = view.findViewById(R.id.btn_call);
        tvNumber2 = view.findViewById(R.id.tv_number2);
        btn_call2 = view.findViewById(R.id.btn_call2);
        tvNumber3 = view.findViewById(R.id.tv_number3);
        btn_call3 = view.findViewById(R.id.btn_call3);


        backinfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Info_Fragment llf = new Info_Fragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });


        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=tvNumber.getText().toString();
                if(phone.length()<10){
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }else{
                    String s= "tel:" + phone;
                    Intent i=new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(s));
                    startActivity(i);
                }
            }
        });

        btn_call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=tvNumber2.getText().toString();
                if(phone.length()<10){
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }else{
                    String s= "tel:" + phone;
                    Intent i=new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(s));
                    startActivity(i);
                }
            }
        });

        btn_call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=tvNumber3.getText().toString();
                if(phone.length()<10){
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }else{
                    String s= "tel:" + phone;
                    Intent i=new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(s));
                    startActivity(i);
                }
            }
        });




        return view;
    }




}
