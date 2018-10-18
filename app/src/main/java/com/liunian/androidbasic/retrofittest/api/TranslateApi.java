package com.liunian.androidbasic.retrofittest.api;

import com.liunian.androidbasic.retrofittest.bean.TranslateBean;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell on 2018/9/6.
 */

public interface TranslateApi {
    @POST("ajax.php?a=fy&f=auto&t=auto")
    @FormUrlEncoded
    Call<TranslateBean> translateRequest(@Field("w") String input);

    @POST("ajax.php?a=fy&f=auto&t=auto")
    @FormUrlEncoded
    Observable<TranslateBean> translateRxRequest(@Field("w") String input);
}
