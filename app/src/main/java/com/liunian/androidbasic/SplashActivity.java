package com.liunian.androidbasic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 启动欢迎页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.APP_STATUS = App.APP_STATUS_NORMAL; // App正常的启动，设置App的启动状态为正常启动
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        goMain();
    }

    /**
     * 去主页面
     */
    private void goMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
