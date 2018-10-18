package com.liunian.androidbasic.daggertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liunian.androidbasic.R;

import javax.inject.Inject;

public class DaggerTestActivity extends AppCompatActivity {
    @Inject
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test);

        DaggerPersonComponent.builder().personMoudle(new PersonMoudle()).build().inject(this);
    }
}
