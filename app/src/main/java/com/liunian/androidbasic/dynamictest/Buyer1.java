package com.liunian.androidbasic.dynamictest;

import android.util.Log;

/**
 * Created by dell on 2018/9/13.
 */

public class Buyer1 implements Subject {
    @Override
    public void buy(String name) {
        Log.i("liunianprint:", "代购将" + name + "东西交给我Buyer1");
    }
}
