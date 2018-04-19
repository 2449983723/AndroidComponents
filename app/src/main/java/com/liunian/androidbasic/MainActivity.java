package com.liunian.androidbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liunian.androidbasic.addbigimage.AddBigImageActivity;
import com.liunian.androidbasic.appkilledtest.AppKilledTestActivity;
import com.liunian.androidbasic.base.BaseActivity;
import com.liunian.androidbasic.enumtest.SexOne;
import com.liunian.androidbasic.servicetest.ServiceTestActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    Button mAddBigPic;
    Button mAppKillTest;
    Button mSeviceButton;
    @Override
    protected void setUpViewAndData(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        App.testString = "张三";

        mAddBigPic = (Button) findViewById(R.id.addBigPic);
        mAddBigPic.setOnClickListener(this);
        mAppKillTest = (Button) findViewById(R.id.app_killed_test);
        mAppKillTest.setOnClickListener(this);
        mSeviceButton = (Button) findViewById(R.id.service_test);
        mSeviceButton.setOnClickListener(this);
        setSex(SexOne.WOMAN);
    }

    public void setSex(@SexOne int sex) {

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
                case R.id.service_test:
                    intent.setClass(MainActivity.this, ServiceTestActivity.class);
                    break;
            }
            if (intent != null) {
                this.startActivity(intent);
            }
        }
    }
}
