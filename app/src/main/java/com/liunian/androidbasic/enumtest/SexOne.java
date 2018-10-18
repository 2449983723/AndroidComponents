package com.liunian.androidbasic.enumtest;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by dell on 2018/4/19.
 */

@IntDef({SexOne.MAN, SexOne.WOMAN})
@Retention(RetentionPolicy.SOURCE)
public @interface SexOne {
    public static final int MAN = 0;
    public static final int WOMAN = 1;
}
