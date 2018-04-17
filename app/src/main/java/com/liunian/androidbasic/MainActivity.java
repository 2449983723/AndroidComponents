package com.liunian.androidbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.liunian.androidbasic.addbigimage.AddBigImageActivity;
import com.liunian.androidbasic.appkilledtest.AppKilledTestActivity;
import com.liunian.androidbasic.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    Button mAddBigPic;
    Button mAppKillTest;

    @Override
    protected void setUpViewAndData(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        mAddBigPic = (Button) findViewById(R.id.addBigPic);
        mAddBigPic.setOnClickListener(this);
        mAppKillTest = (Button) findViewById(R.id.app_killed_test);
        mAppKillTest.setOnClickListener(this);

        App.testString = "张三";
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            int resId = view.getId();
            Intent intent = new Intent();
            switch (resId) {
                case R.id.addBigPic:
                    intent.setClass(MainActivity.this, AddBigImageActivity.class);
                    break;
                case R.id.app_killed_test:
                    intent.setClass(MainActivity.this, AppKilledTestActivity.class);
                    break;
            }
            if (intent != null) {
                this.startActivity(intent);
            }
        }
    }
}
