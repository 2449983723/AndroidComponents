package com.liunian.androidbasic.appkilledtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.liunian.androidbasic.App;
import com.liunian.androidbasic.R;
import com.liunian.androidbasic.base.BaseActivity;

public class AppKilledTestActivity extends BaseActivity {
    TextView mNameTextView;

    @Override
    protected void setUpViewAndData(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_app_killed_test);

        mNameTextView = (TextView) findViewById(R.id.name);
        if (App.testString != null) {
            mNameTextView.setText(App.testString);
        } else {
            Log.i("hhhhhhhh:", "App.testString is NULL");
        }
    }
}
