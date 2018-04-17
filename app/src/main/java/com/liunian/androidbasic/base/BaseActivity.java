package com.liunian.androidbasic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.liunian.androidbasic.App;

/**
 * Created by dell on 2018/4/17.
 * 用来处理应用在后台被杀死后，让应用重新走启动流程
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.APP_STATUS != App.APP_STATUS_NORMAL) { // 非正常启动流程，直接重新初始化应用界面
            Log.i("liunianprint:", "reInitApp");
            App.reInitApp();
            finish();
            return;
        } else { // 正常启动流程
            setUpViewAndData(savedInstanceState); // 子Activity初始化界面
        }
    }

    /**
     * 提供给子Activity设置界面的接口，不要在onCreate中初始化界面
     * @param savedInstanceState
     */
    protected abstract void setUpViewAndData(@Nullable Bundle savedInstanceState);
}
