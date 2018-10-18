package com.liunian.androidbasic.asynctasktest;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by dell on 2018/7/28.
 */

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("liunianprint:", "run thread " + Thread.currentThread().getId());
    }
}
