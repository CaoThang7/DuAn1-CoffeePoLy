package com.example.duan1_coffee.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientChat {
    private static Retrofit retrofit =null;
    private static Retrofit getClientChat(String url){
        if(retrofit ==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
