package com.liunian.androidbasic.servicetest.service;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liunian.androidbasic.R;
import com.liunian.androidbasic.servicetest.ServiceTestActivity;

/**
 * Created by dell on 2018/4/18.
 */

public class ServiceTestOne extends Service {
    MyAidlServiceInterface.Stub mMyBinder = new MyAidlServiceInterface.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCaseStr(String str) throws RemoteException {
            if (str != null) {
                return str.toUpperCase();
            }
            return null;
        }
    };

    @Override
    public void onCreate() {
        Log.i("liunianprint:", "onCreate executed");
        Log.i("liunianprint:", "Service Thread " + Thread.currentThread().getId());
        super.onCreate();

        Intent notificationIntent = new Intent(this, ServiceTestActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification notification = new Builder(this).setSmallIcon(R.mipmap.ic_launcher).
                    setContentTitle("这是标题通知").setContentText("这是通知内容").setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent).build();
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("liunianprint:", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i("liunianprint:", "onDestroy executed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("liunianprint:", "onBind executed");
        return mMyBinder;
    }
}
