package com.asmbcs.woman.Interface;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().build();  // Initialize OkHttpClient

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.31.128:8000/")  // Use your local API URL
                    .client(client)  // Set OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())  // Gson converter
                    .build();
        }
        return retrofit;
    }
}


