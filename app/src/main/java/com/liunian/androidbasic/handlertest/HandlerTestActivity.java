package com.liunian.androidbasic.handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liunian.androidbasic.R;

import java.lang.ref.WeakReference;

public class HandlerTestActivity extends AppCompatActivity {
    public static String TAG = "HandlerTestActivity";
    TextView mShowTextView;
    Button mStartDownLoadButton;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        mHandler = new MyHandler(this);
        mShowTextView = (TextView) findViewById(R.id.show_text);
        mStartDownLoadButton = (Button) findViewById(R.id.start_download);
        mStartDownLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImageThread(mHandler).start();
            }
        });

        mHandlerTestThread = new HandlerTestThread();
        mHandlerTestThread.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                mHandlerTestThread.mHandler.sendMessage(message);
            }
        }).start();
    }

    private HandlerTestThread mHandlerTestThread;

    private class HandlerTestThread extends Thread {
        public Handler mHandler;

        @Override
        public void run() {
            Log.i(TAG, "HandlerTestThread start");
            Looper.prepare();
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.i(TAG, "handlerMessage");
                }
            };
            Looper.loop(); // 让Looper开始工作
            Looper.myLooper().quit();
            Log.i(TAG, "HandlerTestThread end");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁后移除Handler中未处理完的消息，避免内存泄露或者发生引用错误
        mHandler.removeMessages(DownloadImageThread.DOWNLOAD_IMAGE_FINISH);
    }

    // Handler类，声明为static，避免持有外部类的直接引用，防止内存泄露
    private static class MyHandler extends Handler {
        private WeakReference<HandlerTestActivity> activityWeakReference; // 持有外部类的弱引用

        public MyHandler(HandlerTestActivity activity) {
            activityWeakReference = new WeakReference<HandlerTestActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DownloadImageThread.DOWNLOAD_IMAGE_FINISH: // 处理下载完成消息，更新UI
                    HandlerTestActivity handlerTestActivity = activityWeakReference.get();
                    if (handlerTestActivity != null) {
                        handlerTestActivity.mShowTextView.setText((String) msg.obj);
                    }
                    break;
            }
        }
    }

    // 模拟下载图片的子线程
    public static class DownloadImageThread extends Thread { // 将Thread声明为static，非静态内部类或者匿名类默认持有外部类对象的引用，容易造成内存泄露
        public static final int DOWNLOAD_IMAGE_FINISH = 1;
        private WeakReference<Handler> handlerWeakReference;

        public DownloadImageThread(Handler handler) { // 采用弱引用的方式持有Activity中Handler对象的引用，避免内存泄露
            handlerWeakReference = new WeakReference<Handler>(handler);
        }

        @Override
        public void run() {
            Log.i(TAG, "子线程开始下载图片");
            // 模拟下载图片
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(60);
                    Log.i(TAG, "下载进度:" + i + "%");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.i(TAG, "子线程下载图片完成");
            // 发送消息通知UI线程下载完毕
            Message message = Message.obtain();
            message.what = DOWNLOAD_IMAGE_FINISH;
            message.obj = "图片下载完成";
            Handler handler = handlerWeakReference.get();
            if (handler != null) {
                handler.sendMessage(message);
            }
        }
    }
}
