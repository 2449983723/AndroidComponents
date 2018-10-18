package com.liunian.androidbasic.okhttptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.liunian.androidbasic.R;
import com.liunian.androidbasic.okhttptest.utils.OkHttpUtils;

import java.io.IOException;

public class OkHttpTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);

        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    private void request() {
        OkHttpUtils.sendGetRequest("https://www.baidu.com", new OkHttpUtils.OkHttpCallback() {
            @Override
            public void onFailure(IOException e) {

            }

            @Override
            public void onResponse(String responseString) {
                Log.i("liunianprint:", responseString);
            }
        });

    }
}
