package com.liunian.androidbasic.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liunian.androidbasic.R;
import com.liunian.androidbasic.base.BaseActivity;
import com.liunian.androidbasic.servicetest.service.MyAidlServiceInterface;
import com.liunian.androidbasic.servicetest.service.ServiceTestOne;

public class ServiceTestActivity extends BaseActivity implements View.OnClickListener {
    Button mStartServiceButton;
    Button mStopServiceButton;
    Button mBindServiceButton;
    Button mUnBindServiceButton;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyAidlServiceInterface myBinder = MyAidlServiceInterface.Stub.asInterface(iBinder);
            try {
                Log.i("liunianprint:", myBinder.plus(1, 2) + "");
                Log.i("liunianprint:", myBinder.toUpperCaseStr("hello world"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void setUpViewAndData(@Nullable Bundle savedInstanceState) {
        Log.i("liunianprint:", "UI Thread " + Thread.currentThread().getId());
        setContentView(R.layout.activity_service_test);

        mStartServiceButton = (Button) findViewById(R.id.start_service);
        mStopServiceButton = (Button) findViewById(R.id.stop_service);
        mBindServiceButton = (Button) findViewById(R.id.bind_service);
        mUnBindServiceButton = (Button) findViewById(R.id.unbind_service);
        mStartServiceButton.setOnClickListener(this);
        mStopServiceButton.setOnClickListener(this);
        mBindServiceButton.setOnClickListener(this);
        mUnBindServiceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            int resId = view.getId();
            switch (resId) {
                case R.id.start_service:
                    Intent startIntent = new Intent(ServiceTestActivity.this, ServiceTestOne.class);
                    startService(startIntent);
                    break;
                case R.id.stop_service:
                    Intent stopIntent = new Intent(ServiceTestActivity.this, ServiceTestOne.class);
                    stopService(stopIntent);
                    break;
                case R.id.bind_service:
                    Intent bindIntent = new Intent(ServiceTestActivity.this, ServiceTestOne.class);
                    bindService(bindIntent, mServiceConnection, BIND_AUTO_CREATE);
                    break;
                case R.id.unbind_service:
                    unbindService(mServiceConnection);
                    break;
            }
        }
    }
}
