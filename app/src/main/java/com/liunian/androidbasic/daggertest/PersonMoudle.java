package com.liunian.androidbasic.daggertest;

import android.util.Log;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 2018/9/6.
 */
@Module
public class PersonMoudle {
    @Provides
    Person providesPerson() {
        Log.i("liunianprint:", "PersonMoudle providesPerson");
        return new Person();
    }
}
