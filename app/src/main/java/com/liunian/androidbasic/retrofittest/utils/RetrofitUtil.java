package com.liunian.androidbasic.retrofittest.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dell on 2018/9/6.
 */

public class RetrofitUtil {
    public static Retrofit INSTANCE;

    public static Retrofit getRetrofitInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Retrofit.Builder()
                            .baseUrl("http://fy.iciba.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
