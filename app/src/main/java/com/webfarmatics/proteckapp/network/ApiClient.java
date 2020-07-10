package com.webfarmatics.proteckapp.network;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    private static final String BASE_URL = "http://155.12.30.57/proteckservice/";       //LIVE

    private static final String BASE_URL11 = "http://188.95.36.104:8080/ProteckCenter/";         //STAGING

  // private static final String BASE_URL11 = "http://192.168.0.14:8081/ProteckServiceCenter/";      //LOCALHOST

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL11)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }

        return retrofit;

    }


    private static OkHttpClient getOkHttpClient(){

        return new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

    }


    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


}
