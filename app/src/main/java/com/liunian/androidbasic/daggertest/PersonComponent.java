package com.liunian.androidbasic.daggertest;

import dagger.Component;

/**
 * Created by dell on 2018/9/6.
 */
@Component(modules = {PersonMoudle.class})
public interface PersonComponent {
    void inject(DaggerTestActivity daggerTestActivity);
}
