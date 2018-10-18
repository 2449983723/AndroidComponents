package com.liunian.androidbasic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.liunian.androidbasic.addbigimage.AddBigImageActivity;
import com.liunian.androidbasic.animation.AnimationActivity;
import com.liunian.androidbasic.appkilledtest.AppKilledTestActivity;
import com.liunian.androidbasic.asynctasktest.AsyncTaskTestActivity;
import com.liunian.androidbasic.base.BaseActivity;
import com.liunian.androidbasic.coinvedio.CoinVideoActivity;
import com.liunian.androidbasic.compass.CompassActivity;
import com.liunian.androidbasic.daggertest.DaggerTestActivity;
import com.liunian.androidbasic.decibe.DecibelActivity;
import com.liunian.androidbasic.dynamictest.DynamicTestActivity;
import com.liunian.androidbasic.handlertest.HandlerTestActivity;
import com.liunian.androidbasic.httpclienttest.HttpClientTestActivity;
import com.liunian.androidbasic.okhttptest.OkHttpTestActivity;
import com.liunian.androidbasic.retrofittest.RetrofitTestActivity;
import com.liunian.androidbasic.ruler.RulerActivity;
import com.liunian.androidbasic.rxjavatest.RxjavaTestActivity;
import com.liunian.androidbasic.servicetest.ServiceTestActivity;
import com.liunian.androidbasic.sockettest.SocketTestActivity;
import com.liunian.androidbasic.sort.SortActivity;
import com.liunian.androidbasic.surfacetest.SurfaceViewActivity;
import com.liunian.androidbasic.volleytest.VolleyTestActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    Button mAddBigPic;
    Button mAppKillTest;
    Button mSeviceButton;
    Button mHandlerTestButton;
    Button mAsyncTaskTestButton;
    Button mHttpClientTestButton;
    Button mSocketTestButton;
    Button mVolleyTestButton;
    Button mSortTestButton;
    Button mDaggerTestButton;

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
        mHandlerTestButton = (Button) findViewById(R.id.handler_test);
        mHandlerTestButton.setOnClickListener(this);
        mAsyncTaskTestButton = (Button) findViewById(R.id.async_task_test);
        mAsyncTaskTestButton.setOnClickListener(this);
        mHttpClientTestButton = (Button) findViewById(R.id.http_client_test);
        mHttpClientTestButton.setOnClickListener(this);
        mSocketTestButton = (Button) findViewById(R.id.socket_test);
        mSocketTestButton.setOnClickListener(this);
        mVolleyTestButton = (Button) findViewById(R.id.volley_test);
        mVolleyTestButton.setOnClickListener(this);
        mSortTestButton = (Button) findViewById(R.id.sort_test);
        mSortTestButton.setOnClickListener(this);
        mDaggerTestButton = (Button) findViewById(R.id.dagger_test);
        mDaggerTestButton.setOnClickListener(this);
        findViewById(R.id.retrofit_test).setOnClickListener(this);
        findViewById(R.id.okhttp_test).setOnClickListener(this);
        findViewById(R.id.rx_java_test).setOnClickListener(this);
        findViewById(R.id.dynamic_test).setOnClickListener(this);
        findViewById(R.id.compass_test).setOnClickListener(this);
        findViewById(R.id.ruler_test).setOnClickListener(this);
        findViewById(R.id.coin_test).setOnClickListener(this);
        findViewById(R.id.animation_test).setOnClickListener(this);
        findViewById(R.id.surface_view_test).setOnClickListener(this);
        findViewById(R.id.decibe_view_test).setOnClickListener(this);
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
                case R.id.handler_test:
                    intent.setClass(MainActivity.this, HandlerTestActivity.class);
                    break;
                case R.id.async_task_test:
                    intent.setClass(MainActivity.this, AsyncTaskTestActivity.class);
                    break;
                case R.id.http_client_test:
                    intent.setClass(MainActivity.this, HttpClientTestActivity.class);
                    break;
                case R.id.socket_test:
                    intent.setClass(MainActivity.this, SocketTestActivity.class);
                    break;
                case R.id.volley_test:
                    intent.setClass(MainActivity.this, VolleyTestActivity.class);
                    break;
                case R.id.sort_test:
                    intent.setClass(MainActivity.this, SortActivity.class);
                    break;
                case R.id.dagger_test:
                    intent.setClass(MainActivity.this, DaggerTestActivity.class);
                    break;
                case R.id.retrofit_test:
                    intent.setClass(MainActivity.this, RetrofitTestActivity.class);
                    break;
                case R.id.okhttp_test:
                    intent.setClass(MainActivity.this, OkHttpTestActivity.class);
                    break;
                case R.id.rx_java_test:
                    intent.setClass(MainActivity.this, RxjavaTestActivity.class);
                    break;
                case R.id.dynamic_test:
                    intent.setClass(MainActivity.this, DynamicTestActivity.class);
                    break;
                case R.id.compass_test:
                    intent.setClass(MainActivity.this, CompassActivity.class);
                    break;
                case R.id.ruler_test:
                    intent.setClass(MainActivity.this, RulerActivity.class);
                    break;
                case R.id.coin_test:
                    intent.setClass(MainActivity.this, CoinVideoActivity.class);
                    break;
                case R.id.animation_test:
                    intent.setClass(MainActivity.this, AnimationActivity.class);
                    break;
                case R.id.surface_view_test:
                    intent.setClass(MainActivity.this, SurfaceViewActivity.class);
                    break;
                case R.id.decibe_view_test:
                    intent.setClass(MainActivity.this, DecibelActivity.class);
                    break;
            }
            if (intent != null) {
                this.startActivity(intent);
            }
        }
    }
}